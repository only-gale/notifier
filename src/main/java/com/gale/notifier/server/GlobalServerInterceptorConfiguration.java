package com.gale.notifier.server;

import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * @author thatisgale@gmail.com
 * @since 2023/3/21 11:25
 */
@Configuration(proxyBeanMethods = false)
public class GlobalServerInterceptorConfiguration {

    @GrpcGlobalServerInterceptor
    LogGrpcInterceptor logServerInterceptor() {
        return new LogGrpcInterceptor();
    }
}
