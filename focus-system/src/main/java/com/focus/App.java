package com.focus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

import com.focus.util.SpringUtil;

/**
 *jyl
 *
 */
@SpringBootApplication
@EnableCaching
public class App extends SpringBootServletInitializer {
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}
	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(App.class, args);
		SpringUtil.setApplicationContext(app);
	}
}
