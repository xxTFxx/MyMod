package com.xxTFxx.siberianadv.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class Petroleum extends Fluid{
	
	public Petroleum(String name , ResourceLocation still , ResourceLocation flow) {
		super(name, still, flow);
		this.viscosity = 4000;
		this.density = 3000;
	}
	


}
