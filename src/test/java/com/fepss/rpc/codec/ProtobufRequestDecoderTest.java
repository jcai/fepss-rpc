package com.fepss.rpc.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import com.fepss.rpc.RpcProtobuf.Request;
import com.fepss.rpc.test.TestProto.User;
import com.google.protobuf.Message;

public class ProtobufRequestDecoderTest {
	@Test
	public void testDecoder() throws Exception {
		ProtocolDecoderOutput out = EasyMock
				.createMock(ProtocolDecoderOutput.class);
		IoSession session = EasyMock.createMock(IoSession.class);
		out.write(EasyMock.anyObject());
		EasyMock.replay(out, session);
		
		User user= User.newBuilder().setUserName("jcai").build();
		Message message = Request.newBuilder().setServiceName("TestService")
				.setMethodName("testMethod").setRequestProto(user.toByteString()).build();
		
		ProtobufRequestDecoder decoder= new ProtobufRequestDecoder();
		IoBuffer ioBuf =	IoBuffer.wrap(message.toByteArray());
		decoder.decode(session, ioBuf, out);
		
		EasyMock.verify(out,session);

	}

}
