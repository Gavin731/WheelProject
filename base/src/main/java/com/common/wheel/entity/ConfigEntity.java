package com.common.wheel.entity;

public class ConfigEntity {

    private String configKey;
    private Object configStatus;
    private String configValue;

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public Object getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(Object configStatus) {
        this.configStatus = configStatus;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
