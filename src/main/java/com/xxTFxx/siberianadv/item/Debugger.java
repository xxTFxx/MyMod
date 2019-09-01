package com.xxTFxx.siberianadv.item;

import com.xxTFxx.siberianadv.tileentity.cables.TE_Connector;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class Debugger extends ItemBase{
	
	public Debugger(String name) {
		super(name);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {

		TileEntity tile = worldIn.getTileEntity(pos);
		
		if(tile != null)
		{
			if(tile.hasCapability(CapabilityEnergy.ENERGY, null))
			{
				IEnergyStorage handler = tile.getCapability(CapabilityEnergy.ENERGY, null);
				player.sendStatusMessage(new TextComponentTranslation("Energy:" + Integer.toString(handler.getEnergyStored())), true);
			}
			if(tile instanceof TE_Connector)
			{
				//player.sendStatusMessage(new TextComponentTranslation("LinkTo:" + ((TE_Connector) tile).hasConnectionTo()), true);
				//player.sendStatusMessage(new TextComponentTranslation("LinkFrom:" + ((TE_Connector) tile).hasConnectionFrom()), true);
			}
		}
		
		
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

}
