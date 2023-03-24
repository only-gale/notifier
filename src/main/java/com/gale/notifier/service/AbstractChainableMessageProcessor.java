package com.gale.notifier.service;


import com.gale.notifier.BaseMessage;
import com.gale.notifier.ConsumerResponse;

/**
 * @author thatisgale@gmail.com
 * @since 2023/3/20 16:59
 */
public abstract class AbstractChainableMessageProcessor<T extends BaseMessage, R extends ConsumerResponse, MP extends MessageProcessor<T, R>> extends ChainableProcessor<T, R, MP>{
    public AbstractChainableMessageProcessor(MP service) {
        super(service);
    }


}
