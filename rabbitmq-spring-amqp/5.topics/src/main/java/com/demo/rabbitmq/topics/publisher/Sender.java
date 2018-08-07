package com.demo.rabbitmq.topics.publisher;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    private int index;
    private int count = 0;
    private final String[] keys = {"quick.orange.rabbit",
            "lazy.orange.elephant", "quick.orange.fox",
            "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"};

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");

        if (++this.index == keys.length) {
            this.index = 0;
        }

        String key = keys[this.index];
        builder.append(key).append(' ');
        builder.append(Integer.toString(++count));
        String message = builder.toString();
        this.template.convertAndSend(topic.getName(), key, message);
        System.out.println(" [X] Sent '" + message + "'");
    }

}
