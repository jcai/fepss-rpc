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
package com.fepss.rpc.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fepss.rpc.RpcSymbols;
import com.fepss.rpc.RpcServer;
import com.fepss.rpc.codec.ProtobufMessageEncoder;
import com.fepss.rpc.codec.ProtobufRequestDecoder;

/**
 * RPC Server
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class RpcServerImpl implements RpcServer {
	private static final Logger logger = LoggerFactory
			.getLogger(RpcServerImpl.class);
	private SocketAcceptor acceptor;
	private String host;
	private int port;
	private IoHandler ioHandler;

	public RpcServerImpl(@Inject @Symbol(RpcSymbols.HOST) String host,
			@Inject @Symbol(RpcSymbols.PORT) int port,
			@Inject IoHandler rpcIoHandler) {
		this.host = host;
		this.port = port;
		this.ioHandler = rpcIoHandler;
	}

	public void start() throws IOException {
		logger.info("starting rpc server!");
		ExecutorService executor = Executors.newCachedThreadPool();
		int processorCount = Runtime.getRuntime().availableProcessors();
		acceptor = new NioSocketAcceptor(processorCount);
		acceptor.setReuseAddress(true);
		acceptor.getSessionConfig().setReuseAddress(true);// 设置每一个非主监听连接的端口可以重用
		acceptor.getSessionConfig().setReceiveBufferSize(1024);// 设置输入缓冲区的大小
		acceptor.getSessionConfig().setSendBufferSize(10240);// 设置输出缓冲区的大小
		// 设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
		acceptor.getSessionConfig().setTcpNoDelay(true);
		// 设置主服务监听端口的监听队列的最大值为1000
		acceptor.setBacklog(1000);
		
		acceptor.setDefaultLocalAddress(new InetSocketAddress(port));
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("executor", new ExecutorFilter(executor));
		chain.addLast("codec", new ProtocolCodecFilter(
				ProtobufMessageEncoder.class, ProtobufRequestDecoder.class));
		acceptor.setHandler(ioHandler);
		acceptor.bind(new InetSocketAddress(host, port));
	}

	/**
	 * @see com.fepss.rpc.RpcServer#stop()
	 */
	public void stop() {
		logger.info("stoping rpc server!");
		acceptor.unbind();
	}
}
