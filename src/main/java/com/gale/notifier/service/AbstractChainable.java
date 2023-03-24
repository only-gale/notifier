package com.gale.notifier.service;

import java.util.Objects;

/**
 * @author thatisgale@gmail.com
 * @since 2023/3/20 14:00
 */
public abstract class AbstractChainable<N> implements Chainable<N> {
    protected final N service;
    protected Chainable<N> next;

    protected AbstractChainable(N service) {
        if (Objects.isNull(service)) {
            throw new IllegalArgumentException("service is null");
        }
        this.service = service;
        this.next = null;
    }

    @Override
    public void setNext(Chainable<N> next) {
        this.next = next;
    }

    @Override
    public Chainable<N> getNext() {
        return this.next;
    }

    @Override
    public N getService() {
        return this.service;
    }

}
