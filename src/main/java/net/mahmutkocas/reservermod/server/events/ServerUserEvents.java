package net.mahmutkocas.reservermod.server.events;

import net.mahmutkocas.reservermod.AppGlobals;
import net.mahmutkocas.reservermod.common.MinecraftMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
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

    private static final Integer TIMEOUT_TICK = 40;
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
            if(user.hasDisconnected()) {
                toRemove.add(user);
                continue;
            }

            Integer tickCount = userQueue.get(user);
            if(tickCount > TIMEOUT_TICK) {
                user.connection.disconnect(new TextComponentString("Giriş Yapınız!"));
                toRemove.add(user);
                continue;
            }
            userQueue.put(user, tickCount+1);
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
            userQueue.put((EntityPlayerMP) event.getEntity(), 0);
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
    public void onKeyEvent(EntityEvent event) {
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
