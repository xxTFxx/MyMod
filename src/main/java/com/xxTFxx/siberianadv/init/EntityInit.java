package com.xxTFxx.siberianadv.init;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.entity.EntitySiberiaBear;
import com.xxTFxx.siberianadv.entity.EntitySiberiaMan;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.NewRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit {
 
	public static void registerEnities()
	{
		registerEntity("siberia_man", EntitySiberiaMan.class, Main.ENTITY_SIBERIAMAN, 20, 487576, 487576);
		registerEntity("siberia_bear", EntitySiberiaBear.class, Main.ENTITY_SIBERIABEAR, 30, 387576, 487576);
	}
	
	
	private static void registerEntity(String name , Class<? extends Entity> entity , int id , int range , int color1 , int color2)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Main.MOD_ID + ":" + name), entity, name, id, Main.MOD_ID, range, 1, true, color1, color2);
	}
	
}
