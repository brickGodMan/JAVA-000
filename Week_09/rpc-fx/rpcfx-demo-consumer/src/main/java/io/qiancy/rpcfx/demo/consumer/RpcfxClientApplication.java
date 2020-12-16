package io.qiancy.rpcfx.demo.consumer;

import io.qiancy.rpcfx.client.Rpcfx;
import io.qiancy.rpcfx.client.RpcfxCGLib;
import io.qiancy.rpcfx.demo.api.Order;
import io.qiancy.rpcfx.demo.api.OrderService;
import io.qiancy.rpcfx.demo.api.User;
import io.qiancy.rpcfx.demo.api.UserService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcfxClientApplication {

	// 二方库
	// 三方库 lib
	// nexus, userserivce -> userdao -> user
	//

	public static void main(String[] args) {

		// UserService service = new xxx();
		// service.findById

		//动态代理方式
//		UserService userService = Rpcfx.create(UserService.class, "http://localhost:8080/");
		//字节码增强方式
		UserService userService = RpcfxCGLib.create(UserService.class, "http://localhost:8080/");
		User user = userService.findById(1);
		System.out.println("find user id=1 from server: " + user.getName());

//		OrderService orderService = Rpcfx.create(OrderService.class, "http://localhost:8080/");
//		Order order = orderService.findOrderById(1992129);
//		System.out.println(String.format("find order name=%s, amount=%f",order.getName(),order.getAmount()));

		// 新加一个OrderService

//		SpringApplication.run(RpcfxClientApplication.class, args);
	}

}
