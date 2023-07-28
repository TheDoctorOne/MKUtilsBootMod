package net.mahmutkocas.mkutils.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import lombok.*;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MinecraftMessage implements IMessage {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public enum MCMessageType {
        NONE,
        CRATE_LIST,
        CRATE_OPEN,
        CRATE_RESULT
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class MessageWrapper {
        private MCMessageType type = MCMessageType.NONE;
        private String msg;
    }

    private MCMessageType type = MCMessageType.NONE;
    private String msg;


    @SneakyThrows
    public MinecraftMessage(MCMessageType type, Object msg) {
        this.type = type;
        this.msg = MAPPER.writeValueAsString(msg);
    }

    public <T> T getMsg(Class<T> tClass) throws IOException {
        return MAPPER.readValue(msg, tClass);
    }

    @SneakyThrows
    @Override
    public void toBytes(ByteBuf buf) {
        if(msg==null) {
            return;
        }
        buf.writeBytes(MAPPER.writeValueAsString(this).getBytes());
    }

    @SneakyThrows
    @Override
    public void fromBytes(ByteBuf buf) {
        MinecraftMessage message = MAPPER.readValue(buf.toString(StandardCharsets.UTF_8), this.getClass());
        this.setType(message.getType());
        this.setMsg(message.getMsg());
    }
}
