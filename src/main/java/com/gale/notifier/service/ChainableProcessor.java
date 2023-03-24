package com.gale.notifier.service;

/**
 * 可链接的处理器
 * @author thatisgale@gmail.com
 * @since 2023/3/18 15:24
 */
public abstract class ChainableProcessor<T, R, P extends Processor<T, R>> extends AbstractChainable<P> {

    protected ChainableProcessor(P service) {
        super(service);
    }

}
