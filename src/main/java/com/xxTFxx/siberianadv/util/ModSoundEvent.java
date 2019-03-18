package com.xxTFxx.siberianadv.util;

import com.xxTFxx.siberianadv.Main;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModSoundEvent{
	
	public static SoundEvent SOVIET_ANTHEM;
	
	public static void registerSounds() {
		SOVIET_ANTHEM = registerSound("music.soviet_anthem");
	}
	
	private static SoundEvent registerSound(String name) {
		SoundEvent event = new SoundEvent(new ResourceLocation(Main.MOD_ID , name));
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
	
}
