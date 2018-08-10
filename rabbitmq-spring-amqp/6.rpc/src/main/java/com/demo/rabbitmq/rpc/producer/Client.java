package com.demo.rabbitmq.rpc.producer;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Client {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange direct;

    private int start = 0;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        System.out.println(" [x] Requesting fib(" + start + ")");
        Integer response = (Integer) this.template.convertSendAndReceive(direct.getName(), "rpc", start++);
        System.out.println(" [.] Got '" + response + "'");
    }

}
