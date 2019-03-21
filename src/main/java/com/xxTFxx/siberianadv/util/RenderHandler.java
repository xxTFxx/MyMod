package com.xxTFxx.siberianadv.util;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.entity.EntitySiberiaMan;
import com.xxTFxx.siberianadv.entity.render.RenderSiberiaMan;
import com.xxTFxx.siberianadv.init.BlockInit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {
	
	public static void registerEntityRenders()
	{
		/*RenderingRegistry.registerEntityRenderingHandler(EntitySiberiaMan.class, new IRenderFactory<EntitySiberiaMan>() {
			@Override
			public Render<? super EntitySiberiaMan> createRenderFor(RenderManager manager) {
				
				return new RenderSiberiaMan(manager);
			}
		});*/
	}
	
	public static void registerCustomMeshesAndStates()
	{
		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(BlockInit.PETROLEUM_BLOCK), new ItemMeshDefinition() {
			
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation(Main.MOD_ID + ":petroleum" , "fluid");
			}
		});
		
		ModelLoader.setCustomStateMapper(BlockInit.PETROLEUM_BLOCK, new StateMapperBase() {
			
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(Main.MOD_ID + ":petroleum" , "fluid");
			}
		});
	}
	
	
}
