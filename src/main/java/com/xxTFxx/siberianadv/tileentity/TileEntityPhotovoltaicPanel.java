package com.xxTFxx.siberianadv.tileentity;

import com.xxTFxx.siberianadv.block.machines.PhotovoltaicPanel;
import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;
import com.xxTFxx.siberianadv.init.BlockInit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityPhotovoltaicPanel extends TileEntity implements ITickable{

	private CustomEnergyStorage storage = new CustomEnergyStorage(10000, 0 , 500);
	private int capacity = storage.getEnergyStored();
	private int output = 100;
	private int ENERGY_GAIN_DAYTIME = 4;
	private int ENERGY_GAIN_NIGHTTIME = 1;
	
	private EnumFacing facingNorth = (EnumFacing.NORTH);
	private EnumFacing facingSouth = (EnumFacing.SOUTH);
	private EnumFacing facingWest = (EnumFacing.WEST);
	private EnumFacing facingEast = (EnumFacing.EAST);
	
	@Override
	public void update() {
		if(world.isDaytime())
		{
			if(world.canBlockSeeSky(getPos().up()) && storage.getEnergyStored() <= storage.getMaxEnergyStored() - ENERGY_GAIN_DAYTIME)
			{
				storage.addEnergy(ENERGY_GAIN_DAYTIME);					
			}
		}
		else if(world.canBlockSeeSky(getPos().up()) && storage.getEnergyStored() <= storage.getMaxEnergyStored() - ENERGY_GAIN_NIGHTTIME)
		{
			storage.addEnergy(ENERGY_GAIN_NIGHTTIME);	
		}
		
		sendEnergy();
	}
	
	private void sendEnergy() {
		if(getEnergyStored() >= output)
		{
				if(getBlockMetadata() == 2)
				{
					TileEntity tile = world.getTileEntity(pos.offset(facingNorth));
					if(tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, facingNorth.getOpposite()))
					{
						IEnergyStorage handler = tile.getCapability(CapabilityEnergy.ENERGY, facingNorth.getOpposite());
						if(handler != null && handler.canReceive() && handler.getEnergyStored() + output <= handler.getMaxEnergyStored())
						{
							handler.receiveEnergy(output, false);
							storage.consumeEnergy(output);
						}
						else return;
					}
				}
				if(getBlockMetadata() == 0)
				{
					TileEntity tile = world.getTileEntity(pos.offset(facingSouth));
					if(tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, facingSouth.getOpposite()))
					{
						IEnergyStorage handler = tile.getCapability(CapabilityEnergy.ENERGY, facingSouth.getOpposite());
						if(handler != null && handler.canReceive() && handler.getEnergyStored() + output <= handler.getMaxEnergyStored())
						{
							handler.receiveEnergy(output, false);
							storage.consumeEnergy(output);
						}
						else return;
					}
				}
				if(getBlockMetadata() == 1)
				{
					TileEntity tile = world.getTileEntity(pos.offset(facingWest));
					if(tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, facingWest.getOpposite()))
					{
						IEnergyStorage handler = tile.getCapability(CapabilityEnergy.ENERGY, facingWest.getOpposite());
						if(handler != null && handler.canReceive() && handler.getEnergyStored() + output <= handler.getMaxEnergyStored())
						{
							handler.receiveEnergy(output, false);
							storage.consumeEnergy(output);
						}
						else return;
					}
				}
				if(getBlockMetadata() == 3)
				{
					TileEntity tile = world.getTileEntity(pos.offset(facingEast));
					if(tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, facingEast.getOpposite()))
					{
						IEnergyStorage handler = tile.getCapability(CapabilityEnergy.ENERGY, facingEast.getOpposite());
						if(handler != null && handler.canReceive() && handler.getEnergyStored() + output <= handler.getMaxEnergyStored())
						{
							handler.receiveEnergy(output, false);
							storage.consumeEnergy(output);
						}
						else return;
					}
				}		
		}
	}
	
	public int getEnergyStored()
	{
		return this.storage.getEnergyStored();
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability.equals(CapabilityEnergy.ENERGY))
		{
			return (T)this.storage;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		if(capability.equals(CapabilityEnergy.ENERGY))
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		this.storage.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.storage.readFromNBT(compound);
	}
	
	public int getField(int id)
	{
		switch (id) {
		case 0:
			return this.storage.getEnergyStored();
		case 1:
			return this.storage.getMaxEnergyStored();
		default:
			return 0;
		}
	}
	
	public void setField(int id , int value)
	{
		switch (id) {
		case 0:
			this.storage.setEnergy(value);
		}
	}	
	
	@Override
	public final NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.getPos(), 1, this.getUpdateTag());
	}
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
}
