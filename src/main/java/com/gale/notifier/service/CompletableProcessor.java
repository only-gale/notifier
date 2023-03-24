package com.gale.notifier.service;

import java.util.concurrent.CompletableFuture;

/**
 * @author thatisgale@gmail.com
 * @since 2023/3/18 15:11
 */
public interface CompletableProcessor<T, R> extends Processor<T, R> {
    CompletableFuture<R> asyncProcess(T data);
}
