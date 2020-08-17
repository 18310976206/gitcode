package com.platform.au.entity;

import java.io.Serializable;

public class AcCustomer implements Serializable {
    private static final long serialVersionUID = 4194005810112216662L;
    private String customerId;
    private String domain;
    private String dsName;
    private String appName;
    private String server;
    private String client;
    private String wxCorpId;
    private String wxCorpSecret;
    private String wxAseKey;
    private int wxAgentId;
    private String wxToken;

    public AcCustomer(String customerId, String domain, String dsName, String appName, String server, String slient, String wxCorpId,
            String wxCorpSecret, String wxAseKey, int wxAgentId, String wxToken) {
        this.customerId = customerId;
        this.domain = domain;
        this.dsName = dsName;
        this.appName = appName;
        this.server = server;
        this.client = client;
        this.wxCorpId = wxCorpId;
        this.wxCorpSecret = wxCorpSecret;
        this.wxAgentId = wxAgentId;
        this.wxAseKey = wxAseKey;
        this.wxToken = wxToken;
    }

    public AcCustomer() {

    }

  
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getWxCorpId() {
        return wxCorpId;
    }

    public void setWxCorpId(String wxCorpId) {
        this.wxCorpId = wxCorpId;
    }

    public String getWxCorpSecret() {
        return wxCorpSecret;
    }

    public void setWxCorpSecret(String wxCorpSecret) {
        this.wxCorpSecret = wxCorpSecret;
    }

    public String getWxAseKey() {
        return wxAseKey;
    }

    public void setWxAseKey(String wxAseKey) {
        this.wxAseKey = wxAseKey;
    }

    public int getWxAgentId() {
        return wxAgentId;
    }

    public void setWxAgentId(int wxAgentId) {
        this.wxAgentId = wxAgentId;
    }

    public String getWxToken() {
        return wxToken;
    }

    public void setWxToken(String wxToken) {
        this.wxToken = wxToken;
    }
}
