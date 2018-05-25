package com.demo.rabbitmq.publish_subscribe.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveLogs {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args)
            throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(EXCHANGE_NAME, false, false, false, null);
        System.out.println(" [X] Waiting for messages. To exit press CTRL + C");

        channel.basicQos(1); // quality of service

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [X] Received '" + message + "'");

                try {
                    doWork(message);
                } finally {
                    System.out.println(" [X] Done");
                    channel.basicAck(envelope.getDeliveryTag(), false); // trigger acknowledgement by delivery tag
                }
            }
        };

        boolean autoAck = false;// manual-acknowledged
        channel.basicConsume(EXCHANGE_NAME, autoAck, consumer);
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
