package com.kouleen.suppermessage.service;

import com.kouleen.suppermessage.domain.RabbitMQDTO;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhangqing
 * @since 2023/1/25 13:51
 */
public class SupperMessageServiceImpl implements SupperMessageService {

    @Override
    public Channel getChannel(RabbitMQDTO rabbitMQDTO) throws IOException, TimeoutException {
        //初始化连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQDTO.getHost());
        factory.setPort(rabbitMQDTO.getPort());
        factory.setUsername(rabbitMQDTO.getUsername());
        factory.setPassword(rabbitMQDTO.getPassword());
        factory.setVirtualHost(rabbitMQDTO.getVirtualHost());
        //创建连接
        Connection conn = factory.newConnection();
        return conn.createChannel();
    }
}
