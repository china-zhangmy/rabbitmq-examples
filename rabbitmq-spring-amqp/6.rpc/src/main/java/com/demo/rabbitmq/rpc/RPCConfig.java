package com.demo.rabbitmq.rpc;

import com.demo.rabbitmq.rpc.consumer.Server;
import com.demo.rabbitmq.rpc.producer.Client;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"tut6", "rpc"})
@Configuration
public class RPCConfig {

    @Profile("client")
    private static class ClientConfig {

        @Bean
        public DirectExchange direct() {
            return new DirectExchange("tut.rpc");
        }

        @Bean
        public Client client() {
            return new Client();
        }

    }

    @Profile("server")
    private static class ServerConfig {

        @Bean
        public Queue queue() {
            return new Queue("tut.rpc.requests");
        }

        @Bean
        public DirectExchange direct() {
            return new DirectExchange("tut.rpc");
        }

        @Bean
        public Binding binding(DirectExchange direct, Queue queue) {
            return BindingBuilder.bind(queue).to(direct).with("rpc");
        }

        @Bean
        public Server server() {
            return new Server();
        }
    }

}
