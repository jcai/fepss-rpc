package com.fepss.rpc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoHandler;

import com.fepss.rpc.server.impl.RpcIoHandler;
import com.fepss.rpc.server.impl.RpcServerImpl;
import com.fepss.rpc.test.TestServiceImpl;
import com.fepss.rpc.test.TestProto.TestService;
import com.google.protobuf.Service;

public class RunRpcServerMain {

	/**
	 * @param args
	 * @throws IOException 
	 * @since 0.0.2
	 */
	public static void main(String[] args) throws IOException {
		String host = "localhost";
		int port = 8081;
		Map<String, Service> services = new HashMap<String, Service>();
		services.put(TestService.getDescriptor().getFullName(),
				new TestServiceImpl());
		IoHandler handler = new RpcIoHandler(services);

		// start rpc server
		RpcServerImpl server = new RpcServerImpl(host, port, handler);
		server.start();

	}

}
