package com.xxTFxx.siberianadv.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage{
	
	public CustomEnergyStorage(int capacity)
    {
        super(capacity, capacity, capacity, 0);
    }

    public CustomEnergyStorage(int capacity, int maxTransfer)
    {
        super(capacity, maxTransfer, maxTransfer, 0);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract)
    {
        super(capacity, maxReceive, maxExtract, 0);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy)
    {
        super(capacity , maxReceive , maxExtract , energy);
    }
    
    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) 
    {
    	if (!canReceive())
                return 0;

            int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
            if (!simulate)
                this.energy += energyReceived;
            return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) 
    {
    	if (!canExtract())
                return 0;

            int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
            if (!simulate)
                this.energy -= energyExtracted;
            return energyExtracted;
    }
    
    public void consumeEnergy(int energyO)
    {
    	this.energy -= energyO;
    }
    
    public void addEnergy(int energyInput)
    {
    	this.energy += energyInput;
    }
    
    public void setEnergy(int energy)
    {
    	this.energy = energy;
    }
    
   @Override
    public int getEnergyStored() {
    	return super.getEnergyStored();
    }
    
    @Override
    public int getMaxEnergyStored() {
    	return super.getMaxEnergyStored();
    }
    
    @Override
    public boolean canExtract() {
    	return super.canExtract();
    	//return true;
    }
    
    @Override
    public boolean canReceive() {
    	return super.canReceive();
    	//return true;
    }

    
    public void readFromNBT(NBTTagCompound compound)
    {
    	this.energy = compound.getInteger("Energy");
    	this.maxExtract = compound.getInteger("MaxExtract");
    	this.maxReceive = compound.getInteger("MaxReceive");
    	this.capacity = compound.getInteger("Capacity");

    }
    
    public void writeToNBT(NBTTagCompound compound)
    {
    	compound.setInteger("Energy", this.energy);
    	compound.setInteger("MaxExtract", this.maxExtract);
    	compound.setInteger("MaxReceive", this.maxReceive);
    	compound.setInteger("Capacity", this.capacity);
    }
    
}
