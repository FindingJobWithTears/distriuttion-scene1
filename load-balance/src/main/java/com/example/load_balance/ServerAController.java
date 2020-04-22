package com.example.load_balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author dxd
 * @date 2020/4/21
 */
@RestController
public class ServerAController {

  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("/server")
  public String serverA(String msg) {
    return restTemplate.getForObject("http://server-a/serverB?msg="+msg, String.class);
  }
}
