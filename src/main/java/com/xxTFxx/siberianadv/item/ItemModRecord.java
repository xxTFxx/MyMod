package com.xxTFxx.siberianadv.item;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.tabs.ModTab;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;

public class ItemModRecord extends ItemRecord{

	public ItemModRecord(String name, SoundEvent soundIn) {
		super(name, soundIn);
		
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setRegistryName(Main.MOD_ID , name);
		setCreativeTab(ModTab.Mod_Tab);
	}
	

}
