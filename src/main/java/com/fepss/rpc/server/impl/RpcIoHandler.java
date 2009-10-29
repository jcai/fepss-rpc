/* 
 * Copyright 2008 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fepss.rpc.server.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.fepss.rpc.RpcException;
import com.fepss.rpc.client.RpcControllerImpl;
import com.fepss.rpc.client.RpcProtobuf.ErrorReason;
import com.fepss.rpc.client.RpcProtobuf.Request;
import com.fepss.rpc.client.RpcProtobuf.Response;
import com.fepss.rpc.client.RpcProtobuf.Response.Builder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.Service;
import com.google.protobuf.Descriptors.MethodDescriptor;

/**
 * protocol buffer Io handler
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class RpcIoHandler extends IoHandlerAdapter {

	private Map<String, Service> services;
	private final static Logger logger = Logger.getLogger(RpcIoHandler.class);

	public RpcIoHandler(Map<String, Service> services) {
		this.services = services;
	}

	/**
	 * @see org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache.mina.core.session.IoSession,
	 *      java.lang.Object)
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		Request rpcRequest = (Request) message;
		if (rpcRequest == null) {
			throw new RpcException(ErrorReason.BAD_REQUEST_DATA,
					"request data is null!");
		}
		// Get the service/method
		Service service = services.get(rpcRequest.getServiceName());
		if (service == null) {
			throw new RpcException(ErrorReason.SERVICE_NOT_FOUND,
					"could not find service: " + rpcRequest.getServiceName());
		}
		MethodDescriptor method = service.getDescriptorForType()
				.findMethodByName(rpcRequest.getMethodName());
		if (method == null) {
			throw new RpcException(ErrorReason.METHOD_NOT_FOUND, String.format(
					"Could not find method %s in service %s", rpcRequest
							.getMethodName(), service.getDescriptorForType()
							.getFullName()));
		}

		// Parse request
		Message.Builder builder = null;
		try {
			builder = service.getRequestPrototype(method).newBuilderForType()
					.mergeFrom(rpcRequest.getRequestProto());
			if (!builder.isInitialized()) {
				throw new RpcException(ErrorReason.BAD_REQUEST_PROTO,
						"Invalid request proto");
			}
		} catch (InvalidProtocolBufferException e) {
			throw new RpcException(ErrorReason.BAD_REQUEST_PROTO, e);
		}
		Message request = builder.build();

		// Call method
		RpcControllerImpl controller = new RpcControllerImpl();
		Callback callback = new Callback();
		try {
			service.callMethod(method, controller, request, callback);
		} catch (RuntimeException e) {
			throw new RpcException(ErrorReason.RPC_ERROR, e);
		}

		// Build and return response (callback is optional)
		Builder responseBuilder = Response.newBuilder();
		if (callback.response != null) {
			responseBuilder.setCallback(true).setResponseProto(
					callback.response.toByteString());
		} else {
			// Set whether callback was called
			responseBuilder.setCallback(callback.invoked);
		}
		if (controller.failed()) {
			responseBuilder.setError(controller.errorText());
			responseBuilder.setErrorReason(ErrorReason.RPC_FAILED);
		}
		Response rpcResponse = responseBuilder.build();
		outputResponse(session, rpcResponse);
	}

	// output response protobuf
	void outputResponse(IoSession session, Response rpcResponse)
			throws IOException {
		WriteFuture future = session.write(rpcResponse);
		future.addListener(IoFutureListener.CLOSE);
	}

	/**
	 * Callback that just saves the response and the fact that it was invoked.
	 */
	private class Callback implements RpcCallback<Message> {

		private Message response;
		private boolean invoked = false;

		public void run(Message response) {
			this.response = response;
			invoked = true;
		}
	}

	/**
	 * @see org.apache.mina.core.service.IoHandlerAdapter#exceptionCaught(org.apache.mina.core.session.IoSession,
	 *      java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.warn("RPC Server Error", cause);
		Builder builder = Response.newBuilder().setError(cause.toString());
		if (cause instanceof RpcException) {
			RpcException rpcException = (RpcException) cause;
			if (rpcException.getReason() != null) {
				builder.setErrorReason((ErrorReason) rpcException.getReason());
			}
		}
		outputResponse(session, builder.build());
	}

	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		session.close(true);
	}

	public void sessionOpened(IoSession session) throws Exception {
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
	}

}
