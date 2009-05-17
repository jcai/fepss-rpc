package com.fepss.rpc;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.testng.annotations.Test;

import com.fepss.rpc.client.RpcChannelImpl;
import com.fepss.rpc.server.RpcApplication;
import com.fepss.rpc.test.TestServiceImpl;
import com.fepss.rpc.test.TestProto.Result;
import com.fepss.rpc.test.TestProto.TestService;
import com.fepss.rpc.test.TestProto.User;
import com.fepss.rpc.test.TestProto.TestService.Stub;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.Service;

public class RpcApplicationTest {
	@Test
	public void testRpc() throws IOException{
		RpcApplication application = new RpcApplication(TestModule.class);
		try{
			application.start();
			// create client to call rpc
			RpcChannelImpl channel = new RpcChannelImpl(RpcConstants.DEFAULT_HOST,RpcConstants.DEFAULT_PORT);
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
		}finally{
			application.stop();
		}
		
	}
	public static class TestModule{
		public static void contributeIoHandler(MappedConfiguration<String,Service> configruation){
			configruation.add(TestService.getDescriptor().getFullName(),new TestServiceImpl());
		}
	}
}
