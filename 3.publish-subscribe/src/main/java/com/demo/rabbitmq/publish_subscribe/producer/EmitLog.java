package com.demo.rabbitmq.publish_subscribe.producer;

import com.rabbitmq.client.*;

import java.util.Date;

public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String message = getMessages(args);
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        System.out.println(" [X] sent  '" + message + "'");

        channel.close();
        connection.close();
    }

    private static String getMessages(String[] strings) {
        if (strings.length < 1) {
            return "info: Hello World " + new Date().getTime();
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
