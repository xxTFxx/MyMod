package com.xxTFxx.siberianadv.init;



import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.item.ArmourModel;
import com.xxTFxx.siberianadv.item.CoffeCup;
import com.xxTFxx.siberianadv.item.CustomFood;
import com.xxTFxx.siberianadv.item.ItemBase;
import com.xxTFxx.siberianadv.item.ItemModRecord;
import com.xxTFxx.siberianadv.item.PortableGeneratorItem;
import com.xxTFxx.siberianadv.materials.ModMaterials;
import com.xxTFxx.siberianadv.models.ModelUshanka;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Main.MOD_ID)
public class ItemInit {
	//ModelUshanka ushanka = new ModelUshanka();

	public static final Item STALINIUM_INGOT = new ItemBase("stalinium_ingot");
	
	public static final ItemSword STALINIUM_SWORD = null;
	
	public static final ItemModRecord SOVIET_ANTHEM = null;
	
	public static final CoffeCup COFFECUP = null;
	
	public static final ItemBase BEAR_FUR = null;
	
	public static final CustomFood BEAR_MEAT_RAW = new CustomFood("bear_meat_raw", 2, true);
	
	public static final CustomFood BEAR_MEAT_COOKED = new CustomFood("bear_meat_cooked", 8, true);
	
	public static final ArmourModel USHANKA = new ArmourModel("ushanka", ModMaterials.BEAR_MATERIAL, EntityEquipmentSlot.HEAD , new ModelUshanka());
	
	public static final Item IRON_DUST = new ItemBase("iron_dust");
	
	//public static final PortableGeneratorItem PORTABLE_GENERATOR_ITEM = new PortableGeneratorItem("portable_generator" , ModBlocks.PORTABLE_GENERATOR);
}
