/* 
 * Copyright 2009 The Corner Team.
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
package com.fepss.rpc.client;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fepss.rpc.RpcException;
import com.fepss.rpc.client.RpcProtobuf.ErrorReason;
import com.fepss.rpc.client.RpcProtobuf.Request;
import com.fepss.rpc.client.RpcProtobuf.Response;
import com.fepss.rpc.codec.mina.ProtobufDecoder;
import com.fepss.rpc.codec.mina.ProtobufEncoder;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;
import com.google.protobuf.Descriptors.MethodDescriptor;

/**
 * implements RpcChannel
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class RpcChannelImpl implements RpcChannel {

	private final static Logger logger = LoggerFactory
			.getLogger(RpcChannelImpl.class);

	private final String host;
	private final int port;

	/**
	 * Create a channel for TCP/IP RPCs.
	 */
	public RpcChannelImpl(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * Create new rpc controller to be used to control one request.
	 */
	public RpcControllerImpl newRpcController() {
		return new RpcControllerImpl();
	}

	public void callMethod(final MethodDescriptor method,
			final RpcController controller, final Message request,
			final Message responsePrototype, final RpcCallback<Message> done) {
		// check rpc request
		if (!request.isInitialized()) {
			throw new RpcException(ErrorReason.BAD_REQUEST_DATA,
					"request uninitialized!");
		}

		// using MINA IoConnector
		IoConnector connector = new NioSocketConnector();

		// add protocol buffer codec
		/*
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(ProtobufMessageEncoder.class,
						ProtobufResponseDecoder.class));
						*/
		addProtobufCodec(connector.getFilterChain());

		// connector handler
		connector.setHandler(new IoHandlerAdapter() {
			@Override
			public void messageReceived(IoSession session, Object message)
					throws Exception {
				if(logger.isDebugEnabled()){
					logger.debug("received from server");
				}
				Response rpcResponse = (Response) message;
				handleResponse(responsePrototype, rpcResponse, controller, done);
				session.close(true);
			}

			/**
			 * @see org.apache.mina.core.service.IoHandlerAdapter#sessionOpened(org.apache.mina.core.session.IoSession)
			 */
			@Override
			public void sessionOpened(IoSession session) throws Exception {
				if(logger.isDebugEnabled()){
					logger.debug("open session");
				}
		            ((SocketSessionConfig) session.getConfig()).setKeepAlive(true);

    			// Create request protocol buffer
    			Request rpcRequest = Request.newBuilder().setRequestProto(
    					request.toByteString()).setServiceName(
    					method.getService().getFullName()).setMethodName(
    					method.getName()).build();
    			// Write request
    			 session.write(rpcRequest);
    			}

			/**
			 * @see org.apache.mina.core.service.IoHandlerAdapter#exceptionCaught(org.apache.mina.core.session.IoSession, java.lang.Throwable)
			 */
			@Override
			public void exceptionCaught(IoSession session, Throwable cause)
					throws Exception {
				logger.warn("client application has runtime exception!");
				StringBuilder errorBuilder = new StringBuilder();
				errorBuilder.append("client has runtime exception!\n");
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				cause.printStackTrace(new PrintStream(out));
				errorBuilder.append(out.toString());
				controller.setFailed(errorBuilder.toString());
			}
			
		});

		// connect remote server
		ConnectFuture cf = connector.connect(new InetSocketAddress(host, port));
		try {
			cf.awaitUninterruptibly();// wait to connect remote server
			cf.getSession().getCloseFuture().awaitUninterruptibly();
		} finally {
			connector.dispose();
		}
	}
	private void addProtobufCodec(DefaultIoFilterChainBuilder filterChain) {
		filterChain.addLast("protobuf", new ProtocolCodecFilter(new ProtobufEncoder(),new ProtobufDecoder(){
			@Override
			protected Message.Builder newBuilder() {
				return RpcProtobuf.Response.newBuilder();
			}}));
	}

	private void handleResponse(Message responsePrototype,
			Response rpcResponse, RpcController controller,
			RpcCallback<Message> callback) {
		if(logger.isDebugEnabled()){
			logger.debug("handler response");
		}
		// Check for error
		if (rpcResponse.hasError()) {
			com.fepss.rpc.client.RpcProtobuf.ErrorReason reason = rpcResponse.getErrorReason();
			controller.setFailed(reason.name()+" : "+rpcResponse.getError());
			return;
		}

		if ((callback == null) || !rpcResponse.getCallback()) {
			// No callback needed
			return;
		}

		if (!rpcResponse.hasResponseProto()) {
			// Callback was called with null on server side
			callback.run(null);
			return;
		}
		try {
			Message.Builder builder = responsePrototype.newBuilderForType()
					.mergeFrom(rpcResponse.getResponseProto());
			Message response = builder.build();
			callback.run(response);
		} catch (InvalidProtocolBufferException e) {
			logger.warn(e.getMessage(),e);
			throw new RuntimeException(e);
		}
	}
}
