package com.demo.rabbitmq.routing.publisher;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange direct;

    private int index;
    private int count = 0;
    private final String[] keys = {"orange", "black", "green"};

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");

        if (++this.index == 3) {
            this.index = 0;
        }

        String key = keys[this.index];
        builder.append(key).append(' ');
        builder.append(Integer.toString(++count));
        String message = builder.toString();
        this.template.convertAndSend(direct.getName(), key, message);
        System.out.println(" [X] Sent '" + message + "'");
    }

}
