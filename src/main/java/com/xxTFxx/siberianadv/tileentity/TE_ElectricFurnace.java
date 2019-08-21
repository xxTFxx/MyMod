package com.xxTFxx.siberianadv.tileentity;


import javax.annotation.Nullable;

import com.xxTFxx.siberianadv.block.machines.ElectricFurnace;
import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
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

public class TE_ElectricFurnace extends BasicEnergyTile implements ITickable{

	
	public ItemStackHandler handler = new ItemStackHandler(2)
			{
				protected void onContentsChanged(int slot) {
					TE_ElectricFurnace.this.markDirty();
				};
			};
	private CustomEnergyStorage storage = new CustomEnergyStorage(10000 , 1000);
	private int capacity = storage.getMaxEnergyStored();
	private int ENERGY_DRAIN = 4;
	private int cookTime;
	private int totalCookTime = 20;
	private boolean shouldUpdate = false;
	private boolean working = false;
	private int energy;
	
	@Override
	public void update() {
		
		if(storage.getEnergyStored() != energy)
		{
			energy = storage.getEnergyStored();
			//shouldUpdate = true;
			world.notifyBlockUpdate(pos, getState(), getState(), 3);
		}
		
		if(storage.getEnergyStored() > ENERGY_DRAIN && !handler.getStackInSlot(0).isEmpty() && handler.getStackInSlot(1).getCount() < 64)
		{
			if(canSmelt() )
			{
				cookTime++;
				storage.consumeEnergy(ENERGY_DRAIN);
				
				if(!working)
				{
					working = true;
					ElectricFurnace.setState(true, world, pos);
				}
					
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
				shouldUpdate = true;
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
		else if(working)
		{
			working = false;
			ElectricFurnace.setState(false, world, pos);
		}
		/*if(cookTime != 0 && handler.getStackInSlot(0).isEmpty())
		{
			cookTime = 0;
			ElectricFurnace.setState(false, world, pos);

		}*/
		
		if(shouldUpdate)
		{
			sendUpdates();
			shouldUpdate = false;
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
		compound.setBoolean("Working", this.working);
		this.storage.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.cookTime = compound.getInteger("cookTime");
		this.working = compound.getBoolean("Working");
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
	
	public int getCookTime()
	{
		return this.cookTime;
	}
	
	public int getTotalCookTime()
	{
		return this.totalCookTime;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		if(capability == CapabilityEnergy.ENERGY)
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
		if(capability == CapabilityEnergy.ENERGY)
		{
			return (T)this.storage;
		}
		if(capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
		{
			return (T)this.handler;
		}
		return super.getCapability(capability, facing);
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
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}
	
	private void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
		markDirty();
	}
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	
	
	

}