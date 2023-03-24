package com.gale.notifier.service;

/**
 * @author thatisgale@gmail.com
 * @since 2023/3/20 16:44
 */
public interface MessageProcessor<T, R> extends CompletableProcessor<T, R> {
    boolean isSupported(T message);
}
