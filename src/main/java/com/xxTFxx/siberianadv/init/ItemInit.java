package com.xxTFxx.siberianadv.init;



import java.util.ArrayList;
import java.util.List;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.block.machines.PortableGenerator;
import com.xxTFxx.siberianadv.item.ArmourModel;
import com.xxTFxx.siberianadv.item.CementBucket;
import com.xxTFxx.siberianadv.item.CoffeCup;
import com.xxTFxx.siberianadv.item.CustomFood;
import com.xxTFxx.siberianadv.item.Debugger;
import com.xxTFxx.siberianadv.item.ItemBase;
import com.xxTFxx.siberianadv.item.ItemFuel;
import com.xxTFxx.siberianadv.item.ItemModRecord;
import com.xxTFxx.siberianadv.item.ItemModSword;
import com.xxTFxx.siberianadv.item.PortableGeneratorItem;
import com.xxTFxx.siberianadv.item.WireTool;
import com.xxTFxx.siberianadv.materials.ModMaterials;
import com.xxTFxx.siberianadv.models.ModelUshanka;
import com.xxTFxx.siberianadv.util.ModSoundEvent;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class ItemInit {
	public static final List<Item> items = new ArrayList<Item>();
	//ModelUshanka ushanka = new ModelUshanka();

	public static final Item STALINIUM_INGOT = new ItemBase("stalinium_ingot");
	
	public static final Item STALINIUM_SWORD = new ItemModSword(ModMaterials.MOD_TOOL , "stalinium_sword");
	
	public static final Item SOVIET_ANTHEM = new ItemModRecord("soviet_anthem" , ModSoundEvent.SOVIET_ANTHEM);
	
	public static final Item COFFECUP = new CoffeCup("coffecup");
	
	public static final Item BEAR_FUR = new ItemBase("bear_fur");
	
	public static final CustomFood BEAR_MEAT_RAW = new CustomFood("bear_meat_raw", 2, true);
	
	public static final CustomFood BEAR_MEAT_COOKED = new CustomFood("bear_meat_cooked", 8, true);
	
	public static final ArmourModel USHANKA = new ArmourModel("ushanka", ModMaterials.BEAR_MATERIAL, EntityEquipmentSlot.HEAD , new ModelUshanka());
	
	public static final Item IRON_DUST = new ItemBase("iron_dust");
	
	public static final Item SAWDUST = new ItemFuel("sawdust" , 20);
	
	public static final Item PELLET = new ItemFuel("pellet" , 1800);
	
	public static final Item PORTABLE_GENERATOR = new PortableGeneratorItem("portable_generator" , new PortableGenerator("portable_generator"));
	
	public static final Item CEMENT_BUCKET = new CementBucket("cement_bucket");
	
	public static final Item WIRETOOL = new WireTool("wiretool");
	
	public static final Item DEBUGGER = new Debugger("debugger");
	//public static final PortableGeneratorItem PORTABLE_GENERATOR_ITEM = new PortableGeneratorItem("portable_generator" , ModBlocks.PORTABLE_GENERATOR);
}
