package com.gale.notifier.service;

import com.gale.notifier.BaseMessage;
import com.gale.notifier.ConsumerResponse;
import com.gale.notifier.MessageType;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author thatisgale@gmail.com
 * @since 2023/3/20 18:17
 */
@Slf4j
@Component
public class NopMessageProcessor extends AbstractMessageProcessor<BaseMessage, ConsumerResponse> {
    public static final List<MessageType> supportedMessageTypes = Arrays.asList(MessageType.HEART_BEAT, MessageType.CREATE_SPACE);

    public NopMessageProcessor() {
        super(supportedMessageTypes);
    }

    @Override
    protected CompletableFuture<ConsumerResponse> doAsyncProcess(BaseMessage message) {
        try {
            log.info("NopMessageProcessor收到一条消息：{}", com.google.protobuf.util.JsonFormat.printer().print(message));
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(ConsumerResponse.newBuilder().setMessageId(message.getMessageId()).setConsumeTime(Instant.now().toEpochMilli()).setSendTime(message.getSendTime()).setContent("nop").build());
    }

    @Override
    protected ConsumerResponse doProcess(BaseMessage message) {
        try {
            log.info("NopMessageProcessor收到一条消息：{}", com.google.protobuf.util.JsonFormat.printer().print(message));
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
        return ConsumerResponse.newBuilder().setMessageId(message.getMessageId()).setConsumeTime(Instant.now().toEpochMilli()).setSendTime(message.getSendTime()).setContent("nop").build();
    }

}
