package com.example.mqtt.client.paho.demo.client;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Slf4j
public class MqttPublisher {
    public static void main(String[] args) {
        // 消息主题
        String topic        = "Client/V3/TOPIC_TEST/123456";
        // 消息内容
        String content      = "测试消息";
        // 消息质量级别
        int qos             = 1;
        // mqtt broker 连接地址
        String broker       = "tcp://127.0.0.1:1883";
        // 客户端ID
        String clientId     = "publisher-001";
        MemoryPersistence persistence = new MemoryPersistence();

        // 初始化mqtt客户端，并连接服务端
        try(MqttClient client = new MqttClient(broker, clientId, persistence);) {

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            log.info("Connecting to broker: {}", broker);
            client.connect(connOpts);
            log.info("Connected");

            // 发送消息
            log.info("Publishing message: {}", content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            client.publish(topic, message);
            log.info("Message published");

            // 断开连接
            client.disconnect();
            log.info("Disconnected");

        } catch(MqttException me) {
            String message = String.format("code: %s, msg: %s", me.getReasonCode(), me.getMessage());
            log.error(message, me);
        }
    }
}
