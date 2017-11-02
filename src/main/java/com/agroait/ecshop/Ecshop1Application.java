package com.agroait.ecshop;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Ecshop1Application {

	public static void main(String[] args) {
		SpringApplication.run(Ecshop1Application.class, args);
	}
	
	@GetMapping("/hello")
	public String sayHello() {
		return "你好，china";
	}
	
	@GetMapping("/ss/h2")
	public String sayHello2() {
		return "你好，china2";
	}
	
	@Bean
	public Connector httpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(8038);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainerFactory() {
		TomcatEmbeddedServletContainerFactory containerFactory = new TomcatEmbeddedServletContainerFactory() {

			@Override
			protected void postProcessContext(Context context) {
				super.postProcessContext(context);
				SecurityConstraint constraint = new SecurityConstraint();
				constraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/ss/*");
				constraint.addCollection(collection);
				context.addConstraint(constraint);
			}
		};
		containerFactory.addAdditionalTomcatConnectors(httpConnector());
		return containerFactory;
	}
	
	
}
