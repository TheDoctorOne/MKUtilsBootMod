package net.mahmutkocas.reservermod;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class AppGlobals {

    public static Side SIDE;

    public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper("mkreservermod");
}
