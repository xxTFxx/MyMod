package com.xxTFxx.siberianadv.container;


import com.xxTFxx.siberianadv.tileentity.TileEntityInductionFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerInductionFurnace extends Container{
	
	private TileEntityInductionFurnace tileentity;
	private int energy;
	private int cookTime;
	
	public ContainerInductionFurnace(InventoryPlayer player , TileEntityInductionFurnace tileentity) {
		this.tileentity = tileentity;
		
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		this.addSlotToContainer(new SlotItemHandler(handler, 0, 8, 33));
		this.addSlotToContainer(new SlotItemHandler(handler, 1, 25, 33));
		this.addSlotToContainer(new SlotItemHandler(handler, 2, 42, 33));
		this.addSlotToContainer(new SlotItemHandler(handler, 3, 96, 33));
		this.addSlotToContainer(new SlotItemHandler(handler, 4, 113, 33));
		this.addSlotToContainer(new SlotItemHandler(handler, 5, 130, 33));
		
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
	


}
