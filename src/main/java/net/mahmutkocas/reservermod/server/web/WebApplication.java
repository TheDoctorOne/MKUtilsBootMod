package net.mahmutkocas.reservermod.server.web;

import lombok.SneakyThrows;
import net.minecraft.server.MinecraftServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
public class WebApplication {

    @SneakyThrows
    public static ConfigurableApplicationContext start(MinecraftServer minecraftServer) {
        AtomicReference<ConfigurableApplicationContext> ref = new AtomicReference<>();

        Thread t = new Thread(() -> {
            ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class);
            ref.set(context);
            context.getBeanFactory().registerSingleton("minecraftServer", minecraftServer);
        });
        t.setDaemon(true);
        t.setName("SpringWeb");
        t.start();

        while (ref.get() == null) {
            Thread.sleep(100);
        }

        return ref.get();
    }

}
