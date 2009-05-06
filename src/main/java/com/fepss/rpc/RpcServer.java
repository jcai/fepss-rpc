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
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.fepss.rpc.codec.ProtobufMessageEncoder;
import com.fepss.rpc.codec.ProtobufRequestDecoder;
import com.fepss.rpc.test.TestServiceImpl;

/**
 * RPC Server
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class RpcServer {
	private SocketAcceptor acceptor;
	
	public void start() throws IOException {
		ExecutorService executor = Executors.newCachedThreadPool();
		int processorCount = Runtime.getRuntime().availableProcessors();
		acceptor = new NioSocketAcceptor(processorCount);
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("executor", new ExecutorFilter(executor));
		chain.addLast("codec",
				new ProtocolCodecFilter(ProtobufMessageEncoder.class,ProtobufRequestDecoder.class));
		acceptor.setHandler(new RpcIoHandler(new TestServiceImpl()));
		acceptor.bind(new InetSocketAddress("127.0.0.1",8081));
	}
	public void stop(){
		acceptor.unbind();
	}
}
