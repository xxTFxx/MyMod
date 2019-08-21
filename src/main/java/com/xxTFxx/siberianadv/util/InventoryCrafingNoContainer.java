package com.xxTFxx.siberianadv.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;

public class InventoryCrafingNoContainer extends InventoryCrafting {

		private static final NullContainer nullContainer = new NullContainer();

		/* NULL INNER CLASS */
		public static class NullContainer extends Container {

			@Override
			public void onCraftMatrixChanged(IInventory inventory) {

			}

			@Override
			public boolean canInteractWith(EntityPlayer player) {

				return false;
			}

		}

		public InventoryCrafingNoContainer(int width, int height) {

			super(nullContainer, width, height);
		}

}
