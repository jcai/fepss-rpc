package com.fepss.rpc;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.testng.annotations.Test;

import com.fepss.rpc.client.RpcChannelImpl;
import com.fepss.rpc.server.RpcApplication;
import com.fepss.rpc.test.TestProto.Result;
import com.fepss.rpc.test.TestProto.TestService;
import com.fepss.rpc.test.TestProto.User;
import com.fepss.rpc.test.TestProto.Result.Builder;
import com.fepss.rpc.test.TestProto.TestService.Stub;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.Service;

public class ClientExceptionTest {
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
					throw new RuntimeException("client call back exception!");
				}
			};
			// execute remote method
			service.testMethod(controller, request, done);
			Assert.assertTrue(controller.failed());
			Assert.assertTrue(controller.errorText().startsWith("client has runtime exception!"));
		}finally{
			application.stop();
		}
		
	}
	public static class TestModule{
		public static void contributeIoHandler(MappedConfiguration<String,Service> configruation){
			configruation.add(TestService.getDescriptor().getFullName(),new MyTestServiceImpl());
		}
	}
	public static class MyTestServiceImpl extends TestService{

		@Override
		public void testMethod(RpcController controller, User request,
				RpcCallback<Result> done) {
			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			Builder builder =Result.newBuilder().setResult("get user"+request.getUserName());
			builder.setSuccess(true);
			done.run(builder.build());
		}
	}
}
