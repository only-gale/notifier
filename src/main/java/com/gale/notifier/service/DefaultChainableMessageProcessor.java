package com.gale.notifier.service;


import com.gale.notifier.BaseMessage;
import com.gale.notifier.ConsumerResponse;

/**
 * @author thatisgale@gmail.com
 * @since 2023/3/20 17:21
 */
public class DefaultChainableMessageProcessor extends AbstractChainableMessageProcessor<BaseMessage, ConsumerResponse, MessageProcessor<BaseMessage, ConsumerResponse>>{

    public DefaultChainableMessageProcessor(MessageProcessor<BaseMessage, ConsumerResponse> service) {
        super(service);
    }

}
