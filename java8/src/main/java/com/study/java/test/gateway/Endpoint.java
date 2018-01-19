package com.study.java.test.gateway;

import java.util.Date;

/**
 * Created by zoufeng on 2017/10/13.
 *
 * 接口（接入点）对象
 */
public class Endpoint {

    private Long endpointId;

    private Long serviceId;

    private RequestMethod operationType;

    private String endpointName;

    private String endpointURL;

    private boolean isEnable;

    private Date createTime;

    private Date expireTime;

    private boolean isOperationLog;

    private String version;


    public Long getEndpointId() {
        return endpointId;
    }

    public Endpoint setEndpointId(Long endpointId) {
        this.endpointId = endpointId;
        return this;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public Endpoint setServiceId(Long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public RequestMethod getOperationType() {
        return operationType;
    }

    public Endpoint setOperationType(RequestMethod operationType) {
        this.operationType = operationType;
        return this;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public Endpoint setEndpointName(String endpointName) {
        this.endpointName = endpointName;
        return this;
    }

    public String getEndpointURL() {
        return endpointURL;
    }

    public Endpoint setEndpointURL(String endpointURL) {
        this.endpointURL = endpointURL;
        return this;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public Endpoint setEnable(boolean enable) {
        isEnable = enable;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Endpoint setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public Endpoint setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public boolean isOperationLog() {
        return isOperationLog;
    }

    public Endpoint setOperationLog(boolean operationLog) {
        isOperationLog = operationLog;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Endpoint setVersion(String version) {
        this.version = version;
        return this;
    }
}
