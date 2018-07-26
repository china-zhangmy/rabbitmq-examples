package com.demo.rabbitmq.work_queues;

import com.demo.rabbitmq.work_queues.consumer.Receiver;
import com.demo.rabbitmq.work_queues.producer.Sender;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"tut2", "work-queues"})
@Configuration
public class WorkQueuesConfig {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

    @Profile("receiver")
    private static class ReceiverConfig {

        @Bean
        public Receiver receiver1() {
            return new Receiver(1);
        }

        @Bean
        public Receiver receiver2() {
            return new Receiver(2);
        }

        @Bean
        public Receiver receiver3() {
            return new Receiver(3);
        }

        @Bean
        public Receiver receiver4() {
            return new Receiver(4);
        }
    }

    @Profile("sender")
    @Bean
    public Sender sender() {
        return new Sender();
    }

}
