package com.gale.notifier.server;

import com.gale.notifier.BaseMessage;
import com.gale.notifier.ConsumerResponse;
import com.gale.notifier.NotifierGrpc;
import com.gale.notifier.Response;
import com.gale.notifier.service.DefaultChainableMessageProcessor;
import com.gale.notifier.service.MessageProcessorService;
import com.gale.notifier.service.NopMessageProcessor;
import com.gale.notifier.service.NotifyMessageProcessor;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * grpc通知服务
 * @author thatisgale@gmail.com
 * @since 2023/3/18 12:28
 */
@Slf4j
@GrpcService
public class NotifyService extends NotifierGrpc.NotifierImplBase {

    private final MessageProcessorService<BaseMessage, ConsumerResponse> messageProcessorService;

    public NotifyService() {
        DefaultChainableMessageProcessor chainableNotifyMessageProcessor = new DefaultChainableMessageProcessor(new NotifyMessageProcessor());
        DefaultChainableMessageProcessor chainableNopMessageProcessor = new DefaultChainableMessageProcessor(new NopMessageProcessor());
        chainableNotifyMessageProcessor.setNext(chainableNopMessageProcessor);
        messageProcessorService = new MessageProcessorService<>(chainableNotifyMessageProcessor);
    }

    @Override
    public StreamObserver<BaseMessage> send(StreamObserver<Response> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(BaseMessage message) {
                // 处理消息
                try {
                    CompletableFuture<Response> future = messageProcessorService.process(message);
                    future.whenComplete((ack, throwable) -> {
                        if (Objects.nonNull(ack)) {
                            log.info("received ack: {}", ack);
                        }
                    });
                    responseObserver.onNext(future.get());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(Throwable t) {
                log.error("Client has stopped to send messages", t);
            }

            @Override
            public void onCompleted() {
                // 客户端发送完了
                responseObserver.onCompleted();
                log.info("server completed");
            }
        };
    }

}
