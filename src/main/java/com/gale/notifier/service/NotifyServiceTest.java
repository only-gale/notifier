package com.gale.notifier.service;

import com.gale.notifier.BaseMessage;
import com.gale.notifier.ConsumerResponse;
import com.gale.notifier.MessageType;
import com.gale.notifier.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author thatisgale@gmail.com
 * @since 2023/3/20 18:24
 */
@Slf4j
public class NotifyServiceTest {
    private final MessageProcessorService<BaseMessage, ConsumerResponse> messageProcessorService;

    public NotifyServiceTest() {
        DefaultChainableMessageProcessor chainableNotifyMessageProcessor = new DefaultChainableMessageProcessor(new NotifyMessageProcessor());
        DefaultChainableMessageProcessor chainableNopMessageProcessor = new DefaultChainableMessageProcessor(new NopMessageProcessor());
        chainableNotifyMessageProcessor.setNext(chainableNopMessageProcessor);
        messageProcessorService = new MessageProcessorService<>(chainableNotifyMessageProcessor);
    }

    public void test(BaseMessage message) throws ExecutionException, InterruptedException {
        CompletableFuture<Response> future = messageProcessorService.process(message);
        future.whenComplete((ack, throwable) -> {
            if (Objects.nonNull(ack)) {
                log.info("received ack: {}", ack);
            }
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NotifyServiceTest notifyServiceTest = new NotifyServiceTest();
        notifyServiceTest.test(BaseMessage.newBuilder().setMessageId("1").setContent("test").setSendTime(123123).setMessageType(MessageType.HEART_BEAT).build());
    }
}
