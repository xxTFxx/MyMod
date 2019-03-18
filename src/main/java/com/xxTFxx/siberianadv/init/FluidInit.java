package com.xxTFxx.siberianadv.init;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.fluids.Petroleum;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidInit {

	public static final Fluid PETROLEUM_FLUID = new Petroleum("petroleum" , new ResourceLocation(Main.MOD_ID , "blocks/petroleum_still") , new ResourceLocation(Main.MOD_ID , "blocks/petroleum_flow"));

	public static void registerFluids()
	{
		registerFluid(PETROLEUM_FLUID);
	}
	
	private static void registerFluid(Fluid fluid)
	{
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
	}
}
