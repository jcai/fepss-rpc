package com.fepss.rpc.server.impl;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fepss.rpc.RpcException;
import com.fepss.rpc.client.RpcProtobuf.ErrorReason;
import com.fepss.rpc.client.RpcProtobuf.Request;
import com.fepss.rpc.client.RpcProtobuf.Response;
import com.fepss.rpc.server.impl.RpcIoHandler;
import com.fepss.rpc.test.TestServiceImpl;
import com.fepss.rpc.test.TestProto.User;
import com.google.protobuf.Service;

public class RpcIoHandlerTest {
	private IoHandler createRpcHandler(){
		Map<String,Service> services = new HashMap<String,Service>();
		services.put("testService",new TestServiceImpl());
		return  new RpcIoHandler(services);
		
	}
	@Test
	public void testNullReceived(){
		IoHandler handler = createRpcHandler();
		Request message = null;
		IoSession session = null;
		try {
			handler.messageReceived(session, message);
			Assert.fail("could not reach here");
		} catch (RpcException e) {
			Assert.assertEquals(e.getReason(),ErrorReason.BAD_REQUEST_DATA);
			
		} catch (Exception e) {
			Assert.fail("could not reach here",e);
		}
	}
	@Test
	public void testMethodNotFoundReceived(){
		IoHandler handler = createRpcHandler();
		User user= User.newBuilder().setUserName("jcai").build();
		Request request = Request.newBuilder().setServiceName("testService")
				.setMethodName("testMethod").setRequestProto(user.toByteString()).build();
		IoSession session = null;
		try {
			handler.messageReceived(session,request);
			Assert.fail("could not reach here");
		} catch (RpcException e) {
			Assert.assertEquals(e.getReason(),ErrorReason.METHOD_NOT_FOUND);
			
		} catch (Exception e) {
			Assert.fail("could not reach here",e);
		}
	}
	@Test
	public void testReceived(){
		Map<String,Service> services = new HashMap<String,Service>();
		services.put("testService",new TestServiceImpl());
		IoHandler handler = new RpcIoHandler(services){
			/**
			 * @see com.fepss.rpc.server.impl.RpcIoHandler#outputResponse(org.apache.mina.core.session.IoSession, com.fepss.rpc.client.RpcProtobuf.Response)
			 */
			@Override
			void outputResponse(IoSession session, Response rpcResponse)
					throws IOException {
				Assert.assertTrue(rpcResponse.getCallback());
				Assert.assertTrue(rpcResponse.getError().length()==0);
				Assert.assertNotNull(rpcResponse.getResponseProto());
			}
			
		};
		User user= User.newBuilder().setUserName("jcai").build();
		Request request = Request.newBuilder().setServiceName("testService")
				.setMethodName("TestMethod").setRequestProto(user.toByteString()).build();
		try {
			IoSession session =null;
			handler.messageReceived(session,request);
		} catch (RpcException e) {
			Assert.fail("could not reach here",e);
		} catch (Exception e) {
			Assert.fail("could not reach here",e);
		}
	}
}
