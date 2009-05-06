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

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;

/**
 * RPC Application
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class RpcApplication {
	private long startTime;

	private final RegistryBuilder builder = new RegistryBuilder();

	private long registryCreatedTime;
	private Registry registry;
	public RpcApplication(){
        startTime = System.currentTimeMillis();
        builder.add(RpcCoreModule.class);
        registry = builder.build();
	}
	public void start(){
//		registry.getService(RpcServeri.class)
		
	}
	public final static void main(String[] args) {
	}
}
