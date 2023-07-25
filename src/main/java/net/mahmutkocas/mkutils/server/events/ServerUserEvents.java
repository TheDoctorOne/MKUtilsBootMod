package net.mahmutkocas.mkutils.server.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.mahmutkocas.mkutils.AppGlobals;
import net.mahmutkocas.mkutils.common.MinecraftMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SideOnly(Side.SERVER)
public class ServerUserEvents {

    @Getter
    @Setter
    @AllArgsConstructor
    private static class UserLoginInfo {
        private double x;
        private double y;
        private double z;
        private float yaw;
        private float pitch;
        private float tick;

        public void increaseTick() {
            tick += 1;
        }
    }

    private static final Integer TIMEOUT_TICK = 400;
    private final Map<EntityPlayerMP, UserLoginInfo> userQueue = new ConcurrentHashMap<>();

    public static final ServerUserEvents INSTANCE = new ServerUserEvents();

    private ServerUserEvents() {

    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            return;
        }

        List<EntityPlayerMP> toRemove = new ArrayList<>();
        for(EntityPlayerMP user : userQueue.keySet()) {
            if(user.hasDisconnected()) {
                toRemove.add(user);
                continue;
            }
            UserLoginInfo info = userQueue.get(user);
            if(info.getTick() > TIMEOUT_TICK) {
                user.connection.disconnect(new TextComponentString("Giriş Yapınız!"));
                toRemove.add(user);
                continue;
            }
            info.increaseTick();
            user.setLocationAndAngles(info.getX(), info.getY(), info.getZ(), info.getYaw(), info.getPitch());
        }

        for (EntityPlayerMP user : toRemove) {
            userQueue.remove(user);
        }
    }

    public void userConfirmed(String username) {
        EntityPlayerMP target = null;
        for(EntityPlayerMP user : userQueue.keySet()) {
            if(user.getName().toLowerCase(Locale.ENGLISH).equals(username)) {
                target = user;
                break;
            }
        }
        if(target == null) {
            return;
        }
        userQueue.remove(target);
    }

    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
            userQueue.put(player, new UserLoginInfo(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch, 0));
            AppGlobals.NETWORK.sendTo(new MinecraftMessage("token"), (EntityPlayerMP) event.getEntity());
        }
    }

    @SubscribeEvent
    public void onCommandRequest(CommandEvent event) {
        if(userQueue.containsKey(event.getSender())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEntityEvent(EntityEvent event) {
        if(!(event.getEntity() instanceof EntityPlayerMP)) {
            return;
        }
        if(!userQueue.containsKey(event.getEntity())) {
            return;
        }
        if(event.isCancelable()) {
            event.setCanceled(true);
        }
        if(event.hasResult()) {
            event.setResult(Event.Result.DENY);
        }
    }

}
