package com.demo.rabbitmq.hello_world.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "hello")
public class Receiver {

    @RabbitHandler
    public void receive(String in) {
        System.out.println(" [X] Received '" + in + "'");
    }
}
