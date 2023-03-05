1.父工程pom.xml文件里对artifactId的命名和微课不同，不匹配的命名导致项目报错

2.第一次运行报错： 

Failed to bind properties under 'logging.level' to java.util.Map<java.lang.String, org.springframework.boot.logging.LogLevel>

原因：是由application.yml文件的缩进问题导致的。对相应行缩进即可。

3.启动成功后第一次测试Url地址失败，没有返回用户信息。 
在这一步总共遇到了3个问题。 

（1）课件提供的测试用url地址有误。按照课件的步骤，正确的地址应该是：http://localhost:10100/UserController/getUserByIdByPass/12345671111/123。

（2）数据库权限问题。报错”Client does not support authentication protocol requested by server; consider upgrading MySQL clien“。authentication protocol即身份验证协议。修改mysql的权限即可。

（3）数据库版本问题。解决耗时最长的问题。这个问题在之前的项目里也遇到过，当时只需要把pom.xml文件里的mysql-connector-java依赖从较低的版本升级到和电脑数据库版本匹配的版本即可。
但是这次修改后却还是报相同的错。最终发现是因为maven包没有重新导入，导致项目启动时一直默认用低版本的依赖包。

4.模块间的同名类冲突
 
拷贝pom.xml给每个模块的时候没有修改相应的artifactId，导致多个模块被认为是同一个模块。需要重新创建工程。

5.购物车微服务报错

Invalid bound statement (not found): com.neusoft.mapper.CartMapper.listCart 原因：mapper.java的映射文件mapper.xml没拷贝过来


无法更新购物车。原因：因为之前的Bug和误操作，数据库里针对某一个食品有多条购物车数据，导致数据紊乱。
  
6.Failed to convert value of type 'java.lang.String' to required type 'java.lang.Double'
前端参数有误
7.getmapping有什么区别

8.做gateway启动Business微服务时报错。错误信息表明数据库url连接异常。
原因：误给business_server模块建了一个子模块，导致pom.xml文件里自动配置了一个<packaging>pom</packaging>。


9.做完config配置后测试会有一段时间无响应。可能是因为配置文件的数据还未缓存好。

10.post http://localhost:15000/actuator/bus-refresh之后没有更新配置信息。

原因：可能是因为Pom.xml的依赖文件没有导入，重载了一下再重新启动就解决了。