package com.xxTFxx.siberianadv.container;

import com.xxTFxx.siberianadv.tileentity.TileEntityElectricFurnace_ITier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerElectricFurnace_ITier extends Container{
	
	private TileEntityElectricFurnace_ITier tileentity;
	private int energy;
	private int cookTime;
	
	public ContainerElectricFurnace_ITier(InventoryPlayer player , TileEntityElectricFurnace_ITier tileentity) {
		this.tileentity = tileentity;
		
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 32, 33));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 98, 33));
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
			}
		}
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileentity.isUsableByPlayer(playerIn);
	}
	
	@Override
	public void updateProgressBar(int id, int data) {
		this.tileentity.setField(id, data);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i=0 ; i < this.listeners.size() ; i++)
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);
			if(this.energy != this.tileentity.getField(0))
			{
				listener.sendWindowProperty(this, this.energy, this.tileentity.getField(0));
			}
			if(this.cookTime != this.tileentity.getField(2))
			{
				listener.sendWindowProperty(this, 1, this.tileentity.getField(2));
			}
		}
		
		this.energy = this.tileentity.getField(0);
		this.cookTime = this.tileentity.getField(2);
	}
	
	 public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	    {
		 	ItemStack stack = ItemStack.EMPTY;
			Slot slot = (Slot)this.inventorySlots.get(index);
			
			if(slot != null && slot.getHasStack())
			{
				ItemStack stack1 = slot.getStack();
				stack = stack1.copy();
				
				if(index >= 0 && index < 27)
				{
					if(!this.mergeItemStack(stack1, 27, 36, false)) return ItemStack.EMPTY;
				}
				else if(index >= 27 && index < 36)
				{
					if(!this.mergeItemStack(stack1, 0, 27, false)) return ItemStack.EMPTY;
				}
				else if(!this.mergeItemStack(stack1, 0, 36, false))
				{
					return ItemStack.EMPTY;
				}
				
				if(stack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
				else slot.onSlotChanged();
				
				if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
				slot.onTake(playerIn, stack1);
			}
			
			return stack;
		}	

}
