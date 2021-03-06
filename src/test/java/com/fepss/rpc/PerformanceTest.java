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
package com.fepss.rpc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoHandler;
import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fepss.rpc.client.RpcChannelImpl;
import com.fepss.rpc.server.impl.RpcIoHandler;
import com.fepss.rpc.server.impl.RpcServerImpl;
import com.fepss.rpc.test.TestServiceImpl;
import com.fepss.rpc.test.TestProto.Result;
import com.fepss.rpc.test.TestProto.TestService;
import com.fepss.rpc.test.TestProto.User;
import com.fepss.rpc.test.TestProto.TestService.Stub;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.Service;

/**
 * performance test case
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class PerformanceTest {

	/**
	 * @param args
	 * @throws IOException
	 */
//	@Test(invocationCount = 1000000, threadPoolSize = 1000)
	@Test
	public void testRpcServer() throws IOException {
		final RpcController mock = EasyMock.createMock(RpcController.class);
		mock.reset();
		EasyMock.replay(mock);
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
				Assert.assertEquals(result.getResult(), "get userRequest Data");
				Assert.assertTrue(result.getSuccess());
				mock.reset();

			}
		};
		// execute remote method
		service.testMethod(controller, request, done);
		Assert.assertFalse(controller.failed());
		Assert.assertEquals(controller.errorText(), null);
		EasyMock.verify(mock);
	}

	private RpcServerImpl server;
	private String host;
	private int port;

	@BeforeClass
	public void setup() throws IOException {
		host = "localhost";
		port = 8081;
		Map<String, Service> services = new HashMap<String, Service>();
		services.put(TestService.getDescriptor().getFullName(),
				new TestServiceImpl());
		IoHandler handler = new RpcIoHandler(services);

		// start rpc server
		server = new RpcServerImpl(host, port, handler);
		server.start();
	}

	@AfterClass
	public void stopServer() {
		server.stop();
	}
}
