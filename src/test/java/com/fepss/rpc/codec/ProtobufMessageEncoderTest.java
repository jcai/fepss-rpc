package com.fepss.rpc.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import com.fepss.rpc.RpcProtobuf.Request;
import com.fepss.rpc.test.TestProto.User;
import com.google.protobuf.Message;

public class ProtobufMessageEncoderTest {

	@Test
	public void testProtobufMessage() throws Exception {
		ProtocolEncoderOutput out = EasyMock
				.createMock(ProtocolEncoderOutput.class);
		IoSession session = EasyMock.createMock(IoSession.class);
		out.write(EasyMock.anyObject());
		EasyMock.replay(out, session);
		
		User user= User.newBuilder().setUserName("jcai").build();
		Message message = Request.newBuilder().setServiceName("TestService")
				.setMethodName("testMethod").setRequestProto(user.toByteString()).build();
		
		ProtobufMessageEncoder encoder = new ProtobufMessageEncoder();
		encoder.encode(session, message, out);
		
		EasyMock.verify(out,session);

	}
}
