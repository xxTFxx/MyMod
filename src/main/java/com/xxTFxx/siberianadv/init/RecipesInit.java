package com.xxTFxx.siberianadv.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipesInit {

	public static void init()
	{
		GameRegistry.addSmelting(ItemInit.IRON_DUST, new ItemStack(Items.IRON_INGOT), 0.6F);
	}
	
}
