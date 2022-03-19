# POS in Shell

The demo shows a simple POS system with command line interface. 

To run

```shell
mvn clean spring-boot:run
```

Currently, it implements three commands which you can see using the `help` command.

```shell
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.5.7)
 
shell:>help
AVAILABLE COMMANDS

Built-In Commands
        clear: Clear the shell screen.
        exit, quit: Exit the shell.
        help: Display help about available commands.
        history: Display or save the history of previously run commands
        script: Read and execute commands from a file.
        stacktrace: Display the full stacktrace of the last error.

Pos Command
        a: Add a Product to Cart
        n: New Cart
        p: List Products
```

Everytime a customer come to make a purchase, use `n` to create a new cart and then use `a ${productid} ${amount}` to add a product to the cart.

Please make the POS system robust and fully functional by implementing more commands, for instance, print/empty/modify cart.

Implementing a PosDB with real database is very much welcome. 

Please use asciinema (https://asciinema.org) to record a demo and submit the url in QQ group. 

And please elaborate your understanding in layered systems via this homework in your README.md.

## My Understanding

分层的架构不仅是大的问题分解为多个阶段, 变的简单化, 而且还能在更改需求/业务/数据源配置的时候, 实现最小的改动.

在这里分成了三层:
* 视图层(这里是用Spring shell实现的视图层), 只负责用户的交互, 至于获得交互命令后, 使用什么具体的逻辑执行, 都交给Spring来装载.
* 服务层, 实现Service的接口, 只关注逻辑, 处理对应的用户请求, 而用哪种数据库, 都交给数据层来处理.
* 数据层, 实现了DB定义的增删查改接口, 只关注与数据的服务. 比如要把整个系统从MemoryDB换成持久化的(如mysql), 那么只需要增加一个新的持久化的数据管理的实现, 用Spring装载这个就可以了, 其他都可以不需要更改.

对于三层之间的交互, 都是使用的接口(Interface)来定义, 然后接口的实例通过Ioc来进行装载. 同时有Mode的定义贯穿三层(定义数据的组织形式).
