## 结构
                                 send queue 
 浏览器  -> 负载均衡 ----> 服务A  ----------->  服务B
                                <----------- 
                                receive queue
 ### 问题
 
 服务A 如果做成了集群, 消息如何对应