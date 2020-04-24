package com.example.a;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author dxd
 * @date 2020/4/21
 */
@Component
public class MqReceiver {

  @Value("${spring.application.name}")
  private String name;

  @Autowired
  private AmqpTemplate amqpTemplate;

  @RabbitHandler
  @RabbitListener(queues = {"receive"})
  public void receiver(String msg) {
    // 按照服务放到redis list中
    int i = msg.indexOf('$');
    String name = msg.substring(0, i);
    System.out.println(name);
    amqpTemplate.convertAndSend(name, msg);

  }

  @RabbitHandler
  @RabbitListener(queues = {"${spring.application.name}"})
  public void my(String msg) {
    GuardedObject.onEvent(msg, msg);
  }

}
