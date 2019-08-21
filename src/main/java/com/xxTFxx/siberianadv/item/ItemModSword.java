package com.xxTFxx.siberianadv.item;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.init.ItemInit;
import com.xxTFxx.siberianadv.tabs.ModTab;

import net.minecraft.item.ItemSword;

public class ItemModSword extends ItemSword{
	
	public ItemModSword(ToolMaterial material , float damage , float speed , String name) {
		super(material);
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setRegistryName(name);
		setCreativeTab(ModTab.Mod_Tab);
		ItemInit.items.add(this);
	}
	
	public ItemModSword(ToolMaterial material , String name) {
		this(material ,material.getAttackDamage() , material.getEfficiency() , name);
	}
}
