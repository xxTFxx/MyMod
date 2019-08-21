package com.xxTFxx.siberianadv.container;


import com.xxTFxx.siberianadv.tileentity.TEPress;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContPress extends Container{
	
	private final TEPress te;
	
	public ContPress(InventoryPlayer player , TEPress te) {
		this.te = te;
		
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
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
		return this.te.isUsableByPlayer(playerIn);
	}
}