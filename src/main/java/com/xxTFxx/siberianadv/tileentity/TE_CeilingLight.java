package com.xxTFxx.siberianadv.tileentity;

import com.xxTFxx.siberianadv.block.machines.CeilingLight;
import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;
import com.xxTFxx.siberianadv.init.BlockInit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TE_CeilingLight extends TileEntity implements ITickable{
	
	private CustomEnergyStorage storage = new CustomEnergyStorage(10000 , 1000 , 0);
	private int ENERGY_DRAIN = 2;
	private boolean working = false;
	private boolean turned = true;
	private boolean shouldUpdate = false;
	private int energy;
	
	@Override
	public void update() {
		
		if(energy != storage.getEnergyStored())
		{
			shouldUpdate = true;
			energy = storage.getEnergyStored();
			//world.notifyBlockUpdate(pos, getState(), getState(), 3);
		}
		
		if(storage.getEnergyStored() >= ENERGY_DRAIN && turned)
		{			
			storage.consumeEnergy(ENERGY_DRAIN);
			if(!working)
			{
				working = true;
				CeilingLight.setState(true, world, pos);
			}
			
		}
		else if(working || !turned)
		{
			CeilingLight.setState(false, world, pos);
			working = false;
			
		}
		
		if(shouldUpdate)
		{
			sendUpdates();
			shouldUpdate = false;
		}
	}
	
	public void changeMode()
	{
		this.turned = !this.turned;
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
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability.equals(CapabilityEnergy.ENERGY))
		{
			return (T)this.storage;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		this.storage.writeToNBT(compound);
		compound.setBoolean("Turned", this.turned);
		compound.setBoolean("Working", this.working);

		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.storage.readFromNBT(compound);
		this.turned = compound.getBoolean("Turned");
		this.working = compound.getBoolean("Working");

	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}
	
	private void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
		markDirty();
	}

}
