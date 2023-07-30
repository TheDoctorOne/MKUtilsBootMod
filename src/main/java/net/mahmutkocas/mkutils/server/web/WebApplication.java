package net.mahmutkocas.mkutils.server.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.mahmutkocas.mkutils.server.ServerGlobals;
import net.mahmutkocas.mkutils.server.web.dao.CrateContentDAO;
import net.mahmutkocas.mkutils.server.web.dao.CrateDAO;
import net.mahmutkocas.mkutils.server.web.dao.UserCrateDAO;
import net.mahmutkocas.mkutils.server.web.dao.UserDAO;
import net.mahmutkocas.mkutils.server.web.dto.file.CrateContentFileDTO;
import net.mahmutkocas.mkutils.server.web.dto.file.CrateFileDTO;
import net.mahmutkocas.mkutils.server.web.repository.CrateContentRepository;
import net.mahmutkocas.mkutils.server.web.repository.CrateRepository;
import net.mahmutkocas.mkutils.server.web.repository.UserCrateRepository;
import net.mahmutkocas.mkutils.server.web.repository.UserRepository;
import net.mahmutkocas.mkutils.server.web.service.GeneralService;
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
import java.io.File;
import java.io.IOException;
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

    @Autowired
    private GeneralService generalService;

//    @Bean
    public void toFile() throws IOException {
        CrateContentFileDTO content = CrateContentFileDTO.builder()
                .name("Emerald 96")
                .imageUrl("https://static.wikia.nocookie.net/minecraft_gamepedia/images/2/26/Emerald_JE3_BE3.png")
                .chance(100)
                .command("give %p% emerald 64;give %p% emerald 32")
                .colorHex("#32CD32")
                .build();


        CrateContentFileDTO content2 = CrateContentFileDTO.builder()
                .name("Diamond 96")
                .imageUrl("https://www.nicepng.com/png/full/154-1548006_minecraft-diamond-minecraft-diamond-pixel-art.png")
                .chance(100)
                .command("give %p% diamond 64;give %p% diamond 32")
                .colorHex("#00FFFF")
                .build();



        CrateContentFileDTO content3 = CrateContentFileDTO.builder()
                .name("Wood 256")
                .imageUrl("https://static.wikia.nocookie.net/minecraft_gamepedia/images/1/1d/Oak_Wood_(UD)_JE5_BE2.png")
                .chance(400)
                .command("give %p% log 64;give %p% log 64;give %p% log 64;give %p% log 64")
                .colorHex("#A1662F")
                .build();


        CrateContentFileDTO content4 = CrateContentFileDTO.builder()
                .name("Iron 128")
                .imageUrl("https://static.wikia.nocookie.net/minecraft_gamepedia/images/f/fc/Iron_Ingot_JE3_BE2.png")
                .chance(250)
                .command("give %p% iron_ingot 64;give %p% iron_ingot 64;give %p% iron_ingot 64;give %p% iron_ingot 64")
                .colorHex("#808080")
                .build();


        CrateContentFileDTO content5 = CrateContentFileDTO.builder()
                .name("Iron 96")
                .imageUrl("https://static.wikia.nocookie.net/minecraft_gamepedia/images/f/fc/Iron_Ingot_JE3_BE2.png")
                .chance(125)
                .command("give %p% iron_ingot 64;give %p% iron_ingot 32")
                .colorHex("#808080")
                .build();

        CrateContentFileDTO content6 = CrateContentFileDTO.builder()
                .name("Diamond 65")
                .imageUrl("https://www.nicepng.com/png/full/154-1548006_minecraft-diamond-minecraft-diamond-pixel-art.png")
                .chance(150)
                .command("give %p% diamond 64;give %p% diamond 1")
                .colorHex("#00FFFF")
                .build();

        CrateContentFileDTO content7 = CrateContentFileDTO.builder()
                .name("Diamond Random")
                .imageUrl("https://www.nicepng.com/png/full/154-1548006_minecraft-diamond-minecraft-diamond-pixel-art.png")
                .chance(800)
                .command("give %p% diamond %r%[min:0%max:64]")
                .colorHex("#00FFFF")
                .build();

        CrateFileDTO crate = CrateFileDTO.builder()
                .name("Mix Fixed Crate")
                .imageUrl("https://static.wikia.nocookie.net/minecraft_gamepedia/images/4/41/Chest.gif")
                .contents(Arrays.asList(content, content2, content3, content4, content5, content6, content7))
                .colorHex("#800080")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("crate.json"), crate);
        generalService.saveCrate(crate);
        generalService.giveUserCrate("test", "Mix Fixed Crate");
        generalService.giveUserCrate("test", "Mix Fixed Crate");
        generalService.giveUserCrate("test", "Mix Fixed Crate");
        generalService.giveUserCrate("test", "Mix Fixed Crate");
        generalService.giveUserCrate("test", "Mix Fixed Crate");
        generalService.giveUserCrate("test", "Mix Fixed Crate");
        generalService.giveUserCrate("test", "Mix Fixed Crate");
        generalService.giveUserCrate("test", "Mix Fixed Crate");
        generalService.giveUserCrate("test", "Mix Fixed Crate");
        generalService.giveUserCrate("test", "Mix Fixed Crate");
    }

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

        user.getCrates().addAll(userCrates);

        UserDAO persist = userRepository.save(user);
        persist = persist;
    }

}
