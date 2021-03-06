package com.firefly.server.http;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.firefly.mvc.web.servlet.SystemHtmlPage;
import com.firefly.net.Session;
import com.firefly.server.session.HttpSessionManager;
import com.firefly.server.session.LocalHttpSessionManager;

public class Config {
	private String configFileName = "firefly.xml";
	private String encoding = "UTF-8";
	private Map<Integer, String> errorPage;
	
	private int maxRequestLineLength = 8 * 1024,
				maxRequestHeadLength = 16 * 1024, 
				maxRangeNum = 8,
				writeBufferSize = 8 * 1024,
				maxConnections = 2000,
				maxConnectionTimeout = 10 * 1000;
	private long maxUploadLength = 50 * 1024 * 1024;
	private int httpBodyThreshold = 4 * 1024 * 1024;
	private boolean keepAlive = true;
	private boolean pipeline = false;
	private String serverHome, host, servletPath = "", contextPath = "", tempdir;
	private int port;
	
	private boolean secure = false;
	private String credentialPath, keystorePassword, keyPassword;
	
	private String sessionIdName = "jsessionid";
	private int maxSessionInactiveInterval = 10 * 60;
	private HttpSessionManager httpSessionManager = new LocalHttpSessionManager(this);
	private HttpSessionAttributeListener httpSessionAttributeListener = new HttpSessionAttributeListener() {
		@Override
		public void attributeAdded(HttpSessionBindingEvent se) {
		}

		@Override
		public void attributeRemoved(HttpSessionBindingEvent se) {
		}

		@Override
		public void attributeReplaced(HttpSessionBindingEvent se) {
		}
	};
	private HttpSessionListener httpSessionListener = new HttpSessionListener() {

		@Override
		public void sessionCreated(HttpSessionEvent se) {
		}

		@Override
		public void sessionDestroyed(HttpSessionEvent se) {
		}
	};

	private HttpConnectionListener httpConnectionListener = new HttpConnectionListener() {

		@Override
		public void connectionCreated(Session session) {
		}

		@Override
		public void connectionClosed(Session session) {
		}

	};

	private FileAccessFilter fileAccessFilter = new FileAccessFilter() {
		@Override
		public String doFilter(HttpServletRequest request,
				HttpServletResponse response, String path) {
			return path;
		}
	};

	public Config() {
	}

	public Config(String serverHome, String host, int port) {
		setServerHome(serverHome);
		this.host = host;
		this.port = port;
	}

	public HttpConnectionListener getHttpConnectionListener() {
		return httpConnectionListener;
	}

	public void setHttpConnectionListener(
			HttpConnectionListener httpConnectionListener) {
		this.httpConnectionListener = httpConnectionListener;
	}

