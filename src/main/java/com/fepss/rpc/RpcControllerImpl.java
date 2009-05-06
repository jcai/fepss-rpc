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

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

/**
 * implement RpcController
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class RpcControllerImpl implements RpcController {

	private String reason;
	private boolean failed;

	@Override
	public String errorText() {
		return reason;
	}

	@Override
	public boolean failed() {
		return failed;
	}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public void notifyOnCancel(RpcCallback<Object> callback) {
	}

	@Override
	public void reset() {
	}

	@Override
	public void setFailed(String reason) {
		this.reason = reason;
		this.failed = true;
	}

	@Override
	public void startCancel() {
	}

}
