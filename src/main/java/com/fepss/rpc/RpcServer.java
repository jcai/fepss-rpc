package com.fepss.rpc;

import java.io.IOException;

/**
 * Rpc Server interface
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public interface RpcServer {

	/**
	 * start rpc server
	 * @throws IOException if some error happens
	 * @since 0.0.2
	 */
	public abstract void start() throws IOException;

	public abstract void stop();

}