package com.gale.notifier.server;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author thatisgale@gmail.com
 * @since 2023/3/21 11:26
 */
@Slf4j
public class LogGrpcInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        log.info("{} has been called with metadata: {}", call.getMethodDescriptor().getFullMethodName(), headers);
        return next.startCall(call, headers);
    }

}
