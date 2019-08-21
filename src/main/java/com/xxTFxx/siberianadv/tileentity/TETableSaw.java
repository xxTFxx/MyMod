package com.xxTFxx.siberianadv.tileentity;

import javax.annotation.Nullable;

import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;
import com.xxTFxx.siberianadv.init.ItemInit;
import com.xxTFxx.siberianadv.util.InventoryCrafingNoContainer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TETableSaw extends BasicEnergyTile implements ITickable{
	
	public ItemStackHandler handler = new ItemStackHandler(3)
	{
		protected void onContentsChanged(int slot) {
			TETableSaw.this.markDirty();
		};
	};
	
	private CustomEnergyStorage storage = new CustomEnergyStorage(10000 , 500 , 0);
	private int currentTime = 0;
	private int totalTime = 40;
	private int ENERGY_DRAIN = 10;
	private int amount , dustAmount , energy;
	private boolean shouldUpdate = false;
	private InventoryCrafingNoContainer crafting = new InventoryCrafingNoContainer(3, 3);
	
	@Override
	public void update() {
		
		if(energy != storage.getEnergyStored())
		{
			shouldUpdate = true;
			energy = storage.getEnergyStored();
		}
		
		if(!handler.getStackInSlot(0).isEmpty() && storage.getEnergyStored() >= ENERGY_DRAIN )
		{
			int id[] = OreDictionary.getOreIDs(handler.getStackInSlot(0));
			
			if(id.length > 0)
			{
				
				String name = OreDictionary.getOreName(id[0]);
				
				if(name == "logWood")
				{
					amount = world.rand.nextInt(3) + 4;
					dustAmount = world.rand.nextInt(3) + 1;
					if(canProcess())
					{
						currentTime++;
						storage.consumeEnergy(ENERGY_DRAIN);
						shouldUpdate = true;
						if(currentTime == totalTime)
						{
							ItemStack logWood = handler.getStackInSlot(0);
							
							crafting.setInventorySlotContents(0, logWood);
							ItemStack resultEntry = CraftingManager.findMatchingResult(crafting, null);
							
							if (!resultEntry.isEmpty()) {
								ItemStack result = resultEntry.copy();
								result.setCount(amount);
								handler.insertItem(1, result, false);
								handler.getStackInSlot(0).shrink(1);
							}
							
							ItemStack dust = new ItemStack(ItemInit.SAWDUST, dustAmount);
							handler.insertItem(2, dust, false);
							
							currentTime = 0;				
						}
					}
				}
			}
			else if(currentTime != 0)
			{
				currentTime = 0;
			}
			
		}
		else if(currentTime != 0)
		{
			currentTime = 0;
		}
		
		if(shouldUpdate)
		{
			shouldUpdate = false;
			sendUpdates();
		}
		
	}
		
	private boolean canProcess()
	{
		ItemStack logWood = handler.getStackInSlot(0);
		crafting.setInventorySlotContents(0, logWood);
		ItemStack resultEntry = CraftingManager.findMatchingResult(crafting, null);
		
		if(handler.getStackInSlot(1).isEmpty())
		{
			if(handler.getStackInSlot(2).isEmpty() || handler.getStackInSlot(2).getCount() + dustAmount <= 64)
			{
				return true;
			}
		}
		else if(handler.getStackInSlot(1).getCount() <= 64 - amount && handler.getStackInSlot(1).getItem() == resultEntry.getItem() && (handler.getStackInSlot(2).isEmpty() || handler.getStackInSlot(2).getCount() + dustAmount <= 64))
		{
			return true;
		}
		return false;
	}
	
	public int getTime()
	{
		return this.currentTime;
	}
	
	public int getTotalTime()
	{
		return this.totalTime;
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	public int getEnergyStored()
	{
		return this.storage.getEnergyStored();
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
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY)
		{
			return (T)this.storage;
		}
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T)this.handler;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY)
		{
			return true;
		}
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		this.storage.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("currentTime", this.currentTime);
		super.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.storage.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.currentTime = compound.getInteger("currentTime");
	}

	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
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
