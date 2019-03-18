package com.xxTFxx.siberianadv.container;

import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler;

import com.xxTFxx.siberianadv.tileentity.TileEntityEnergyStorage_ITier;
import com.xxTFxx.siberianadv.tileentity.TileEntitySimpleGenerator;

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

public class ContainerEnergyStorage extends Container{
	
	private final TileEntityEnergyStorage_ITier tileentity;
	private int energy;
	
	public ContainerEnergyStorage(TileEntityEnergyStorage_ITier tileentity) {
		this.tileentity = tileentity;
		
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
			if(this.energy != tileentity.getField(0)) listener.sendWindowProperty(this, 0, this.tileentity.getField(0));
		}
		
		this.energy = this.tileentity.getField(0);
	}
	


}
