package com.example.mqtt.client.paho.demo.client;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MqttSubscriber {
    public static void main(String[] args) {
        // 消息主题
        String topic        = "Client/V3/TOPIC_TEST/123456";
        // mqtt broker 连接地址
        String broker       = "tcp://127.0.0.1:1883";
        // 客户端ID
        String clientId     = "subscriber-001";
        MemoryPersistence persistence = new MemoryPersistence();

        // 初始化mqtt客户端，并连接服务端
        try (MqttClient client = new MqttClient(broker, clientId, persistence)){
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setAutomaticReconnect(true);
            log.info("Connecting to broker: {}", broker);
            client.connect(connOpts);
            log.info("Connected");

            // 订阅消息
            log.info("Subscribe topic: {}", topic);
            client.subscribe(topic);
            // 注册callback
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    log.info("失去连接, 开始重连...");
                    try {
                        client.reconnect();
                    } catch (MqttException e) {
                        log.info("重连失败");
                        e.printStackTrace();
                    }
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    log.info("收到新消息, topic: {}, message: {}", s , mqttMessage);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    log.info("deliveryComplete");
                }
            });

            // 等待
            try {
                TimeUnit.DAYS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch(MqttException me) {
            String message = String.format("code: %s, msg: %s", me.getReasonCode(), me.getMessage());
            log.error(message, me);
        }
    }
}
