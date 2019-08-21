package com.xxTFxx.siberianadv.item;

import java.util.List;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.init.FluidInit;
import com.xxTFxx.siberianadv.init.ItemInit;
import com.xxTFxx.siberianadv.tabs.ModTab;
import com.xxTFxx.siberianadv.tileentity.TileEntityPortableGenerator;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class PortableGeneratorItem extends ItemBlock{

	public PortableGeneratorItem(String name , Block block) {
		super(block);
		
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setRegistryName(name);
		//setCreativeTab(ModTab.Mod_Tab);
		
		setMaxStackSize(1);
		ItemInit.items.add(this);	
	}
	

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
		NBTTagCompound nbt = stack.getTagCompound();
		
		if(nbt != null)
		{
			String infoEnergy;
			String infoFuel;
			int energy = nbt.getInteger("Energy");
			int fuel = nbt.getInteger("Fuel");
			infoEnergy = "Energy: §3" + Integer.toString(energy);
			infoFuel = "Fuel: §3" + Integer.toString(fuel);
			tooltip.add(infoEnergy);
			tooltip.add(infoFuel);
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
}
