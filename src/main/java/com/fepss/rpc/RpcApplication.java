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

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RPC Application
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class RpcApplication {
	private static final Logger logger = LoggerFactory.getLogger(RpcApplication.class);
	private long startTime;

	private final RegistryBuilder builder = new RegistryBuilder();

	private Registry registry;

	private RpcServer server;
	public RpcApplication() throws IOException{
        startTime = System.currentTimeMillis();
        builder.add(RpcCoreModule.class);
        registry = builder.build();
		server = registry.getService(RpcServer.class);
		start();
		long endTime = System.currentTimeMillis();
		logger.info("start rpc server with "+(endTime-startTime)+" millis");
	}
	public void start() throws IOException{
		server.start();
	}
	public void stop(){
		server.stop();
	}
	public final static void main(String[] args) throws IOException {
		new RpcApplication();
	}
}
