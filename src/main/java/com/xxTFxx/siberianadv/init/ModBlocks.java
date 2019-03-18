package com.xxTFxx.siberianadv.init;

import java.util.ArrayList;
import java.util.List;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.block.BasicBlock;
import com.xxTFxx.siberianadv.block.BlockFluid;
import com.xxTFxx.siberianadv.block.BarbedWire;
import com.xxTFxx.siberianadv.block.BarbedWireFence;
import com.xxTFxx.siberianadv.block.CupBlock;
import com.xxTFxx.siberianadv.block.machines.BlockEnergyStorage_ITier;
import com.xxTFxx.siberianadv.block.machines.BlockSimpleGenerator;
import com.xxTFxx.siberianadv.block.machines.ElectricFurnace_ITier;
import com.xxTFxx.siberianadv.block.machines.InductionFurnace;
import com.xxTFxx.siberianadv.block.machines.PhotovoltaicPanel;
import com.xxTFxx.siberianadv.block.machines.PortableGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

//@ObjectHolder(Main.MOD_ID)
public class ModBlocks {
	public static final List<Block> blocks = new ArrayList<Block>();
	
	public static final Block STALINIUM_BLOCK = new BasicBlock(Material.IRON , SoundType.METAL , "stalinium_block" , 5.0F);
	
	public static final Block BARBEDWIRE = new BarbedWire("barbedwire");
	public static final Block BARBEDWIREFENCE = new BarbedWireFence("barbedwirefence");
	public static final Block CUP = new CupBlock(Material.WOOD, SoundType.GLASS, "cup", 0.3F);
	
	//machines
	public static final Block SIMPLE_GENERATOR = new BlockSimpleGenerator("simple_generator");
	public static final Block ENERGYSTORAGE_ITIER = new BlockEnergyStorage_ITier("energy_storage_1");
	public static final Block PHOTOVOLTAICPANEL_BLOCK = new PhotovoltaicPanel("photovoltaic_panel");
	public static final Block ELECTRIC_FURNACE_ITIER = new ElectricFurnace_ITier("electric_furnace_1");
	public static final Block INDUCTION_FURNACE = new InductionFurnace("induction_furnace");
	public static final Block PORTABLE_GENERATOR = new PortableGenerator("portable_generator");
	
	//fluids
	public static final Block PETROLEUM_BLOCK = new BlockFluid("petroleum", FluidInit.PETROLEUM_FLUID, Material.WATER);

}
