package com.xxTFxx.siberianadv.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class Utils {

	public static ItemStack addStackToInventory(ItemStackHandler handler, ItemStack stack, boolean simulate) {
		ItemStack stackout = stack;
		for (int slot = 0; slot < handler.getSlots(); slot++) {
			stackout = handler.insertItem(slot, stack, simulate);
			if (stackout == ItemStack.EMPTY)
				break;
		}
		return stackout;
	}
	
	public static boolean isThereSpaceForStack(ItemStackHandler handler , ItemStack stack)
	{
		for(int i = 0 ; i < handler.getSlots() ; i++)
		{
			if(handler.getStackInSlot(i).isEmpty() || (handler.getStackInSlot(i).getItem() == stack.getItem() && handler.getStackInSlot(i).getCount() < stack.getMaxStackSize()) )
			{
				return true;
			}
		}
		return false;
	}
}
