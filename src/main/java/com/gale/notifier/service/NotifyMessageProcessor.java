package com.gale.notifier.service;

import com.gale.notifier.BaseMessage;
import com.gale.notifier.ConsumerResponse;
import com.gale.notifier.MessageType;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 通知型消息处理器
 * @author thatisgale@gmail.com
 * @since 2023/3/20 14:40
 */
@Slf4j
@Component
public class NotifyMessageProcessor extends AbstractMessageProcessor<BaseMessage, ConsumerResponse> {
    public static final List<MessageType> supportedMessageTypes = Arrays.asList(MessageType.HEART_BEAT, MessageType.CREATE_SPACE);

    public NotifyMessageProcessor() {
        super(supportedMessageTypes);
    }

    @Override
    protected CompletableFuture<ConsumerResponse> doAsyncProcess(BaseMessage message) {
        try {
            log.info("NotifyMessageProcessor收到一条消息：{}", JsonFormat.printer().print(message));
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(ConsumerResponse.newBuilder().setMessageId(message.getMessageId()).setConsumeTime(Instant.now().toEpochMilli()).setSendTime(message.getSendTime()).setContent("finished").build());
    }

    @Override
    protected ConsumerResponse doProcess(BaseMessage message) {
        try {
            log.info("NotifyMessageProcessor收到一条消息：{}", JsonFormat.printer().print(message));
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
        return ConsumerResponse.newBuilder().setMessageId(message.getMessageId()).setConsumeTime(Instant.now().toEpochMilli()).setSendTime(message.getSendTime()).setContent("finished").build();
    }

}
