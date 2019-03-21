package com.xxTFxx.siberianadv.block;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.tabs.ModTab;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluid extends BlockFluidClassic{
	
	public BlockFluid(String name , Fluid fluid , Material material) {
		super(fluid, material);
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setRegistryName(name);
		setCreativeTab(ModTab.Mod_Tab);
		
		BlockInit.blocks.add(this);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

}
