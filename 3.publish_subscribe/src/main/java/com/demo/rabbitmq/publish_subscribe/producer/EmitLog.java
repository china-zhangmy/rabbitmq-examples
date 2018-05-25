package com.demo.rabbitmq.publish_subscribe.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.Date;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true; // declare queue durable
        channel.queueDeclare(EXCHANGE_NAME, durable, false, false, null);
        String message = getMessages(args);
        channel.basicPublish("", EXCHANGE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
        System.out.println(" [X] sent  '" + message + "'");

        channel.close();
        connection.close();
    }

    private static String getMessages(String[] strings) {
        if (strings.length < 1) {
            return "Hello World .......... " + new Date().getTime();
        }

        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) {
            return "";
        }

        StringBuffer words = new StringBuffer(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }

        return words.toString();
    }
}
