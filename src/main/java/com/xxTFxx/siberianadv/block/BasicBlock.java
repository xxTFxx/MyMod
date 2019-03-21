package com.xxTFxx.siberianadv.block;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.tabs.ModTab;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BasicBlock extends Block{
	public BasicBlock(Material material , String name) {
		this(material, SoundType.STONE , name);
		BlockInit.blocks.add(this);
	}
	public BasicBlock(Material material , SoundType sound , String name) {
		super(material);
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setRegistryName(name);
		setCreativeTab(ModTab.Mod_Tab);
		setSoundType(sound);
		setHardness(0.5F);
		BlockInit.blocks.add(this);
	}
	
	public BasicBlock(Material material , SoundType sound , String name , float hardness){
		super(material);
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setRegistryName(name);
		setCreativeTab(ModTab.Mod_Tab);
		setSoundType(sound);
		setHardness(hardness);
		BlockInit.blocks.add(this);
	}
	
	
}