	public String getConfigFileName() {
		return configFileName;
	}

	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}

	public HttpSessionAttributeListener getHttpSessionAttributeListener() {
		return httpSessionAttributeListener;
	}

	public void setHttpSessionAttributeListener(
			HttpSessionAttributeListener httpSessionAttributeListener) {
		this.httpSessionAttributeListener = httpSessionAttributeListener;
	}

	public HttpSessionListener getHttpSessionListener() {
		return httpSessionListener;
	}

	public void setHttpSessionListener(HttpSessionListener httpSessionListener) {
		this.httpSessionListener = httpSessionListener;
	}

	/**
	 * 获取HttpSession默认超时时间，单位秒
	 * 
	 * @return HttpSession默认超时时间
	 */
	public int getMaxSessionInactiveInterval() {
		return maxSessionInactiveInterval;
	}

	/**
	 * 设置HttpSession默认超时时间，单位秒
	 * 
	 * @param maxSessionInactiveInterval
	 *            HttpSession默认超时时间
	 */
	public void setMaxSessionInactiveInterval(int maxSessionInactiveInterval) {
		this.maxSessionInactiveInterval = maxSessionInactiveInterval;
	}

	/**
	 * 获取HttpSession在cookie或者url中的名称
	 * 
	 * @return HttpSession的名称
	 */
	public String getSessionIdName() {
		return sessionIdName;
	}

	/**
	 * 设置HttpSession在cookie或者url中的名称
	 * 
	 * @param sessionIdName
	 *            HttpSession的名称
	 */
	public void setSessionIdName(String sessionIdName) {
		this.sessionIdName = sessionIdName;
	}

	/**
	 * 获取HttpSession管理器，默认为本地Session存储
	 * 
	 * @return HttpSession管理器
	 */
	public HttpSessionManager getHttpSessionManager() {
		return httpSessionManager;
	}

	/**
	 * 设置HttpSession管理器
	 * 
	 * @param httpSessionManager
	 *            HttpSession管理器
	 */
	public void setHttpSessionManager(HttpSessionManager httpSessionManager) {
		this.httpSessionManager = httpSessionManager;
	}

	/**
	 * 获取静态文件访问过滤器，此过滤器可以拦截本地静态文件的访问，并做出控制
	 * 
	 * @return 静态文件访问过滤器
	 */
	public FileAccessFilter getFileAccessFilter() {
		return fileAccessFilter;
	}

	/**
	 * 设置静态文件访问过滤器
	 * 
	 * @param fileAccessFilter
	 *            静态文件访问过滤器
	 */
	public void setFileAccessFilter(FileAccessFilter fileAccessFilter) {
		this.fileAccessFilter = fileAccessFilter;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = removeLastSlash(contextPath);
	}

	public String getServletPath() {
		return servletPath;
	}

	public void setServletPath(String servletPath) {
		this.servletPath = removeLastSlash(servletPath);
	}

	public static String removeLastSlash(String str) {
		if (str.charAt(str.length() - 1) == '/')
			return str.substring(0, str.length() - 1);
		return str;
	}

	public int getWriteBufferSize() {
		return writeBufferSize;
	}

	public void setWriteBufferSize(int writeBufferSize) {
		this.writeBufferSize = writeBufferSize;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getServerHome() {
		return serverHome;
	}

	public void setServerHome(String serverHome) {
		if (serverHome.charAt(serverHome.length() - 1) == '/')
			this.serverHome = serverHome.substring(0, serverHome.length() - 1);
		else
			this.serverHome = serverHome;
	}

	public int getMaxRequestHeadLength() {
		return maxRequestHeadLength;
	}

	public void setMaxRequestHeadLength(int maxRequestHeadLength) {
		this.maxRequestHeadLength = maxRequestHeadLength;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public int getMaxRequestLineLength() {
		return maxRequestLineLength;
	}

	public void setMaxRequestLineLength(int maxRequestLineLength) {
		this.maxRequestLineLength = maxRequestLineLength;
	}

	public int getMaxRangeNum() {
		return maxRangeNum;
	}

	public void setMaxRangeNum(int maxRangeNum) {
		this.maxRangeNum = maxRangeNum;
	}

	public long getMaxUploadLength() {
		return maxUploadLength;
	}

	public void setMaxUploadLength(long maxUploadLength) {
		this.maxUploadLength = maxUploadLength;
	}
	
	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public int getMaxConnectionTimeout() {
		return maxConnectionTimeout;
	}

	public void setMaxConnectionTimeout(int maxConnectionTimeout) {
		this.maxConnectionTimeout = maxConnectionTimeout;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public boolean isPipeline() {
		return pipeline;
	}

	public void setPipeline(boolean pipeline) {
		this.pipeline = pipeline;
	}

	public Map<Integer, String> getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(Map<Integer, String> errorPage) {
		this.errorPage = errorPage;
		SystemHtmlPage.addErrorPageMap(errorPage);
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public String getCredentialPath() {
		return credentialPath;
	}

	public void setCredentialPath(String credentialPath) {
		this.credentialPath = credentialPath;
	}

	public String getKeystorePassword() {
		return keystorePassword;
	}

	public void setKeystorePassword(String keystorePassword) {
		this.keystorePassword = keystorePassword;
	}

	public String getKeyPassword() {
		return keyPassword;
	}

	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}

	public String getTempdir() {
		return tempdir;
	}

	public void setTempdir(String tempdir) {
		this.tempdir = tempdir;
	}

	public int getHttpBodyThreshold() {
		return httpBodyThreshold;
	}

	/**
	 * http body length threshold. if the length over threshold, server will use temp file save http body data.
	 *  
	 * @param httpBodyThreshold body data length threshold
	 */
	public void setHttpBodyThreshold(int httpBodyThreshold) {
		this.httpBodyThreshold = httpBodyThreshold;
	}

}
