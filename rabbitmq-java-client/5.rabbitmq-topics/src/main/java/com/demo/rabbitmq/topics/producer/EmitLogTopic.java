package com.demo.rabbitmq.topics.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Date;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) {
        ConnectionFactory factory = null;
        Connection connection = null;

        try {
            factory = new ConnectionFactory();
            factory.setHost("localhost");

            connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            String routingKey = getRouting(args);
            String message = getMessages(args);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {

                }
            }
        }
    }

    private static String getRouting(String[] strings) {
        if (strings.length < 1) {
            return "anonymous.info";
        }

        return strings[0];
    }


    private static String getMessages(String[] strings) {
        if (strings.length < 2) {
            return "Hello World " + new Date().getTime();
        }

        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) {
            return "";
        }

        if (length < startIndex) {
            return "";
        }

        StringBuffer words = new StringBuffer(strings[startIndex]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }

        return words.toString();
    }
}
