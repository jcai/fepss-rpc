package com.fepss.rpc;
import java.io.IOException;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.fepss.rpc.test.TestServiceImpl;
import com.fepss.rpc.test.TestProto.Result;
import com.fepss.rpc.test.TestProto.TestService;
import com.fepss.rpc.test.TestProto.User;
import com.fepss.rpc.test.TestProto.TestService.Stub;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

public class RpcServerCallTest {
	

	/**
	 * @param args
	 * @throws IOException 
	 */
	@Test
	public void testRpcServer() throws IOException {
		RpcServer server = new RpcServer();
		server.start(new TestServiceImpl());
		
		RpcChannelImpl channel = new RpcChannelImpl("localhost",8081);
		RpcController controller = channel.newRpcController();
		Stub service = TestService.newStub(channel);
		String reqdata = "Request Data";
	    User request = User.newBuilder().setUserName(reqdata).build();

		RpcCallback<Result> done=new RpcCallback<Result>(){
			@Override
			public void run(Result result){
				Assert.assertEquals(result.getResult(),"get userRequest Data");
				Assert.assertTrue(result.getSuccess());
				
			}};
		service.testMethod(controller, request, done);
		
		server.stop();

	}

}
