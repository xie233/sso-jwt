# sso-jwt

- JWT?

由于HTTP协议的无状态特性，不采取一些手段，用户每次操作如加入购物车都需要登录，极不友好，有采用Cookie记录用户的一些关键信息，每次请求带上Cookie即可，可是Cookie大小是有限的，还有...，另一种服务器端保存 jsessionid，用户每次访问只要携带 jsessionid 过来即可。然后系统大了，分布式之后呢，每台机器都得去保存这个 jsessionid，就很头疼，要不搞一台机器专门记录 jsessionid 吧，又怕宕机，还得再搞个备份。   然后呢，有人就说这么麻烦啊！ 之后有人提出一种 token 替代sessionid的方案，服务器不保存 token，这下轻松了，但是需要声明一个隐秘的密钥对用户的一些信息比如用户id进行包装，这个 token 一般是{header,payload,signature}， 通过某种公开算法+密钥,对 {header,payload} 进行哈希或者加密得到 signature, 然后返回给用户，用户每次请求在 cookie 或者header里携带这个token就好了，服务器只需要重新将这种公开算法+密钥 对{header,payload}进行哈希或者加密与签名作对比就知道你是不是先前登陆过的了，说得好像这东西没毛病了，只是服务器端需要多做些cpu计算而已啦.. 然而服务器端不保存这token，咋让它过期啊，虽然 payload 里面可以设置过期时间，但这个时间也是不好确定的，比如1个小时，然后这段时间用户可是一直在线做一些事情的啊，挺活跃的，通常做法是再加一个 refreshToken，比 accessToken（原先的token）少点时间，比如10分钟的时间，用户在50分的时候又发来一些请求，这时候refreshToken已经过期，但用户还活跃，就可以根据这个refreshToken再给这个用户延长一小时咯。实际上怎么做，还看具体的场景了（套话）  还有就是 header，payload 使用 base64 编码的，可以直接从token里面知道这些具体信息，密码什么的就别放里面了，token给谁都能用要保管好。。 这个就叫 json web token JWT。 具体也可以看这个

[干掉状态：从session到token](https://mp.weixin.qq.com/s?__biz=MzAxOTc0NzExNg==&mid=2665513566&idx=1&sn=a2688cadbe9c8042ff1abbdf04a8bd5e&chksm=80d67a1db7a1f30b28b93ed2ab29edfbf982b780433e4bfd178e3cc52cb1f9100cc8f923db4f#rd)

[理解JWT的使用场景和优劣](http://blog.didispace.com/learn-how-to-use-jwt-xjf/)

- SSO?

单点登录，访问同一个系统多个服务，每个服务都用一个密码，用户也会烦的，统一登录很有必要，在一个服务登录后，在其他服务可以直接访问。 过程应该是这样的用户 X 访问服务 A， 服务 A 设置了拦截请求，检测到用户请求中 Token 不存在或者已过期，则重定向统一认证服务器进行验证，认证服务器校验成功后，发给用户一个令牌token（或者加一个refreshToken），然后跳转回用户原先要访问的地方。。 同样的在token未过期的时间内，用户携带Token（或者加一个refreshToken）访问该系统下的其他服务，则可以直接访问了。 为了简单，直接用 redis 存储 {userId:user}

- 加了点 JSR303 参数校验

- 使用 auth0.jwt 

- sso-server 是认证服务器端口8090, sso-service 和 sso-service22 是两个相同的服务==端口8091和8092 

访问 [localhost:8091](http://localhost:8091) 跳转到 [localhost:8090](http://localhost:8090) 再返回 [localhost:8091](http://localhost:8091)

访问  [localhost:8092](http://localhost:8092) 就不用再登录了



打开IDEA， 分别启动就可=
用户名密码随便输
