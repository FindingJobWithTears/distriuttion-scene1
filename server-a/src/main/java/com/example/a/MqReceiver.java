package com.example.a;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author dxd
 * @date 2020/4/21
 */
@Component
public class MqReceiver {

  @RabbitHandler
  @RabbitListener(queues = {"receive"})
  public void receiver(String msg) {
    System.out.println("Receiver :" + msg);
    GuardedObject.onEvent(msg, msg);
  }
}
