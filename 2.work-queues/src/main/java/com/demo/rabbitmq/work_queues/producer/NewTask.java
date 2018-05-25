package com.demo.rabbitmq.work_queues.producer;

import com.rabbitmq.client.*;

import java.util.Date;

public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true; // declare queue durable
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        String message = getMessages(args);
        channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
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
