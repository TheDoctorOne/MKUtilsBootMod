package net.mahmutkocas.reservermod.server.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SideOnly(Side.SERVER)
public class ServerUserEvents {

    private static final Integer TIMEOUT_TICK = 5;
    private final Map<EntityPlayerMP, Integer> userQueue = new ConcurrentHashMap<>();

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
            Integer tickCount = userQueue.get(user);
            if(tickCount > TIMEOUT_TICK) {
                user.connection.disconnect(new TextComponentString("Giriş Yapınız!"));
            }
            toRemove.add(user);
        }

        for (EntityPlayerMP user : toRemove) {
            userQueue.remove(user);
        }
    }

    public void userConfirmed(String username) {
        EntityPlayerMP target = null;
        for(EntityPlayerMP user : userQueue.keySet()) {
            if(user.getName().equals(username)) {
                target = user;
                break;
            }
        }
        userQueue.remove(target);
    }

    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof EntityPlayerMP) {
            userQueue.put((EntityPlayerMP) event.getEntity(), 0);
        }
    }
}
