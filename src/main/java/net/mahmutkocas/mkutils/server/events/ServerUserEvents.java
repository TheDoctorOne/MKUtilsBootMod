package net.mahmutkocas.mkutils.server.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.mahmutkocas.mkutils.AppGlobals;
import net.mahmutkocas.mkutils.common.MinecraftMessage;
import net.mahmutkocas.mkutils.server.mc.LoginBlockContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SideOnly(Side.SERVER)
public class ServerUserEvents {

    public static final ServerUserEvents INSTANCE = new ServerUserEvents();

    private ServerUserEvents() {
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event) {
    }


    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent event) {
    }

    @SubscribeEvent
    public void onCommandRequest(CommandEvent event) {
    }

    @SubscribeEvent
    public void onEntityEvent(EntityEvent event) {
    }

    @SubscribeEvent
    public void onItemTossEvent(ItemTossEvent event) {
    }
}
