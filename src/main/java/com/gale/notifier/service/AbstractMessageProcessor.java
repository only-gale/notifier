package com.gale.notifier.service;

import com.gale.notifier.BaseMessage;
import com.gale.notifier.ConsumerResponse;
import com.gale.notifier.MessageType;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author thatisgale@gmail.com
 * @since 2023/3/20 17:11
 */
public abstract class AbstractMessageProcessor<T extends BaseMessage, R extends ConsumerResponse> implements MessageProcessor<T, R> {
    protected final List<MessageType> supportedMessageTypes;

    protected AbstractMessageProcessor(List<MessageType> supportedMessageTypes) {
        this.supportedMessageTypes = supportedMessageTypes;
    }

    @Override
    public boolean isSupported(T message) {
        return !CollectionUtils.isEmpty(supportedMessageTypes) && Objects.nonNull(message) && supportedMessageTypes.contains(message.getMessageType());
    }

    @Override
    public CompletableFuture<R> asyncProcess(T message) {
        if (Objects.isNull(message) || !isSupported(message)) {
            return CompletableFuture.completedFuture(null);
        }
        return doAsyncProcess(message);
    }

    @Override
    public R process(T message) throws Exception {
        if (Objects.isNull(message) || !isSupported(message)) {
            return null;
        }
        return doProcess(message);
    }

    protected abstract CompletableFuture<R> doAsyncProcess(T message);
    protected abstract R doProcess(T message);
}
