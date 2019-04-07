package com.xxTFxx.siberianadv.item;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.init.ItemInit;
import com.xxTFxx.siberianadv.tabs.ModTab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item{

	public ItemBase(String name)
	{
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setRegistryName(name);
		setCreativeTab(ModTab.Mod_Tab);

	}
	


}
