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

import org.apache.mina.core.service.IoHandler;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;

import com.fepss.rpc.server.RpcServer;
import com.fepss.rpc.server.impl.RpcIoHandler;
import com.fepss.rpc.server.impl.RpcServerImpl;

/**
 * RPC Core Module
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class RpcCoreModule {
	public static void bind(ServiceBinder binder){
		binder.bind(RpcServer.class,RpcServerImpl.class);
		binder.bind(IoHandler.class,RpcIoHandler.class);
	}

    /**
     * Contributes factory defaults that may be overridden.
     */
    public static void contributeFactoryDefaults(MappedConfiguration<String, String> configuration)
    {
    	configuration.add(RpcSymbols.HOST,RpcConstants.DEFAULT_HOST);
    	configuration.add(RpcSymbols.PORT,String.valueOf(RpcConstants.DEFAULT_PORT));
    	
    }
}
