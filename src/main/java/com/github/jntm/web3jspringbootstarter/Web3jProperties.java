package com.github.jntm.web3jspringbootstarter;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = Web3jProperties.WEB3J_PREFIX)
public class Web3jProperties {
    private String gasProvider;

    public String getGasProvider() {
        return gasProvider;
    }

    public void setGasProvider(String gasProvider) {
        this.gasProvider = gasProvider;
    }

    private String accountAddr;
    private String secrete;

    public String getAccountAddr() {
        return accountAddr;
    }

    public void setAccountAddr(String accountAddr) {
        this.accountAddr = accountAddr;
    }

    public String getSecrete() {
        return secrete;
    }

    public void setSecrete(String secrete) {
        this.secrete = secrete;
    }

    public static final String WEB3J_PREFIX = "web3j";

    private static String getWeb3jPrefix() {
        return WEB3J_PREFIX;
    }

    private String getScanPackage() {
        return scanPackage;
    }

    private void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }

    private String scanPackage;

    private String serverAddress;

    private Boolean adminClient;

    private String networkId;

    private Long httpTimeoutSeconds = 500L;

    private Boolean includeRawResponse = false;

    private Map<String, String> contracts;


    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public Boolean getAdminClient() {
        return adminClient;
    }

    public void setAdminClient(Boolean adminClient) {
        this.adminClient = adminClient;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public Long getHttpTimeoutSeconds() {
        return httpTimeoutSeconds;
    }

    public void setHttpTimeoutSeconds(Long httpTimeoutSeconds) {
        this.httpTimeoutSeconds = httpTimeoutSeconds;
    }

    public Boolean getIncludeRawResponse() {
        return includeRawResponse;
    }

    public void setIncludeRawResponse(Boolean includeRawResponse) {
        this.includeRawResponse = includeRawResponse;
    }

    public Map<String, String> getContracts() {
        return contracts;
    }

    public void setContracts(Map<String, String> contracts) {
        this.contracts = contracts;
    }
}
