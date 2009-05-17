package com.fepss.rpc.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import com.fepss.rpc.client.RpcProtobuf.Response;
import com.fepss.rpc.test.TestProto.Result;
import com.google.protobuf.Message;

public class ProtobufResponseDecoderTest {
	@Test
	public void testDecoder() throws Exception {
		ProtocolDecoderOutput out = EasyMock
				.createMock(ProtocolDecoderOutput.class);
		IoSession session = EasyMock.createMock(IoSession.class);
		out.write(EasyMock.anyObject());
		EasyMock.replay(out, session);
		
		Result result= Result.newBuilder().setResult("result").build();
		Message message = Response.newBuilder().setCallback(true).setResponseProto(result.toByteString()).build();
		
		ProtobufResponseDecoder decoder= new ProtobufResponseDecoder();
		IoBuffer ioBuf =	IoBuffer.wrap(message.toByteArray());
		decoder.decode(session, ioBuf, out);
		
		EasyMock.verify(out,session);

	}

}
