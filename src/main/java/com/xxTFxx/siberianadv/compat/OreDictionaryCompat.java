package com.xxTFxx.siberianadv.compat;

import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.init.ItemInit;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryCompat {
	public static void registerOres()
	{
		OreDictionary.registerOre("dustIron", ItemInit.IRON_DUST);
		

	}
	


}
