package com.demo.rabbitmq.routing.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

public class Receiver {

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive11(String in) throws InterruptedException {
        receive(in, 11);
    }

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive12(String in) throws InterruptedException {
        receive(in, 12);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive21(String in) throws InterruptedException {
        receive(in, 21);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive22(String in) throws InterruptedException {
        receive(in, 22);
    }

    public void receive(String in, int receiver) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + receiver + " [X] Received '" + in + "'");
        doWork(in);
        watch.stop();
        System.out.println("instance " + receiver + " [X] Done in " + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
