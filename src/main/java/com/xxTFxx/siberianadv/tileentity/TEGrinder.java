package com.xxTFxx.siberianadv.tileentity;

import javax.annotation.Nullable;

import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;
import com.xxTFxx.siberianadv.recipes.GrinderRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TEGrinder extends BasicEnergyTile implements ITickable{
	
	public ItemStackHandler handler = new ItemStackHandler(2)
	{
		protected void onContentsChanged(int slot) {
			TEGrinder.this.markDirty();
		};
	};
	
	private CustomEnergyStorage storage = new CustomEnergyStorage(10000, 500, 0);
	private int itemAmount;
	private int currentTime = 0;
	private int totalTime = 60;
	private int ENERGY_DRAIN = 10;
	
	@Override
	public void update() {

		
		
		if(!handler.getStackInSlot(0).isEmpty() && canSmelt() && storage.getEnergyStored() >= ENERGY_DRAIN)
		{
			itemAmount = GrinderRecipes.instance().getResultAmount(handler.getStackInSlot(0));
			if(handler.getStackInSlot(1).getCount() + itemAmount <= 64)
			{
				currentTime++;
				storage.consumeEnergy(ENERGY_DRAIN);
				if(currentTime == totalTime)
				{
					if(handler.getStackInSlot(1).getCount() > 0)
					{
						handler.getStackInSlot(1).grow(itemAmount);
					}
					else
					{
						handler.insertItem(1, GrinderRecipes.instance().getResult(handler.getStackInSlot(0)).copy(), false);
						handler.getStackInSlot(1).grow(itemAmount - 1);
					}
					handler.getStackInSlot(0).shrink(1);
					currentTime = 0;
				}
			}
		}
		else if(currentTime != 0)
		{
			currentTime = 0;
		}
		if(currentTime != 0 && handler.getStackInSlot(0).isEmpty())
		{
			currentTime = 0;
		}
		
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	public int getEnergyStored()
	{
		return this.storage.getEnergyStored();
	}
	
	private boolean canSmelt()
	{
		if(!GrinderRecipes.instance().getResult(this.handler.getStackInSlot(0)).isEmpty())
		{
			if(this.handler.getStackInSlot(1).isEmpty())
			{
				return true;
			}
			else if(GrinderRecipes.instance().getResult(this.handler.getStackInSlot(0)).getItem() == this.handler.getStackInSlot(1).getItem())
			{
				return true;
			}
		}
		return false;
	}
	
	public int getCurrentTime()
	{
		return this.currentTime;
	}
	
	public int getTotalTime()
	{
		return this.totalTime;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T)this.handler;
		}
		if(capability == CapabilityEnergy.ENERGY)
		{
			return (T)this.storage;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}
		if(capability == CapabilityEnergy.ENERGY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("currentTime", this.currentTime);
		this.storage.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.currentTime = compound.getInteger("currentTime");
		this.storage.readFromNBT(compound);
	}
	
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}

}
