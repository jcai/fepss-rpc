package com.fepss.rpc.server.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.mina.core.service.IoHandler;
import org.easymock.EasyMock;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fepss.rpc.client.RpcChannelImpl;
import com.fepss.rpc.test.TestServiceImpl;
import com.fepss.rpc.test.TestProto.Result;
import com.fepss.rpc.test.TestProto.TestService;
import com.fepss.rpc.test.TestProto.User;
import com.fepss.rpc.test.TestProto.TestService.Stub;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.Service;

public class RpcServerCallTest {
	private RpcServerImpl server;
	private int port;
	private String host;
	@BeforeClass
	void setUpServer() throws IOException{
		host = "localhost";
		port = 8081;
		Map<String, Service> services = new HashMap<String, Service>();
		services.put(TestService.getDescriptor().getFullName(), new TestServiceImpl());
		IoHandler handler = new RpcIoHandler(services);

		// start rpc server
		server = new RpcServerImpl(host, port, handler);
		server.start();
	}
	@AfterClass
	void stopServer(){
		server.stop();
	}

	

	/**
	 * @param args
	 * @throws IOException
	 */
	@Test
	public void testRpcServer() throws IOException {
		final RpcController mock = EasyMock.createMock(RpcController.class);
		mock.reset();
		EasyMock.replay(mock);
		

		try {
			// create client to call rpc
			RpcChannelImpl channel = new RpcChannelImpl(host, port);
			RpcController controller = channel.newRpcController();
			Stub service = TestService.newStub(channel);
			// request data
			String reqdata = "Request Data";
			User request = User.newBuilder().setUserName(reqdata).build();

			// response callback
			RpcCallback<Result> done = new RpcCallback<Result>() {
				@Override
				public void run(Result result) {
					Assert.assertEquals(result.getResult(),
							"get userRequest Data");
					Assert.assertTrue(result.getSuccess());
					mock.reset();

				}
			};
			// execute remote method
			service.testMethod(controller, request, done);
			Assert.assertFalse(controller.failed());
			Assert.assertEquals(controller.errorText(), null);
			EasyMock.verify(mock);
		} finally {
		}
	}
	/**
	 * @param args
	 * @throws IOException
	 */
	@Test
	public void testFailRpcServer() throws IOException {
		try {
			// create client to call rpc
			RpcChannelImpl channel = new RpcChannelImpl(host, port);
			RpcController controller = channel.newRpcController();
			Stub service = TestService.newStub(channel);
			// request data
			String reqdata = "fail";
			User request = User.newBuilder().setUserName(reqdata).build();

			// response callback
			RpcCallback<Result> done = new RpcCallback<Result>() {
				@Override
				public void run(Result result) {
					Assert.fail("should not be here!");
					

				}
			};
			// execute remote method
			service.testMethod(controller, request, done);
			Assert.assertTrue(controller.failed());
			Assert.assertEquals(controller.errorText(), "RPC_ERROR : com.fepss.rpc.RpcException: java.lang.RuntimeException: try to throw exception!");
		} finally {
		}
	}
}
