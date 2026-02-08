package com.kireygroup.camel.servlet.route;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class IntegrationRoute extends EndpointRouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from(servlet("api"))
			.process(exchange -> {
				Message message = exchange.getMessage();
		        String contentType = message.getHeader(Exchange.CONTENT_TYPE, String.class);
		        String httpUri = message.getHeader(Exchange.HTTP_URI, String.class);
		        HttpServletRequest req = message.getHeader(Exchange.HTTP_SERVLET_REQUEST, HttpServletRequest.class);
		        log.info("http request {}", req);
		        message.setBody("<b>Got Content-Type: " + contentType + ", URI: " + httpUri + "</b>");
		        message.setHeader(Exchange.CONTENT_TYPE, "text/plain");
			});
	}
}
