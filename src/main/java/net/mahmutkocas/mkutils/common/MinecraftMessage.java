package net.mahmutkocas.mkutils.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import lombok.*;
import net.mahmutkocas.mkutils.common.dto.CrateContentDTO;
import net.mahmutkocas.mkutils.common.dto.CrateDTO;
import net.mahmutkocas.mkutils.common.dto.CrateDTOList;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MinecraftMessage implements IMessage {

    @Getter
    public enum MCMessageType {
        NONE(null, false),
        GET_CRATES(null, false),
        CRATE_LIST(CrateDTOList.class,true),
        CRATE_OPEN(CrateDTO.class, false),
        CRATE_RESULT(CrateContentDTO.class, false);

        @JsonIgnore
        private final Class<?> returningType;

        @JsonIgnore
        private final boolean list;

        MCMessageType(Class<?> msgClass, boolean list) {
            this.returningType = msgClass;
            this.list = list;
        }
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private MCMessageType type = MCMessageType.NONE;
    private String msg = "";

    public MinecraftMessage(MCMessageType type) {
        this.type = type;
    }

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
