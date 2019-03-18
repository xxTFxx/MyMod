package com.xxTFxx.siberianadv.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy{
	
	public void PreInit(FMLPreInitializationEvent event) {
		
	}
	public void init(FMLInitializationEvent event) {
		
	}
	public void PostInit(FMLPostInitializationEvent event) {
		
	}
	
	
    public static EntityPlayer getClientPlayer(){
        return Minecraft.getMinecraft().player;
    }
	
}
