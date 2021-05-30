package br.com.renansoriano.wallet.infrastructure.configuration;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.renansoriano.wallet.core.tracking.TrackingProvider;
import br.com.renansoriano.wallet.core.wallet.WalletGenerate;
import br.com.renansoriano.wallet.infrastructure.order.JpaOrderRepository;

@Configuration
public class BeansConfigurer {
	
	@Bean
	public TrackingProvider getTrackingProvider() {
		return TrackingProvider.getInstance();
	}
	
    @Bean
    public WalletGenerate walletGenerate(JpaOrderRepository repository) {
        return new WalletGenerate(repository);
    }

	@Bean(name = "json")
	@Primary
	public ObjectMapper jsonObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		return objectMapper;
	}

	@Bean(name = "yaml")
	public ObjectMapper yamlObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		return objectMapper;
	}

	@Bean(name = "longHTTP")
	public RestTemplate restTemplateLongTimeout(ObjectMapper objectMapper, RestTemplateBuilder builder)
			throws Exception {

		return restTemplateBuilder(objectMapper, builder)
				.setConnectTimeout(Duration.ofMillis(300))
				.setReadTimeout(Duration.ofMillis(10000))
				.build();
	}
	
	@Bean(name = "mediumHTTP")
	public RestTemplate restTemplateMediumTimeout(ObjectMapper objectMapper, RestTemplateBuilder builder)
			throws Exception {

		return restTemplateBuilder(objectMapper, builder)
				.setConnectTimeout(Duration.ofMillis(300))
				.setReadTimeout(Duration.ofMillis(1000))
				.build();
	}
	
	@Bean(name = "shortHTTP")
	public RestTemplate restTemplateShortTimeout(ObjectMapper objectMapper, RestTemplateBuilder builder)
			throws Exception {

		return restTemplateBuilder(objectMapper, builder)
				.setConnectTimeout(Duration.ofMillis(300))
				.setReadTimeout(Duration.ofMillis(500))
				.build();
	}
	
	@Bean(name = "microHTTP")
	public RestTemplate restTemplateMicroTimeout(ObjectMapper objectMapper, RestTemplateBuilder builder)
			throws Exception {

		return restTemplateBuilder(objectMapper, builder)
				.setConnectTimeout(Duration.ofMillis(300))
				.setReadTimeout(Duration.ofMillis(250))
				.build();
	}

	private RestTemplateBuilder restTemplateBuilder(ObjectMapper objectMapper, RestTemplateBuilder builder)
			throws Exception {

		TrustManager[] trustManager = {

				new X509TrustManager() {

					@Override
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return new X509Certificate[0];
					}

					@Override
					public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
					}

					@Override
					public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {

					}

				} };

		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, trustManager, new SecureRandom());

		HttpClient httpClient = HttpClients
				.custom()
				.setSSLContext(sslContext)
				.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.build();

		HttpComponentsClientHttpRequestFactory customRequestFactory = new HttpComponentsClientHttpRequestFactory();
		customRequestFactory.setHttpClient(httpClient);

		return builder.requestFactory(() -> customRequestFactory);

	}
}
