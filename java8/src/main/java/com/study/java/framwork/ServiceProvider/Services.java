package com.study.java.framwork.ServiceProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务注册中心
 *
 * @author Jeffrey
 * @since 2017/04/01 19:40
 */
public class Services {

    private static final Map<String, Provider> providers = new ConcurrentHashMap<>();

    private static final String DEFAULT_PROVIDER_NAME = "provider";

    private Services() {

    }

    /**
     * 注册服务提供者
     */
    public static void RegisterProvider(Provider provider) {
        providers.put(DEFAULT_PROVIDER_NAME, provider);
    }

    public static void RegisterProvider(String name, Provider provider) {
        providers.put(name, provider);
    }

    /**
     * 获取默认服务
     */
    public static Service newInstance() {
        return newInstance(DEFAULT_PROVIDER_NAME);
    }

    /**
     * 根据name获取服务
     */
    public static Service newInstance(String name) {
        Provider provider = providers.get(name);
        if (provider == null) {
            throw new IllegalArgumentException("No provider registered with name: " + name);
        }
        return provider.newService();
    }

}
