package br.com.renansoriano.wallet.infrastructure.log;

import javax.annotation.PostConstruct;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.renansoriano.wallet.core.tracking.TrackingProvider;

@Component
public class TrackingLogConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(TrackingLogConfigurer.class);
	private static final String KEY_INNER_ID = "innerId";
	private static final String KEY_CID = "cid";
	
	@PostConstruct
	public void configure() {
		TrackingProvider.getInstance().addInnerIdObserver(this::setInnerId);
		TrackingProvider.getInstance().addCidObserver(this::setCorrelationId);
	}
	
	private void setInnerId(String innerId) {
		MDC.put(KEY_INNER_ID, innerId);
		
		logger.trace("InnerId {} setted", innerId);
	}
	
	private void setCorrelationId(String correlationId) {
		MDC.put(KEY_CID, correlationId);
		
		logger.trace("CorrelationId {} setted", correlationId);
	}
	
}
