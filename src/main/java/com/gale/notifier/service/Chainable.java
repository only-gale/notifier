package com.gale.notifier.service;

/**
 * 责任链：可链接类型
 * @author thatisgale@gmail.com
 * @since 2023/3/18 15:24
 */
public interface Chainable<N> {

    /**
     * 设置下一个节点
     * @param next 下一个节点
     */
    void setNext(Chainable<N> next);

    /**
     * 获取下一个节点
     * @return Chainable
     */
    Chainable<N> getNext();

    /**
     * 获取节点服务
     * @return N
     */
    N getService();
}
