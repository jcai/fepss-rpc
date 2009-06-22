// Generated by the protocol buffer compiler.  DO NOT EDIT!

package com.fepss.rpc.client;

public final class RpcProtobuf {
  private RpcProtobuf() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public static enum ErrorReason
      implements com.google.protobuf.ProtocolMessageEnum {
    BAD_REQUEST_DATA(0, 0),
    BAD_REQUEST_PROTO(1, 1),
    SERVICE_NOT_FOUND(2, 2),
    METHOD_NOT_FOUND(3, 3),
    RPC_ERROR(4, 4),
    RPC_FAILED(5, 5),
    ;
    
    
    public final int getNumber() { return value; }
    
    public static ErrorReason valueOf(int value) {
      switch (value) {
        case 0: return BAD_REQUEST_DATA;
        case 1: return BAD_REQUEST_PROTO;
        case 2: return SERVICE_NOT_FOUND;
        case 3: return METHOD_NOT_FOUND;
        case 4: return RPC_ERROR;
        case 5: return RPC_FAILED;
        default: return null;
      }
    }
    
    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.fepss.rpc.client.RpcProtobuf.getDescriptor().getEnumTypes().get(0);
    }
    
    private static final ErrorReason[] VALUES = {
      BAD_REQUEST_DATA, BAD_REQUEST_PROTO, SERVICE_NOT_FOUND, METHOD_NOT_FOUND, RPC_ERROR, RPC_FAILED, 
    };
    public static ErrorReason valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }
    private final int index;
    private final int value;
    private ErrorReason(int index, int value) {
      this.index = index;
      this.value = value;
    }
    
    static {
      com.fepss.rpc.client.RpcProtobuf.getDescriptor();
    }
  }
  
  public static final class Request extends
      com.google.protobuf.GeneratedMessage {
    // Use Request.newBuilder() to construct.
    private Request() {}
    
    private static final Request defaultInstance = new Request();
    public static Request getDefaultInstance() {
      return defaultInstance;
    }
    
    public Request getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.fepss.rpc.client.RpcProtobuf.internal_static_com_fepss_rpc_client_Request_descriptor;
    }
    
    @Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.fepss.rpc.client.RpcProtobuf.internal_static_com_fepss_rpc_client_Request_fieldAccessorTable;
    }
    
    // required string service_name = 1;
    public static final int SERVICE_NAME_FIELD_NUMBER = 1;
    private boolean hasServiceName;
    private java.lang.String serviceName_ = "";
    public boolean hasServiceName() { return hasServiceName; }
    public java.lang.String getServiceName() { return serviceName_; }
    
    // required string method_name = 2;
    public static final int METHOD_NAME_FIELD_NUMBER = 2;
    private boolean hasMethodName;
    private java.lang.String methodName_ = "";
    public boolean hasMethodName() { return hasMethodName; }
    public java.lang.String getMethodName() { return methodName_; }
    
    // required bytes request_proto = 3;
    public static final int REQUEST_PROTO_FIELD_NUMBER = 3;
    private boolean hasRequestProto;
    private com.google.protobuf.ByteString requestProto_ = com.google.protobuf.ByteString.EMPTY;
    public boolean hasRequestProto() { return hasRequestProto; }
    public com.google.protobuf.ByteString getRequestProto() { return requestProto_; }
    
    @Override
    public final boolean isInitialized() {
      if (!hasServiceName) return false;
      if (!hasMethodName) return false;
      if (!hasRequestProto) return false;
      return true;
    }
    
    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (hasServiceName()) {
        output.writeString(1, getServiceName());
      }
      if (hasMethodName()) {
        output.writeString(2, getMethodName());
      }
      if (hasRequestProto()) {
        output.writeBytes(3, getRequestProto());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    @Override
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasServiceName()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(1, getServiceName());
      }
      if (hasMethodName()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getMethodName());
      }
      if (hasRequestProto()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, getRequestProto());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static com.fepss.rpc.client.RpcProtobuf.Request parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Request parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Request parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Request parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Request parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Request parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Request parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input).buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Request parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Request parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Request parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return new Builder(); }
    public Builder newBuilderForType() { return new Builder(); }
    public static Builder newBuilder(com.fepss.rpc.client.RpcProtobuf.Request prototype) {
      return new Builder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      // Construct using com.fepss.rpc.client.RpcProtobuf.Request.newBuilder()
      private Builder() {}
      
      com.fepss.rpc.client.RpcProtobuf.Request result = new com.fepss.rpc.client.RpcProtobuf.Request();
      
      @Override
      protected com.fepss.rpc.client.RpcProtobuf.Request internalGetResult() {
        return result;
      }
      
      @Override
      public Builder clear() {
        result = new com.fepss.rpc.client.RpcProtobuf.Request();
        return this;
      }
      
      @Override
      public Builder clone() {
        return new Builder().mergeFrom(result);
      }
      
      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.fepss.rpc.client.RpcProtobuf.Request.getDescriptor();
      }
      
      public com.fepss.rpc.client.RpcProtobuf.Request getDefaultInstanceForType() {
        return com.fepss.rpc.client.RpcProtobuf.Request.getDefaultInstance();
      }
      
      public com.fepss.rpc.client.RpcProtobuf.Request build() {
        if (result != null && !isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result);
        }
        return buildPartial();
      }
      
      private com.fepss.rpc.client.RpcProtobuf.Request buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public com.fepss.rpc.client.RpcProtobuf.Request buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");  }
        com.fepss.rpc.client.RpcProtobuf.Request returnMe = result;
        result = null;
        return returnMe;
      }
      
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.fepss.rpc.client.RpcProtobuf.Request) {
          return mergeFrom((com.fepss.rpc.client.RpcProtobuf.Request)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.fepss.rpc.client.RpcProtobuf.Request other) {
        if (other == com.fepss.rpc.client.RpcProtobuf.Request.getDefaultInstance()) return this;
        if (other.hasServiceName()) {
          setServiceName(other.getServiceName());
        }
        if (other.hasMethodName()) {
          setMethodName(other.getMethodName());
        }
        if (other.hasRequestProto()) {
          setRequestProto(other.getRequestProto());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
        return mergeFrom(input,
          com.google.protobuf.ExtensionRegistry.getEmptyRegistry());
      }
      
      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistry extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10: {
              setServiceName(input.readString());
              break;
            }
            case 18: {
              setMethodName(input.readString());
              break;
            }
            case 26: {
              setRequestProto(input.readBytes());
              break;
            }
          }
        }
      }
      
      
      // required string service_name = 1;
      public boolean hasServiceName() {
        return result.hasServiceName();
      }
      public java.lang.String getServiceName() {
        return result.getServiceName();
      }
      public Builder setServiceName(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasServiceName = true;
        result.serviceName_ = value;
        return this;
      }
      public Builder clearServiceName() {
        result.hasServiceName = false;
        result.serviceName_ = "";
        return this;
      }
      
      // required string method_name = 2;
      public boolean hasMethodName() {
        return result.hasMethodName();
      }
      public java.lang.String getMethodName() {
        return result.getMethodName();
      }
      public Builder setMethodName(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasMethodName = true;
        result.methodName_ = value;
        return this;
      }
      public Builder clearMethodName() {
        result.hasMethodName = false;
        result.methodName_ = "";
        return this;
      }
      
      // required bytes request_proto = 3;
      public boolean hasRequestProto() {
        return result.hasRequestProto();
      }
      public com.google.protobuf.ByteString getRequestProto() {
        return result.getRequestProto();
      }
      public Builder setRequestProto(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasRequestProto = true;
        result.requestProto_ = value;
        return this;
      }
      public Builder clearRequestProto() {
        result.hasRequestProto = false;
        result.requestProto_ = com.google.protobuf.ByteString.EMPTY;
        return this;
      }
    }
    
    static {
      com.fepss.rpc.client.RpcProtobuf.getDescriptor();
    }
  }
  
  public static final class Response extends
      com.google.protobuf.GeneratedMessage {
    // Use Response.newBuilder() to construct.
    private Response() {}
    
    private static final Response defaultInstance = new Response();
    public static Response getDefaultInstance() {
      return defaultInstance;
    }
    
    public Response getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.fepss.rpc.client.RpcProtobuf.internal_static_com_fepss_rpc_client_Response_descriptor;
    }
    
    @Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.fepss.rpc.client.RpcProtobuf.internal_static_com_fepss_rpc_client_Response_fieldAccessorTable;
    }
    
    // optional bytes response_proto = 1;
    public static final int RESPONSE_PROTO_FIELD_NUMBER = 1;
    private boolean hasResponseProto;
    private com.google.protobuf.ByteString responseProto_ = com.google.protobuf.ByteString.EMPTY;
    public boolean hasResponseProto() { return hasResponseProto; }
    public com.google.protobuf.ByteString getResponseProto() { return responseProto_; }
    
    // optional string error = 2;
    public static final int ERROR_FIELD_NUMBER = 2;
    private boolean hasError;
    private java.lang.String error_ = "";
    public boolean hasError() { return hasError; }
    public java.lang.String getError() { return error_; }
    
    // optional bool callback = 3 [default = false];
    public static final int CALLBACK_FIELD_NUMBER = 3;
    private boolean hasCallback;
    private boolean callback_ = false;
    public boolean hasCallback() { return hasCallback; }
    public boolean getCallback() { return callback_; }
    
    // optional .com.fepss.rpc.client.ErrorReason error_reason = 4;
    public static final int ERROR_REASON_FIELD_NUMBER = 4;
    private boolean hasErrorReason;
    private com.fepss.rpc.client.RpcProtobuf.ErrorReason errorReason_ = com.fepss.rpc.client.RpcProtobuf.ErrorReason.BAD_REQUEST_DATA;
    public boolean hasErrorReason() { return hasErrorReason; }
    public com.fepss.rpc.client.RpcProtobuf.ErrorReason getErrorReason() { return errorReason_; }
    
    @Override
    public final boolean isInitialized() {
      return true;
    }
    
    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (hasResponseProto()) {
        output.writeBytes(1, getResponseProto());
      }
      if (hasError()) {
        output.writeString(2, getError());
      }
      if (hasCallback()) {
        output.writeBool(3, getCallback());
      }
      if (hasErrorReason()) {
        output.writeEnum(4, getErrorReason().getNumber());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    @Override
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasResponseProto()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getResponseProto());
      }
      if (hasError()) {
        size += com.google.protobuf.CodedOutputStream
          .computeStringSize(2, getError());
      }
      if (hasCallback()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(3, getCallback());
      }
      if (hasErrorReason()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(4, getErrorReason().getNumber());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static com.fepss.rpc.client.RpcProtobuf.Response parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Response parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Response parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Response parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Response parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Response parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Response parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input).buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Response parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Response parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.fepss.rpc.client.RpcProtobuf.Response parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistry extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return new Builder(); }
    public Builder newBuilderForType() { return new Builder(); }
    public static Builder newBuilder(com.fepss.rpc.client.RpcProtobuf.Response prototype) {
      return new Builder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      // Construct using com.fepss.rpc.client.RpcProtobuf.Response.newBuilder()
      private Builder() {}
      
      com.fepss.rpc.client.RpcProtobuf.Response result = new com.fepss.rpc.client.RpcProtobuf.Response();
      
      @Override
      protected com.fepss.rpc.client.RpcProtobuf.Response internalGetResult() {
        return result;
      }
      
      @Override
      public Builder clear() {
        result = new com.fepss.rpc.client.RpcProtobuf.Response();
        return this;
      }
      
      @Override
      public Builder clone() {
        return new Builder().mergeFrom(result);
      }
      
      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.fepss.rpc.client.RpcProtobuf.Response.getDescriptor();
      }
      
      public com.fepss.rpc.client.RpcProtobuf.Response getDefaultInstanceForType() {
        return com.fepss.rpc.client.RpcProtobuf.Response.getDefaultInstance();
      }
      
      public com.fepss.rpc.client.RpcProtobuf.Response build() {
        if (result != null && !isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result);
        }
        return buildPartial();
      }
      
      private com.fepss.rpc.client.RpcProtobuf.Response buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw new com.google.protobuf.UninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public com.fepss.rpc.client.RpcProtobuf.Response buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");  }
        com.fepss.rpc.client.RpcProtobuf.Response returnMe = result;
        result = null;
        return returnMe;
      }
      
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.fepss.rpc.client.RpcProtobuf.Response) {
          return mergeFrom((com.fepss.rpc.client.RpcProtobuf.Response)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.fepss.rpc.client.RpcProtobuf.Response other) {
        if (other == com.fepss.rpc.client.RpcProtobuf.Response.getDefaultInstance()) return this;
        if (other.hasResponseProto()) {
          setResponseProto(other.getResponseProto());
        }
        if (other.hasError()) {
          setError(other.getError());
        }
        if (other.hasCallback()) {
          setCallback(other.getCallback());
        }
        if (other.hasErrorReason()) {
          setErrorReason(other.getErrorReason());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input)
          throws java.io.IOException {
        return mergeFrom(input,
          com.google.protobuf.ExtensionRegistry.getEmptyRegistry());
      }
      
      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistry extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10: {
              setResponseProto(input.readBytes());
              break;
            }
            case 18: {
              setError(input.readString());
              break;
            }
            case 24: {
              setCallback(input.readBool());
              break;
            }
            case 32: {
              int rawValue = input.readEnum();
              com.fepss.rpc.client.RpcProtobuf.ErrorReason value = com.fepss.rpc.client.RpcProtobuf.ErrorReason.valueOf(rawValue);
              if (value == null) {
                unknownFields.mergeVarintField(4, rawValue);
              } else {
                setErrorReason(value);
              }
              break;
            }
          }
        }
      }
      
      
      // optional bytes response_proto = 1;
      public boolean hasResponseProto() {
        return result.hasResponseProto();
      }
      public com.google.protobuf.ByteString getResponseProto() {
        return result.getResponseProto();
      }
      public Builder setResponseProto(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasResponseProto = true;
        result.responseProto_ = value;
        return this;
      }
      public Builder clearResponseProto() {
        result.hasResponseProto = false;
        result.responseProto_ = com.google.protobuf.ByteString.EMPTY;
        return this;
      }
      
      // optional string error = 2;
      public boolean hasError() {
        return result.hasError();
      }
      public java.lang.String getError() {
        return result.getError();
      }
      public Builder setError(java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  result.hasError = true;
        result.error_ = value;
        return this;
      }
      public Builder clearError() {
        result.hasError = false;
        result.error_ = "";
        return this;
      }
      
      // optional bool callback = 3 [default = false];
      public boolean hasCallback() {
        return result.hasCallback();
      }
      public boolean getCallback() {
        return result.getCallback();
      }
      public Builder setCallback(boolean value) {
        result.hasCallback = true;
        result.callback_ = value;
        return this;
      }
      public Builder clearCallback() {
        result.hasCallback = false;
        result.callback_ = false;
        return this;
      }
      
      // optional .com.fepss.rpc.client.ErrorReason error_reason = 4;
      public boolean hasErrorReason() {
        return result.hasErrorReason();
      }
      public com.fepss.rpc.client.RpcProtobuf.ErrorReason getErrorReason() {
        return result.getErrorReason();
      }
      public Builder setErrorReason(com.fepss.rpc.client.RpcProtobuf.ErrorReason value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasErrorReason = true;
        result.errorReason_ = value;
        return this;
      }
      public Builder clearErrorReason() {
        result.hasErrorReason = false;
        result.errorReason_ = com.fepss.rpc.client.RpcProtobuf.ErrorReason.BAD_REQUEST_DATA;
        return this;
      }
    }
    
    static {
      com.fepss.rpc.client.RpcProtobuf.getDescriptor();
    }
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_fepss_rpc_client_Request_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_fepss_rpc_client_Request_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_fepss_rpc_client_Response_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_fepss_rpc_client_Response_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String descriptorData =
      "\n,src/main/java/com/fepss/rpc/client/rpc" +
      ".proto\022\024com.fepss.rpc.client\"K\n\007Request\022" +
      "\024\n\014service_name\030\001 \002(\t\022\023\n\013method_name\030\002 \002" +
      "(\t\022\025\n\rrequest_proto\030\003 \002(\014\"\203\001\n\010Response\022\026" +
      "\n\016response_proto\030\001 \001(\014\022\r\n\005error\030\002 \001(\t\022\027\n" +
      "\010callback\030\003 \001(\010:\005false\0227\n\014error_reason\030\004" +
      " \001(\0162!.com.fepss.rpc.client.ErrorReason*" +
      "\206\001\n\013ErrorReason\022\024\n\020BAD_REQUEST_DATA\020\000\022\025\n" +
      "\021BAD_REQUEST_PROTO\020\001\022\025\n\021SERVICE_NOT_FOUN" +
      "D\020\002\022\024\n\020METHOD_NOT_FOUND\020\003\022\r\n\tRPC_ERROR\020\004" +
      "\022\016\n\nRPC_FAILED\020\005B%\n\024com.fepss.rpc.client" +
      "B\013RpcProtobufH\001";
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_fepss_rpc_client_Request_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_fepss_rpc_client_Request_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_fepss_rpc_client_Request_descriptor,
              new java.lang.String[] { "ServiceName", "MethodName", "RequestProto", },
              com.fepss.rpc.client.RpcProtobuf.Request.class,
              com.fepss.rpc.client.RpcProtobuf.Request.Builder.class);
          internal_static_com_fepss_rpc_client_Response_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_com_fepss_rpc_client_Response_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_fepss_rpc_client_Response_descriptor,
              new java.lang.String[] { "ResponseProto", "Error", "Callback", "ErrorReason", },
              com.fepss.rpc.client.RpcProtobuf.Response.class,
              com.fepss.rpc.client.RpcProtobuf.Response.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
}