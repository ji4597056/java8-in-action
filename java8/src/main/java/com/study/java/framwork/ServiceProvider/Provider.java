package com.study.java.framwork.ServiceProvider;

/**
 * 服务提供者
 *
 * @author Jeffrey
 * @since 2017/04/01 19:38
 */
public interface Provider {

    /**
     * 获取service(静态工厂)
     */
    Service newService();
}
