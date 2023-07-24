package net.mahmutkocas.reservermod.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import lombok.SneakyThrows;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@SideOnly(Side.SERVER)
public class MinecraftMessage implements IMessage {

    private String msg;
    private final ObjectMapper mapper = new ObjectMapper();

    public MinecraftMessage() {
        msg = null;
    }

    public MinecraftMessage(String msg) {
        this.msg = msg;
    }

    @SneakyThrows
    public MinecraftMessage(Object msg) {
        this.msg = mapper.writeValueAsString(msg);
    }

    public String getMsg() {
        return msg;
    }

    @SneakyThrows
    public <T> T getMsg(Class<T> tClass) {
        return mapper.readValue(msg, tClass);
    }
    @Override
    public void toBytes(ByteBuf buf) {
        if(msg==null) {
            return;
        }
        buf.writeBytes(msg.getBytes());
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        msg = buf.toString(StandardCharsets.UTF_8);
    }
}
