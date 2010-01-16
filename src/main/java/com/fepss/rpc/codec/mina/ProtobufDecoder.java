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
package com.fepss.rpc.codec.mina;

import java.io.IOException;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.Message;

/**
 * Protocol decoder used to decode protobuf messages from the wire.
 * It is based on {@link CumulativeProtocolDecoder} to deal with
 * fragmented messages handled by the framework in asynchronous fashion.
 * 
 * This decoder handles single type of messages provided as prototype.
 * However the registry of extensions {@link ExtensionRegistry} can also
 * be provided. 
 * 
 * The instance of the decoder should be created by the {@link ProtobufCodecFactory},
 * however the protected constructors has been provided for convenience.
 * 
 * @author Tomasz Blachowicz
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev$, $Date$
 */
public abstract class ProtobufDecoder extends CumulativeProtocolDecoder  {
	
	/**
	 * Decodes protobuf messages of given type from buffer. If not enough data
	 * has been presented delegates to the {@link CumulativeProtocolDecoder} base
	 * class to read more data from the wire.
	 * 
	 * It uses instance of internal {@link SizeContext} class to calculate
	 * size of buffer expected by the given type of message. The size of every
	 * message that arrives on the wire is specified by the prepending varint
	 * value.
	 * 
	 * @param session The session used to store internal {@link SizeContext}.
	 * @param in The buffer used to read messages if contains enough data.
	 * @param out The output for messages decoded from data provided.
	 * 
	 * @see ProtobufEncoder
	 * @see SizeContext
	 */
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		SizeContext ctx = SizeContext.get(session, in);
		if(ctx.hasEnoughData(in)) {
			try	{
				decodeMessage(in, out, ctx);
				return true;
			} finally {
				ctx.shiftPositionAndReset(session, in);
			}
		}
		return false;
	}

	private void decodeMessage(IoBuffer in, ProtocolDecoderOutput out,
			SizeContext ctx) throws IOException {
		Message.Builder builder = newBuilder();
		ctx.getInputStream(in).readMessage(builder, ExtensionRegistry.getEmptyRegistry());
		out.write(builder.build());
	}
	protected abstract Message.Builder newBuilder();
}
