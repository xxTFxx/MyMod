package com.xxTFxx.siberianadv.tabs;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.init.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModTab extends CreativeTabs{
	
	public ModTab(String name) {
		super(Main.MOD_ID + "." + name);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.STALINIUM_INGOT);
	}
	
	public static final ModTab Mod_Tab = new ModTab("mod_tab") ;
}
