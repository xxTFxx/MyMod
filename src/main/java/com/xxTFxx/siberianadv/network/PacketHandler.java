package com.xxTFxx.siberianadv.network;

import com.xxTFxx.siberianadv.Main;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	public static SimpleNetworkWrapper INSTANCE; 
	
	public static void init() {
		
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Main.MOD_ID);
		
		INSTANCE.registerMessage(MyMessageHandler.class, MyMessage.class, 0, Side.SERVER);
		INSTANCE.registerMessage(MessageOnButtonPressed.class, MessageOnButtonPressed.class, 1, Side.SERVER);
	
	}
	
}
