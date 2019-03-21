package com.xxTFxx.siberianadv.util;

import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.init.ModItems;

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
		registerModel(ModItems.STALINIUM_INGOT);
		registerModel(ModItems.SOVIET_ANTHEM);
		registerModel(ModItems.STALINIUM_SWORD);		
		registerModel(ModItems.COFFECUP);
		registerModel(ModItems.BEAR_FUR);
		registerModel(ModItems.BEAR_MEAT_RAW);
		registerModel(ModItems.BEAR_MEAT_COOKED);
		registerModel(ModItems.USHANKA);
		
		registerModel(Item.getItemFromBlock(BlockInit.STALINIUM_BLOCK));
		registerModel(Item.getItemFromBlock(BlockInit.BARBEDWIRE));
		registerModel(Item.getItemFromBlock(BlockInit.BARBEDWIREFENCE));
		registerModel(Item.getItemFromBlock(BlockInit.CUP));
		registerModel(Item.getItemFromBlock(BlockInit.PHOTOVOLTAICPANEL_BLOCK));
		registerModel(Item.getItemFromBlock(BlockInit.ELECTRIC_FURNACE_ITIER));
		registerModel(Item.getItemFromBlock(BlockInit.PORTABLE_GENERATOR));
		
	}
	private static void registerModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
}
 