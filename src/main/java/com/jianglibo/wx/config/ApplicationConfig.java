﻿package com.jianglibo.wx.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Priority;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "myapp")
@Priority(1)
public class ApplicationConfig implements InitializingBean {
	
	private String dataWriteSourcePath;
	
	private String templateRoot;
	
	private String buildRoot;
	
	private String appId;
	
	private String appSecret;
	
	private boolean refreshWxToken;
	
	private Path templateRootPath;
	
	private Path buildRootPath;
	
	private String hdfsHost;
	
	private String zkQuoram;
	
	private String hdfsPort;
	
	private String antExec;
	
	private JwtConfig jwtConfig;
	
	private String hdfsFullUrlNoLastSlash;
	
	private String tProjectRoot;
	
	private String hadoopExecutable;
	
	private String hbaseRestHost;
	private int hbaseRestPort;
	private String hbaseRestProtocol;
	
	private boolean disableCsrf;
	
	private String hbaseRestPrefix;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.templateRootPath = Paths.get(getTemplateRoot()).normalize().toAbsolutePath();
		this.setBuildRootPath(Paths.get(getBuildRoot()).normalize().toAbsolutePath());
		this.setHbaseRestPrefix(String.format("%s://%s:%s", getHbaseRestProtocol(), getHbaseRestHost(), getHbaseRestPort()));
		if (getHdfsPort() == null || getHdfsPort().trim().isEmpty()) {
			this.setHdfsFullUrlNoLastSlash("hdfs://" + getHdfsHost());
		} else {
			this.setHdfsFullUrlNoLastSlash("hdfs://" + getHdfsHost() + ":" + getHdfsPort());
		}
	}

	public String getOutSideBaseUrl() {
		return null;
	}
	
	public String getDataWriteSourcePath() {
		return dataWriteSourcePath;
	}

	public void setDataWriteSourcePath(String dataWriteSourcePath) {
		this.dataWriteSourcePath = dataWriteSourcePath;
	}


	public String getTemplateRoot() {
		return templateRoot;
	}

	public void setTemplateRoot(String templateRoot) {
		this.templateRoot = templateRoot;
	}

	public Path getTemplateRootPath() {
		return templateRootPath;
	}

	public boolean isDisableCsrf() {
		return disableCsrf;
	}

	public void setDisableCsrf(boolean disableCsrf) {
		this.disableCsrf = disableCsrf;
	}

	public String getBuildRoot() {
		return buildRoot;
	}

	public void setBuildRoot(String buildRoot) {
		this.buildRoot = buildRoot;
	}

	public Path getBuildRootPath() {
		return buildRootPath;
	}

	public void setBuildRootPath(Path buildRootPath) {
		this.buildRootPath = buildRootPath;
	}

	public String getHbaseRestHost() {
		return hbaseRestHost;
	}

	public void setHbaseRestHost(String hbaseRestHost) {
		this.hbaseRestHost = hbaseRestHost;
	}

	public int getHbaseRestPort() {
		return hbaseRestPort;
	}

	public void setHbaseRestPort(int hbaseRestPort) {
		this.hbaseRestPort = hbaseRestPort;
	}

	public void setTemplateRootPath(Path templateRootPath) {
		this.templateRootPath = templateRootPath;
	}

	public String getHbaseRestProtocol() {
		return hbaseRestProtocol;
	}

	public void setHbaseRestProtocol(String hbaseRestProtocol) {
		this.hbaseRestProtocol = hbaseRestProtocol;
	}

	public String getHbaseRestPrefix() {
		return hbaseRestPrefix;
	}

	public void setHbaseRestPrefix(String hbaseRestPrefix) {
		this.hbaseRestPrefix = hbaseRestPrefix;
	}

	public String getHadoopExecutable() {
		return hadoopExecutable;
	}

	public void setHadoopExecutable(String hadoopExecutable) {
		this.hadoopExecutable = hadoopExecutable;
	}

	public String getHdfsHost() {
		return hdfsHost;
	}

	public void setHdfsHost(String hdfsHost) {
		this.hdfsHost = hdfsHost;
	}

	public String getHdfsPort() {
		return hdfsPort;
	}

	public void setHdfsPort(String hdfsPort) {
		this.hdfsPort = hdfsPort;
	}

	public String getAntExec() {
		return antExec;
	}

	public void setAntExec(String antExec) {
		this.antExec = antExec;
	}

	public String gettProjectRoot() {
		return tProjectRoot;
	}

	public void settProjectRoot(String tProjectRoot) {
		this.tProjectRoot = tProjectRoot;
	}

	public String getHdfsFullUrlNoLastSlash() {
		return hdfsFullUrlNoLastSlash;
	}

	public void setHdfsFullUrlNoLastSlash(String hdfsFullUrlNoLastSlash) {
		this.hdfsFullUrlNoLastSlash = hdfsFullUrlNoLastSlash;
	}

	public String getZkQuoram() {
		return zkQuoram;
	}

	public void setZkQuoram(String zkQuoram) {
		this.zkQuoram = zkQuoram;
	}
	
	public JwtConfig getJwtConfig() {
		return jwtConfig;
	}

	public void setJwtConfig(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}


	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}


	public boolean isRefreshWxToken() {
		return refreshWxToken;
	}

	public void setRefreshWxToken(boolean refreshWxToken) {
		this.refreshWxToken = refreshWxToken;
	}


	public static class JwtConfig {
		private String file;
		private String issuer;
		
		private long principalTokenAlive;
		
		private long emailTokenAlive;
		
		public String getFile() {
			return file;
		}
		public void setFile(String file) {
			this.file = file;
		}
		public String getIssuer() {
			return issuer;
		}
		public void setIssuer(String issuer) {
			this.issuer = issuer;
		}
		
		public long getEmailTokenAlive() {
			return emailTokenAlive;
		}
		public void setEmailTokenAlive(long emailTokenAlive) {
			this.emailTokenAlive = emailTokenAlive;
		}
		public long getPrincipalTokenAlive() {
			return principalTokenAlive;
		}
		public void setPrincipalTokenAlive(long principalTokenAlive) {
			this.principalTokenAlive = principalTokenAlive;
		}
	}
}
