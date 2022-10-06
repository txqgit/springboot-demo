package com.example.mqtt.borker.moquette.demo.config;

import io.moquette.broker.Server;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MoquetteServer {
    private Server mqttServer;

    /**
     * 启动 MoquetteServer
     */
    public void start() {
        // 初始化配置文件
        IResourceLoader configFileResourceLoader = new ClasspathResourceLoader("mqtt/moquette-config.conf");
        final IConfig config = new ResourceLoaderConfig(configFileResourceLoader);

        mqttServer = new Server();

        // 启动服务
        mqttServer.startServer(config, null, null, null, null);
        log.info("Moquette Server Started.");
    }


    /**
     * 关闭 MoquetteServer
     */
    public void stop() {
        if (mqttServer != null) {
            mqttServer.stopServer();
            log.info("Moquette Server Stopped.");
        }
    }
}
