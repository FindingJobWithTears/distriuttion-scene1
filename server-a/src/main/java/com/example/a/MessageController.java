package com.example.a;

import java.util.Objects;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dxd
 * @date 2020/4/21
 */
@RestController
public class MessageController {

  @Value("${spring.application.name}")
  private String name;

  @Autowired
  private AmqpTemplate amqpTemplate;

  @GetMapping("/serverB")
  public String serverB(String msg) {
    String timestamp = System.currentTimeMillis() + name + " " +msg;

    GuardedObject<String> guardedObject = GuardedObject.create(timestamp);

    amqpTemplate.convertAndSend("send", timestamp);

    return guardedObject.get(Objects::nonNull);
  }
}
