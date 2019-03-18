package com.xxTFxx.siberianadv.proxy;

import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {

	public void PreInit(FMLPreInitializationEvent event);
	public void init(FMLInitializationEvent event);
	public void PostInit(FMLPostInitializationEvent event);

}
