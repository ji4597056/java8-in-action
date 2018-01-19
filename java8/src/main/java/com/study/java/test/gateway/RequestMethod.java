package com.study.java.test.gateway;

/**
 * @author Jeffrey
 * @since 2017/10/13 16:54
 */
public enum RequestMethod {
    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    private RequestMethod() {
    }
}
