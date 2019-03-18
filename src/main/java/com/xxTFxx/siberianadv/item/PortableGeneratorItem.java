package com.xxTFxx.siberianadv.item;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.tabs.ModTab;
import com.xxTFxx.siberianadv.tileentity.TileEntityPortableGenerator;

import net.minecraft.block.Block;
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

public class PortableGeneratorItem extends ItemBlock{

	public PortableGeneratorItem(String name , Block block) {
		super(block);
		
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setRegistryName(name);
		setCreativeTab(ModTab.Mod_Tab);
		
		setMaxStackSize(1);
	}
	
	/*@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		ItemStack itemStack = player.getHeldItem(hand);
		
		//worldIn.setTileEntity(pos, new TileEntityPortableGenerator());
		TileEntityPortableGenerator tileEntityPortableGenerator = (TileEntityPortableGenerator)worldIn.getTileEntity(new BlockPos(pos.getX(), pos.getY() + 1 , pos.getZ()));
		if(itemStack.getSubCompound("Energy") != null)
		{
			System.out.println(itemStack.getSubCompound("Energy"));
			System.out.println(tileEntityPortableGenerator);

			tileEntityPortableGenerator.readFromNBT(itemStack.getSubCompound("Energy"));			
		}
			
		
		return EnumActionResult.SUCCESS;
	}*/
	
}
