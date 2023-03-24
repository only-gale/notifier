package com.gale.notifier.service;

/**
 * 处理器类型，接收T，返回R
 * @author thatisgale@gmail.com
 * @since 2023/3/18 14:16
 */
public interface Processor<T, R> {
    R process(T data) throws Exception;

    /**
     * data是否可被当前处理器处理
     * @param data 一般是消息数据
     * @return boolean
     */
    boolean isSupported(T data);
}
