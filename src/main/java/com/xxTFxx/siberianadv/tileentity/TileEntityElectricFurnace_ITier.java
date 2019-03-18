package com.xxTFxx.siberianadv.tileentity;


import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityElectricFurnace_ITier extends TileEntity implements ITickable{

	
	public ItemStackHandler handler = new ItemStackHandler(2)
			{
				protected void onContentsChanged(int slot) {
					TileEntityElectricFurnace_ITier.this.markDirty();
				};
			};
	private CustomEnergyStorage storage = new CustomEnergyStorage(10000 , 1000);
	private int capacity = storage.getMaxEnergyStored();
	private int a = 4;
	private int cookTime;
	private int totalCookTime = 20;
	
	@Override
	public void update() {
		
		if(storage.getEnergyStored() > a && !handler.getStackInSlot(0).isEmpty() && handler.getStackInSlot(1).getCount() < 64)
		{
			if(canSmelt() )
			{
				cookTime++;
				storage.consumeEnergy(a);
				if(cookTime == totalCookTime)
				{
					if(handler.getStackInSlot(1).getCount() > 0)
					{
						handler.getStackInSlot(1).grow(1);
					}
					else
					{
						handler.insertItem(1, FurnaceRecipes.instance().getSmeltingResult(handler.getStackInSlot(0)).copy(), false);					
					}
					handler.getStackInSlot(0).shrink(1);
					cookTime = 0;
				}
			}
			else if(cookTime != 0)
			{
				cookTime = 0;
			}
		}
		else if(cookTime != 0)
		{
			cookTime = 0;
		}
		if(cookTime != 0 && handler.getStackInSlot(0).isEmpty())
		{
			cookTime = 0;
		}
	}
	
	private boolean canSmelt()
	{
		if(!FurnaceRecipes.instance().getSmeltingResult(handler.getStackInSlot(0)).isEmpty())
		{
			if(handler.getStackInSlot(1).isEmpty())
			{
				return true;
			}
			else if(FurnaceRecipes.instance().getSmeltingResult(handler.getStackInSlot(0)).getItem() == handler.getStackInSlot(1).getItem())
			{
				return true;
			}
			else return false;
		}
		return false;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("cookTime", this.cookTime);
		this.storage.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.cookTime = compound.getInteger("cookTime");
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
	
	
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		if(capability.equals(CapabilityEnergy.ENERGY))
		{
			return true;
		}
		if(capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
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
		if(capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
		{
			return (T)this.handler;
		}
		return super.getCapability(capability, facing);
	}
	    
	    public int getField(int id)
		{
			switch (id) {
			case 0:
				return this.storage.getEnergyStored();
			case 1:
				return this.capacity;
			case 2:
				return this.cookTime;
			case 3:
				return this.totalCookTime;
			default:
				return 0;
			}
		}

	public void setField(int id, int value) {
		switch(id) {
			case 0:
				this.storage.setEnergy(value);
			case 1:
				this.cookTime = value;
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