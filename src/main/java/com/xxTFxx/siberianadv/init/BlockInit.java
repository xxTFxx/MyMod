package com.xxTFxx.siberianadv.init;

import java.util.ArrayList;
import java.util.List;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.block.BasicBlock;
import com.xxTFxx.siberianadv.block.BlockFluid;
import com.xxTFxx.siberianadv.block.blocks.BarbedWire;
import com.xxTFxx.siberianadv.block.blocks.BarbedWireFence;
import com.xxTFxx.siberianadv.block.blocks.CupBlock;
import com.xxTFxx.siberianadv.block.blocks.WetConcrete;
import com.xxTFxx.siberianadv.block.cables.Connector;
import com.xxTFxx.siberianadv.block.machines.BlockEnergyStorage_ITier;
import com.xxTFxx.siberianadv.block.machines.BlockSimpleGenerator;
import com.xxTFxx.siberianadv.block.machines.CeilingLight;
import com.xxTFxx.siberianadv.block.machines.ConcreteMixer;
import com.xxTFxx.siberianadv.block.machines.ElectricAnvil;
import com.xxTFxx.siberianadv.block.machines.ElectricFurnace;
import com.xxTFxx.siberianadv.block.machines.Grinder;
import com.xxTFxx.siberianadv.block.machines.InductionFurnace;
import com.xxTFxx.siberianadv.block.machines.OilPump;
import com.xxTFxx.siberianadv.block.machines.PhotovoltaicPanel;
import com.xxTFxx.siberianadv.block.machines.PortableGenerator;
import com.xxTFxx.siberianadv.block.machines.Press;
import com.xxTFxx.siberianadv.block.machines.SimpleMiner;
import com.xxTFxx.siberianadv.block.machines.TableSaw;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class BlockInit {
	public static final List<Block> blocks = new ArrayList<Block>();
	
	public static final Block STALINIUM_BLOCK = new BasicBlock(Material.IRON , SoundType.METAL , "stalinium_block" , 5.0F);
	public static final Block WET_CONCRETE = new WetConcrete("wet_concrete");
	public static final Block CONCRETE = new BasicBlock(Material.ROCK , "concrete");
	public static final Block REBAR_FRAME = new BasicBlock(Material.IRON, "rebar_frame");
	
	public static final Block BARBEDWIRE = new BarbedWire("barbedwire");
	public static final Block BARBEDWIREFENCE = new BarbedWireFence("barbedwirefence");
	public static final Block CUP = new CupBlock(Material.WOOD, SoundType.GLASS, "cup", 0.3F);
	
	public static final Block CEILINGLIGHT = new CeilingLight("ceilinglight" , false);
	public static final Block CEILINGLIGHT_LIT = new CeilingLight("ceilinglight_lit" , true);


	
	//machines
	public static final Block SIMPLE_GENERATOR = new BlockSimpleGenerator("solid_fuel_generator");
	public static final Block ENERGYSTORAGE_ITIER = new BlockEnergyStorage_ITier("energy_storage_1");
	public static final Block PHOTOVOLTAICPANEL_BLOCK = new PhotovoltaicPanel("photovoltaic_panel");
	
	public static final Block ELECTRIC_FURNACE = new ElectricFurnace("electric_furnace");
	public static final Block ELECTRIC_FURNACE_LIT = new ElectricFurnace("electric_furnace_lit");

	public static final Block INDUCTION_FURNACE = new InductionFurnace("induction_furnace");
	//public static final Block PORTABLE_GENERATOR = new PortableGenerator("portable_generator");
	public static final Block OIL_PUMP = new OilPump("oil_pump");
	public static final Block GRINDER = new Grinder("grinder");
	public static final Block SIMPLE_MINER = new SimpleMiner("simple_miner");
	public static final Block TABLE_SAW = new TableSaw("table_saw");
	public static final Block PRESS = new Press("press");
	public static final Block ELECTRIC_ANVIL = new ElectricAnvil("electric_anvil");
	public static final Block CONCRETE_MIXER = new ConcreteMixer("concrete_mixer");
	
	public static final Block CONNECTOR = new Connector("connector");

		
	
	//fluids
	public static final Block PETROLEUM_BLOCK = new BlockFluid("petroleum", FluidInit.PETROLEUM_FLUID, Material.WATER);

}
