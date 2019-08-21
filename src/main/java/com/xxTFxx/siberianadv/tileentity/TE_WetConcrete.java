package com.xxTFxx.siberianadv.tileentity;

import com.xxTFxx.siberianadv.block.blocks.WetConcrete;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TE_WetConcrete extends TileEntity implements ITickable{
	
	private int timer = 0;
	
	@Override
	public void update() {
		
		if(timer == 160)
		{
			WetConcrete.changeState(world, pos);
		}
		
		timer++;
		
	}

}
