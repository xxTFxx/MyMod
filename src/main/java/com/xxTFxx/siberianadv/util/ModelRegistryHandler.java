package com.xxTFxx.siberianadv.util;

import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.init.ItemInit;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class ModelRegistryHandler {
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		registerModel(ItemInit.STALINIUM_INGOT);
		registerModel(ItemInit.SOVIET_ANTHEM);
		registerModel(ItemInit.STALINIUM_SWORD);		
		registerModel(ItemInit.COFFECUP);
		registerModel(ItemInit.BEAR_FUR);
		registerModel(ItemInit.BEAR_MEAT_RAW);
		registerModel(ItemInit.BEAR_MEAT_COOKED);
		registerModel(ItemInit.USHANKA);
		registerModel(ItemInit.IRON_DUST);
		registerModel(ItemInit.SAWDUST);
		registerModel(ItemInit.PELLET);
		registerModel(ItemInit.PORTABLE_GENERATOR);

		registerModel(Item.getItemFromBlock(BlockInit.STALINIUM_BLOCK));
		registerModel(Item.getItemFromBlock(BlockInit.BARBEDWIRE));
		registerModel(Item.getItemFromBlock(BlockInit.BARBEDWIREFENCE));
		registerModel(Item.getItemFromBlock(BlockInit.CUP));
		registerModel(Item.getItemFromBlock(BlockInit.PHOTOVOLTAICPANEL_BLOCK));
		registerModel(Item.getItemFromBlock(BlockInit.ELECTRIC_FURNACE));
		//registerModel(Item.getItemFromBlock(BlockInit.PORTABLE_GENERATOR));
		registerModel(Item.getItemFromBlock(BlockInit.GRINDER));
		//registerModel(Item.getItemFromBlock(BlockInit.SIMPLE_GENERATOR));
		registerModel(Item.getItemFromBlock(BlockInit.INDUCTION_FURNACE));
		registerModel(Item.getItemFromBlock(BlockInit.TABLE_SAW));
		registerModel(Item.getItemFromBlock(BlockInit.ELECTRIC_ANVIL));
		registerModel(Item.getItemFromBlock(BlockInit.CEILINGLIGHT));
		registerModel(Item.getItemFromBlock(BlockInit.WET_CONCRETE));
		registerModel(Item.getItemFromBlock(BlockInit.CONCRETE));
		registerModel(Item.getItemFromBlock(BlockInit.CONNECTOR));
		registerModel(Item.getItemFromBlock(BlockInit.CONCRETE_MIXER));



	}
	private static void registerModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
}
 