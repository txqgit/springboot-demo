package com.example.mqtt.borker.moquette.demo;

import com.example.mqtt.borker.moquette.demo.config.MoquetteServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringbootMqttBorkerMoquetteApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SpringbootMqttBorkerMoquetteApplication.class, args);

		SpringApplication application = new SpringApplication(SpringbootMqttBorkerMoquetteApplication.class);
		final ApplicationContext context = application.run(args);
		// 启动 MoquetteServer
		MoquetteServer server = context.getBean(MoquetteServer.class);
		server.start();
		// 服务关闭时，同时关闭 MoquetteServer
		Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
	}

}
