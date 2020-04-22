package com.example.b.mq;



import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dxd
 * @date 2020/4/21
 */
@Configuration
public class RabbitMqConfig {

  @Bean
  public Queue receiver() {
   return new Queue("receive", true);
  }

  @Bean
  public Queue sender() {
    return new Queue("send", true);
  }


}
