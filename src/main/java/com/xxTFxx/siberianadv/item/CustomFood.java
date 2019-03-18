package com.xxTFxx.siberianadv.item;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.tabs.ModTab;

import net.minecraft.item.ItemFood;

public class CustomFood extends ItemFood{

	public CustomFood(String name , int amount , boolean isWolfFood) {
		super(amount, isWolfFood);
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setRegistryName(name);
		setCreativeTab(ModTab.Mod_Tab);
	}
}
