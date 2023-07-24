package net.mahmutkocas.reservermod.web;

import net.minecraft.server.MinecraftServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients("net.mahmutkocas.reservermod.web.client")
public class WebApplication {

    public static ConfigurableApplicationContext start(MinecraftServer minecraftServer) {
        ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class);
        context.getBeanFactory().registerSingleton("minecraftServer", minecraftServer);
        return context;
    }

}
