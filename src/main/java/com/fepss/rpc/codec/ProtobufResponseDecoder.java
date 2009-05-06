/* 
 * Copyright 2008 The Corner Team.
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
package com.fepss.rpc.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.fepss.rpc.RpcProtobuf;
import com.fepss.rpc.RpcProtobuf.Response;
import com.fepss.rpc.RpcProtobuf.Response.Builder;

/**
 * provide some protocol buffer decoder.
 * <p>
 * It should decode some protocol buffer object.
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class ProtobufResponseDecoder extends ProtocolDecoderAdapter {

	/**
	 * @see org.apache.mina.filter.codec.ProtocolDecoder#decode(org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer, org.apache.mina.filter.codec.ProtocolDecoderOutput)
	 */
	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		Builder builder = RpcProtobuf.Response.newBuilder().mergeFrom(in.asInputStream());
		Response response= builder.build();
		out.write(response);
	}

}
