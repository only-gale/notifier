package com.gale.notifier.service;


import com.gale.notifier.BaseMessage;
import com.gale.notifier.ConsumerResponse;
import com.gale.notifier.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 消息处理服务
 * @author thatisgale@gmail.com
 * @since 2023/3/20 16:09
 */
public class MessageProcessorService<T extends BaseMessage, R extends ConsumerResponse> {
    private final AbstractChainable<MessageProcessor<T, R>> chainableProcessor;

    public MessageProcessorService(ChainableProcessor<T, R, MessageProcessor<T, R>> chainableProcessor) {
        this.chainableProcessor = chainableProcessor;
    }

    public CompletableFuture<Response> process(T message) throws ExecutionException, InterruptedException {
        Response.Builder builder = Response.newBuilder();
        List<CompletableFuture<R>> processors = new ArrayList<>();
        AbstractChainable<MessageProcessor<T, R>> chainableProcessor = this.chainableProcessor;
        do {
            processors.add(chainableProcessor.getService().asyncProcess(message));
            if (chainableProcessor.getNext() instanceof AbstractChainable<MessageProcessor<T, R>> next
                    && next.getService() != null
                    && next.getService().isSupported(message)) {
                chainableProcessor = next;
            } else {
                chainableProcessor = null;
            }
        } while (chainableProcessor != null);

        CompletableFuture.allOf(processors.toArray(new CompletableFuture[]{})).thenRunAsync(() -> processors.forEach(r -> {
            try {
                builder.addConsumerResponses(r.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        })).get();
        return CompletableFuture.completedFuture(builder.build());
    }

}
