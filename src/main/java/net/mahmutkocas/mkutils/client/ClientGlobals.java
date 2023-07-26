package net.mahmutkocas.mkutils.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.client.network.web.client.UserClient;
import net.mahmutkocas.mkutils.common.dto.TokenDTO;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@SideOnly(Side.CLIENT)
@Log4j2
public class ClientGlobals {

    private static final File configFolder = new File("mkutils");
    private static final File configFile = new File(configFolder, "config.json");

    private static TokenDTO userToken;
    private static UserClient userClient;
    private static ClientConfig clientConfig;

    public static void runUserClient() {
        userClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(UserClient.class, "http://localhost:8080");
    }

    public static synchronized void readConfig() {
        if(!configFolder.isDirectory() || !configFile.exists()) {
            clientConfig = new ClientConfig();
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            clientConfig = objectMapper.readValue(configFile, ClientConfig.class);
        } catch (IOException e) {
            log.error("Client config parse error!", e);
            clientConfig = new ClientConfig();
        }
    }

    @SneakyThrows
    public static synchronized void saveConfig() {
        if(!configFolder.isDirectory() && !configFolder.mkdirs()) {
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(configFile, clientConfig);
    }

    public static ClientConfig getClientConfig() {
        return clientConfig;
    }

    public static UserClient getUserClient() {
        return userClient;
    }

    public static void setUserToken(TokenDTO userToken) {
        ClientGlobals.userToken = userToken;
    }

    public static TokenDTO getUserToken() {
        if(userToken == null) {
            return new TokenDTO();
        }
        return userToken;
    }

    public static BufferedImage getImageByURL(String url) {
        BufferedImage bufferedimage;
        try
        {
            bufferedimage = ImageIO.read(new URL(url));
            if(bufferedimage.getWidth() != 64 || bufferedimage.getHeight() != 64) {
                bufferedimage = ClientGlobals.resizeImage(bufferedimage, 64, 64);
            }
        }
        catch (Throwable throwable)
        {
            log.error("Invalid icon for {}", url, throwable);
            return null;
        }
        return bufferedimage;
    }

    public static BufferedImage resizeImage(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return dimg;
    }
}
