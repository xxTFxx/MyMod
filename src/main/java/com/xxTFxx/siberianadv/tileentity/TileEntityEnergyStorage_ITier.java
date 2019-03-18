package com.xxTFxx.siberianadv.tileentity;

import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityEnergyStorage_ITier extends TileEntity implements ITickable{
	
	private CustomEnergyStorage storage = new CustomEnergyStorage(500000 , 1000);
	private int capacity = storage.getMaxEnergyStored();
	
	@Override
	public void update() {
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		//compound.setInteger("GUIEnergy", this.energy);
		this.storage.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		//this.energy = compound.getInteger("GUIEnergy");
		this.storage.readFromNBT(compound);
	}
	
	public int getEnergyStored()
	{
		return this.storage.getEnergyStored();
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	public int getField(int id)
	{
		switch (id) {
		case 0:
			return this.storage.getEnergyStored();
		case 1:
			return this.capacity;
		default:
			return 0;
		}
	}
	
	 @Override
	    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

	        if (capability == CapabilityEnergy.ENERGY) {
	            return true;
	        }
	        return super.hasCapability(capability, facing);
	    }

	    @Override
	    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

	        if (capability == CapabilityEnergy.ENERGY) {
	            return CapabilityEnergy.ENERGY.cast(storage);
	        }
	        return super.getCapability(capability, facing);
	    }

	public void setField(int id, int value) {
		switch(id) {
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
