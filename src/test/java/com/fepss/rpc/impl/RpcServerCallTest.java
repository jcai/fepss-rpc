package com.fepss.rpc.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.mina.core.service.IoHandler;
import org.testng.annotations.Test;

import com.fepss.rpc.RpcChannelImpl;
import com.fepss.rpc.RpcServer;
import com.fepss.rpc.test.TestServiceImpl;
import com.fepss.rpc.test.TestProto.Result;
import com.fepss.rpc.test.TestProto.TestService;
import com.fepss.rpc.test.TestProto.User;
import com.fepss.rpc.test.TestProto.TestService.Stub;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.Service;

public class RpcServerCallTest {

	/**
	 * @param args
	 * @throws IOException
	 */
	@Test
	public void testServiceNotFoundRpcServer() throws IOException {
		String host = "localhost";
		int port = 8081;
		Map<String, Service> services = new HashMap<String, Service>();
		services.put("testService", new TestServiceImpl());
		IoHandler handler = new RpcIoHandler(services);

		// start rpc server
		RpcServer server = new RpcServerImpl(host, port, handler);
		try {
			server.start();

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

				}
			};
			// execute remote method
			service.testMethod(controller, request, done);
			Assert.assertTrue(controller.failed());
			Assert.assertEquals(controller.errorText(), "SERVICE_NOT_FOUND : could not find service: protobuf.rpc.TestService");
		} finally {
			// stop server
			server.stop();
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	@Test
	public void testRpcServer() throws IOException {
		String host = "localhost";
		int port = 8081;
		Map<String, Service> services = new HashMap<String, Service>();
		services.put("protobuf.rpc.TestService", new TestServiceImpl());
		IoHandler handler = new RpcIoHandler(services);

		// start rpc server
		RpcServer server = new RpcServerImpl(host, port, handler);
		try {
			server.start();

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

				}
			};
			// execute remote method
			service.testMethod(controller, request, done);
			Assert.assertFalse(controller.failed());
			Assert.assertEquals(controller.errorText(), null);
		} finally {
			// stop server
			server.stop();
		}
	}
}
