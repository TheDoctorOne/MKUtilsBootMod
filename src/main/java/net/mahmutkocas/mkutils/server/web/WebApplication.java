package net.mahmutkocas.mkutils.server.web;

import lombok.SneakyThrows;
import net.mahmutkocas.mkutils.server.ServerGlobals;
import net.mahmutkocas.mkutils.server.web.dao.CrateContentDAO;
import net.mahmutkocas.mkutils.server.web.dao.CrateDAO;
import net.mahmutkocas.mkutils.server.web.dao.UserCrateDAO;
import net.mahmutkocas.mkutils.server.web.dao.UserDAO;
import net.mahmutkocas.mkutils.server.web.repository.CrateContentRepository;
import net.mahmutkocas.mkutils.server.web.repository.CrateRepository;
import net.mahmutkocas.mkutils.server.web.repository.UserCrateRepository;
import net.mahmutkocas.mkutils.server.web.repository.UserRepository;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@SideOnly(Side.SERVER)
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

    private CrateContentDAO diamond(int diaCount) {
        return CrateContentDAO.builder()
                .name("Diamond " + diaCount)
                .chance(1000/diaCount)
                .color(Color.YELLOW.getRGB())
                .command("give %p% diamond " + diaCount)
                .imageUrl("https://www.nicepng.com/png/full/154-1548006_minecraft-diamond-minecraft-diamond-pixel-art.png")
                .build();
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CrateRepository crateRepository;
    @Autowired
    private CrateContentRepository crateContentRepository;
    @Autowired
    private UserCrateRepository userCrateRepository;

//    @Bean
    public void data() {
        UserDAO user = userRepository.findByUsername("test").get();

        HashSet<CrateContentDAO> contents = new HashSet<>();
        contents.add(diamond(64));
        contents.add(diamond(32));
        contents.add(diamond(48));
        contents.add(diamond(10));
        contents.add(diamond(16));

        for(CrateContentDAO dao : contents) {
            crateContentRepository.save(dao);
        }


        CrateDAO crateDAO = CrateDAO.builder()
                .name("Diamond Crate")
                .crateContents(contents)
                .imageUrl("https://freepngimg.com/thumb/minecraft/11-2-minecraft-diamond-png.png")
                .color(Color.CYAN.getRGB())
                .build();

        crateRepository.save(crateDAO);

        List<UserCrateDAO> userCrates = new ArrayList<>();

        userCrates.add(UserCrateDAO.builder().crateDAO(crateDAO).userDAO(user).claimed(false).build());
        userCrates.add(UserCrateDAO.builder().crateDAO(crateDAO).userDAO(user).claimed(false).build());
        userCrates.add(UserCrateDAO.builder().crateDAO(crateDAO).userDAO(user).claimed(false).build());
        userCrates.add(UserCrateDAO.builder().crateDAO(crateDAO).userDAO(user).claimed(false).build());
        userCrates.add(UserCrateDAO.builder().crateDAO(crateDAO).userDAO(user).claimed(false).build());

        for(UserCrateDAO dao : userCrates) {
            userCrateRepository.save(dao);
        }

        user.setCrates(userCrates);

        UserDAO persist = userRepository.save(user);
        persist = persist;
    }

}
