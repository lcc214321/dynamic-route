package com.dynamic.route;

import com.dynamic.route.event.MonitorThread;
import com.dynamic.route.event.MonitorThreadControl;
import com.dynamic.route.filter.ZuulAutoFilter;
import com.dynamic.route.service.ZuulRouteVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableZuulProxy
@RestController
public class DynamicRouteApplication {

	@Bean
	public ZuulAutoFilter zuulAutoFilter(){
		return new ZuulAutoFilter();
	}

	@Autowired
	private ZuulRouteVoService zuulRouteVoService;

	@Autowired
	private MonitorThread monitorThread;

	@Autowired
	public void  setInitialize(){
		zuulRouteVoService.initZuulRouteVoMap();

		monitorThread.setDaemon(true);
		monitorThread.start();
	}

	public static void main(String[] args) {
		SpringApplication.run(DynamicRouteApplication.class, args);

		MonitorThreadControl.setState(1);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String running(){
		return "dynamic route good running!";
	}
}
