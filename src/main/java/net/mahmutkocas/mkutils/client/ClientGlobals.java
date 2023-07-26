package net.mahmutkocas.mkutils.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.mahmutkocas.mkutils.client.network.web.client.UserClient;
import net.mahmutkocas.mkutils.common.dto.TokenDTO;

import java.io.File;
import java.io.IOException;

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
}
