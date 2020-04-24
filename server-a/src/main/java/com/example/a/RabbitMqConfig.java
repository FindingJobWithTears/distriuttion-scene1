package com.example.a;



import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dxd
 * @date 2020/4/21
 */
@Configuration
public class RabbitMqConfig {

  @Value("${spring.application.name}")
  private String name;

  @Bean
  public Queue receiver() {
   return new Queue(name, false);
  }

}
