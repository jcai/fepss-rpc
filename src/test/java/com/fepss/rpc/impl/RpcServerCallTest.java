package com.fepss.rpc.impl;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.mina.core.service.IoHandler;
import org.testng.annotations.Test;

import com.fepss.rpc.RpcChannelImpl;
import com.fepss.rpc.RpcIoHandler;
import com.fepss.rpc.RpcServer;
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
		String host = "localhost";
		int port=8081;
		IoHandler handler = new RpcIoHandler(new TestServiceImpl());
		//start rpc server
		RpcServer server = new RpcServerImpl(host, port, handler);
		server.start();
		
		//create client to call rpc
		RpcChannelImpl channel = new RpcChannelImpl(host,port);
		RpcController controller = channel.newRpcController();
		Stub service = TestService.newStub(channel);
		//request data
		String reqdata = "Request Data";
	    User request = User.newBuilder().setUserName(reqdata).build();

	    //response callback
		RpcCallback<Result> done=new RpcCallback<Result>(){
			@Override
			public void run(Result result){
				Assert.assertEquals(result.getResult(),"get userRequest Data");
				Assert.assertTrue(result.getSuccess());
				
			}};
		//execute remote method
		service.testMethod(controller, request, done);
		
		//stop server
		server.stop();

	}

}
