package com.xxTFxx.siberianadv.tileentity;

import javax.annotation.Nullable;

import com.xxTFxx.siberianadv.init.ItemInit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TE_ConcreteMixer extends TileEntity implements ITickable{

	public ItemStackHandler handler = new ItemStackHandler(3)
	{
		protected void onContentsChanged(int slot) {
			TE_ConcreteMixer.this.markDirty();
		};
	};
	
	private int currentTime = 0;
	private int totalTime = 60;
	
	@Override
	public void update() {
		
		if(!handler.getStackInSlot(0).isEmpty() && !handler.getStackInSlot(1).isEmpty())
		{
			ItemStack stack1 = handler.getStackInSlot(0);
			ItemStack stack2 = handler.getStackInSlot(1);
			ItemStack stack3 = handler.getStackInSlot(2);
			
			if((stack1.getItem() == Item.getItemFromBlock(Blocks.SAND) || stack1.getItem() == Item.getItemFromBlock(Blocks.COBBLESTONE)) && stack1.getCount() >= 4)
			{
				if((stack2.getItem() == Item.getItemFromBlock(Blocks.SAND) || stack2.getItem() == Item.getItemFromBlock(Blocks.COBBLESTONE)) && stack2.getCount() >= 4)
				{
					if(stack3.isEmpty() || (stack3.getItem() == ItemInit.CEMENT_BUCKET && stack3.getCount() < 64))
					{
						currentTime++;
						if(currentTime == totalTime)
						{
							if(stack3.isEmpty())
							{
								handler.insertItem(2, new ItemStack(ItemInit.CEMENT_BUCKET), false);					
							}
							else
							{
								handler.getStackInSlot(2).grow(1);
							}
							handler.getStackInSlot(0).shrink(4);
							handler.getStackInSlot(1).shrink(4);
							currentTime = 0;
						}
						
					}
				}
			}
		}
		
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T)this.handler;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
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
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.currentTime = compound.getInteger("currentTime");
	}
	

}
