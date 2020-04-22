package com.example.b.mq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dxd
 * @date 2020/4/21
 */
@Component
public class MqReceiver {

  @Autowired
  private AmqpTemplate amqpTemplate;

  private ThreadPoolExecutor executor =
      new ThreadPoolExecutor(3, 3, 1, TimeUnit.HOURS
          , new ArrayBlockingQueue<>(20)
          ,  r-> new Thread(r, "mq-"+ r.hashCode()));

  @RabbitHandler
  @RabbitListener(queuesToDeclare  = @Queue("send"))
  public void receiver(String msg) throws InterruptedException {
    // 假装是个耗时的任务
    executor.submit(() -> {
      long v = (long) (Math.random() * 5000);
      try {
        Thread.sleep(v);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Send :" + msg);
      amqpTemplate.convertAndSend("receive", msg);
    });

  }
}
