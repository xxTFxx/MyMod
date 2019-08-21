package com.xxTFxx.siberianadv.util;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Quaternion;

import com.xxTFxx.siberianadv.Main;

import com.xxTFxx.siberianadv.block.SnowMod;
import com.xxTFxx.siberianadv.compat.OreDictionaryCompat;
import com.xxTFxx.siberianadv.init.BiomeInit;
import com.xxTFxx.siberianadv.init.EntityInit;
import com.xxTFxx.siberianadv.init.FluidInit;
import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.init.ItemInit;
import com.xxTFxx.siberianadv.init.RecipesInit;
import com.xxTFxx.siberianadv.item.CoffeCup;
import com.xxTFxx.siberianadv.item.ItemBase;
import com.xxTFxx.siberianadv.item.ItemModRecord;
import com.xxTFxx.siberianadv.item.ItemModSword;
import com.xxTFxx.siberianadv.materials.ModMaterials;
import com.xxTFxx.siberianadv.network.PacketHandler;
import com.xxTFxx.siberianadv.render.ConnectorRender;
import com.xxTFxx.siberianadv.render.LineRender;
import com.xxTFxx.siberianadv.tileentity.TEGrinder;
import com.xxTFxx.siberianadv.tileentity.TEOilPump;
import com.xxTFxx.siberianadv.tileentity.TEPress;
import com.xxTFxx.siberianadv.tileentity.TESimpleMiner;
import com.xxTFxx.siberianadv.tileentity.TETableSaw;
import com.xxTFxx.siberianadv.tileentity.TE_CeilingLight;
import com.xxTFxx.siberianadv.tileentity.TE_ConcreteMixer;
import com.xxTFxx.siberianadv.tileentity.TE_ElectricAnvil;
import com.xxTFxx.siberianadv.tileentity.TE_ElectricFurnace;
import com.xxTFxx.siberianadv.tileentity.TileEntityEnergyStorage_ITier;
import com.xxTFxx.siberianadv.tileentity.TileEntityInductionFurnace;
import com.xxTFxx.siberianadv.tileentity.TileEntityPhotovoltaicPanel;
import com.xxTFxx.siberianadv.tileentity.TileEntityPortableGenerator;
import com.xxTFxx.siberianadv.tileentity.TileEntitySimpleGenerator;
import com.xxTFxx.siberianadv.tileentity.cables.TE_Connector;
import com.xxTFxx.siberianadv.util.handlers.GUIHandler;
import com.xxTFxx.siberianadv.world.biomes.BiomeSiberia;
import com.xxTFxx.siberianadv.world.gen.WorldGenCustomStructures;
import com.xxTFxx.siberianadv.world.gen.generators.WorldGenStructure;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnowBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class RegistryHandler {
	
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(BlockInit.blocks.toArray(new Block[0]));
	}
	
	private static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntitySimpleGenerator.class, new ResourceLocation(Main.MOD_ID + "solid_fluid_generator"));
		GameRegistry.registerTileEntity(TileEntityPhotovoltaicPanel.class, new ResourceLocation(Main.MOD_ID + ":photovoltaic_panel"));
		GameRegistry.registerTileEntity(TE_ElectricFurnace.class, new ResourceLocation(Main.MOD_ID + ":electric_furnace_1"));
		GameRegistry.registerTileEntity(TileEntityEnergyStorage_ITier.class, new ResourceLocation(Main.MOD_ID + ":electric_storage_1"));
		GameRegistry.registerTileEntity(TileEntityInductionFurnace.class, new ResourceLocation(Main.MOD_ID + ":induction_furnace"));
		GameRegistry.registerTileEntity(TileEntityPortableGenerator.class, new ResourceLocation(Main.MOD_ID + ":portable_generator"));
		GameRegistry.registerTileEntity(TEOilPump.class, new ResourceLocation(Main.MOD_ID + ":oil_pump"));
		GameRegistry.registerTileEntity(TEGrinder.class, new ResourceLocation(Main.MOD_ID + ":grinder"));
		GameRegistry.registerTileEntity(TESimpleMiner.class, new ResourceLocation(Main.MOD_ID + ":simple_miner"));
		GameRegistry.registerTileEntity(TETableSaw.class, new ResourceLocation(Main.MOD_ID + ":table_saw"));
		GameRegistry.registerTileEntity(TEPress.class, new ResourceLocation(Main.MOD_ID + ":press"));
		GameRegistry.registerTileEntity(TE_ElectricAnvil.class, new ResourceLocation(Main.MOD_ID + ":electric_anvil"));
		GameRegistry.registerTileEntity(TE_CeilingLight.class, new ResourceLocation(Main.MOD_ID + ":ceilinglight"));
		GameRegistry.registerTileEntity(TE_ConcreteMixer.class, new ResourceLocation(Main.MOD_ID + ":concrete_mixer"));
		GameRegistry.registerTileEntity(TE_Connector.class, new ResourceLocation(Main.MOD_ID + ":connector"));

	}
	
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ItemInit.items.toArray(new Item[0]));
		
	}
	
	public static void preInitRegistries(FMLPreInitializationEvent event)
	{
		PacketHandler.init();
		FluidInit.registerFluids();
		
		registerTileEntities();
		ModSoundEvent.registerSounds();
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 0);
		EntityInit.registerEnities();
		RenderHandler.registerEntityRenders();
		RenderHandler.registerCustomMeshesAndStates();
		ClientRegistry.bindTileEntitySpecialRenderer(TE_Connector.class, new ConnectorRender());
	}
	
	public static void initRegistries(FMLInitializationEvent event)
	{
		OreDictionaryCompat.registerOres();
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.MOD_ID , new GUIHandler());
		RecipesInit.init();
		
	}
	
	public static void postInitRegistries(FMLPostInitializationEvent event)
	{
	}
	
	@SubscribeEvent
	public static void onRegisterBlocksEvent(final RegistryEvent.Register<Block> event) {

		event.getRegistry().register(

			(new SnowMod() {
			})
				.setRegistryName("minecraft:snow")
				.setUnlocalizedName("snow")
		);
	}
	
	@SubscribeEvent
	public static void renderWorldLastEvent(RenderWorldLastEvent event)
	{
		
		EntityPlayer rootPlayer = Minecraft.getMinecraft().player;
		
		World world = Minecraft.getMinecraft().world;
		
		/*for(TileEntity te : world.loadedTileEntityList)
		{
			if(te instanceof TE_Connector)
			{
				if(((TE_Connector) te).isConnected())
				{
					GlStateManager.pushMatrix();
					double px = rootPlayer.lastTickPosX + (rootPlayer.posX - rootPlayer.lastTickPosX) * event.getPartialTicks();
					double py = rootPlayer.lastTickPosY + (rootPlayer.posY - rootPlayer.lastTickPosY) * event.getPartialTicks();
					double pz = rootPlayer.lastTickPosZ + (rootPlayer.posZ - rootPlayer.lastTickPosZ) * event.getPartialTicks();
					BlockPos pos = ((TE_Connector)te).getLinkTo();


					
								
					Vec3d vec1 = new Vec3d(te.getPos().getX() + 0.5D, te.getPos().getY() + 0.5D,  te.getPos().getZ() + 0.5D);
					Vec3d vec2 = new Vec3d(pos.getX() + 0.5D , pos.getY() + 0.5D, pos.getZ() + 0.5D);

					GlStateManager.translate(-px, -py, -pz);
					GL11.glRotatef(15, 0, 1, 0);
					GlStateManager.translate(vec1.x - px, 0, vec1.z -pz);
					
					GlStateManager.disableTexture2D();
					GlStateManager.glLineWidth(2.0F);

				    Tessellator tessellator = Tessellator.getInstance();
				    BufferBuilder buffer = tessellator.getBuffer();
				    buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
				    double lenght = Math.sqrt( (vec1.x - vec2.x) * (vec1.x - vec2.x) + (vec1.y - vec2.y) * (vec1.y - vec2.y)  + (vec1.z - vec2.z) * (vec1.z - vec2.z) );
				    //System.out.println(lenght);
				    for(double i = 0 ; i <= lenght + 0.1D ; i += 0.2D)
				    {
				    	double y = 0.0015D * lenght * (i) * (i - lenght);
				    	Vec3d vec3 = new Vec3d(vec1.x - i, vec1.y + y, vec1.z);
				    	buffer.pos(vec3.x , vec3.y , vec3.z).color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha()).endVertex();
				    }
				    
				    
				    tessellator.draw();
					GlStateManager.enableTexture2D();
					
					GlStateManager.popMatrix();
				}
			}
		}*/
	}
	
}
