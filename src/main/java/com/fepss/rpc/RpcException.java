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

import com.fepss.rpc.RpcProtobuf.ErrorReason;

/**
 * rpc call exception.
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class RpcException extends RuntimeException {

	private static final long serialVersionUID = 8146528988781443238L;
	private ErrorReason reason;

	public RpcException(ErrorReason errorReason, String message) {
		super(message);
		this.reason = errorReason;
	}
	public RpcException(ErrorReason errorReason,
			Exception e) {
		super(e);
		this.reason = errorReason;
	}
	/**
	 * @return the reason
	 */
	public ErrorReason getReason() {
		return reason;
	}

}
