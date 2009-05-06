package com.fepss.rpc;


import java.io.IOException;

import org.apache.mina.core.session.IoSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fepss.rpc.RpcProtobuf.ErrorReason;
import com.fepss.rpc.RpcProtobuf.Request;
import com.fepss.rpc.RpcProtobuf.Response;
import com.fepss.rpc.test.TestServiceImpl;
import com.fepss.rpc.test.TestProto.User;

public class RpcIoHandlerTest {
	@Test
	public void testNullReceived(){
		RpcIoHandler handler = new RpcIoHandler(new TestServiceImpl());
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
		RpcIoHandler handler = new RpcIoHandler(new TestServiceImpl());
		User user= User.newBuilder().setUserName("jcai").build();
		Request request = Request.newBuilder().setServiceName("TestService")
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
		RpcIoHandler handler = new RpcIoHandler(new TestServiceImpl()){

			/**
			 * @see com.fepss.rpc.RpcIoHandler#outputResponse(org.apache.mina.core.session.IoSession, com.fepss.rpc.RpcProtobuf.Response)
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
		Request request = Request.newBuilder().setServiceName("protobuf.socketrpc.TestService")
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
