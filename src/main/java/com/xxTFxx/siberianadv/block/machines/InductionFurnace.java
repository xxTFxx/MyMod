package com.xxTFxx.siberianadv.block.machines;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.block.RotBlock;
import com.xxTFxx.siberianadv.tileentity.TileEntityElectricFurnace_ITier;
import com.xxTFxx.siberianadv.tileentity.TileEntityInductionFurnace;
import com.xxTFxx.siberianadv.tileentity.TileEntitySimpleGenerator;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InductionFurnace extends RotBlock{
	
	public static final PropertyBool BURNING = PropertyBool.create("burning");
	
	public InductionFurnace(String name) {
		super(Material.IRON, SoundType.METAL, name , 1.0F);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(!worldIn.isRemote) {
			playerIn.openGui(Main.MOD_ID, Main.GUI_INDUCTIONFURNACE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;	
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityInductionFurnace();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityInductionFurnace tileentity = (TileEntityInductionFurnace)worldIn.getTileEntity(pos);
		worldIn.spawnEntity(new EntityItem(worldIn , pos.getX() , pos.getY() , pos.getZ() , tileentity.handler.getStackInSlot(0)));
		worldIn.spawnEntity(new EntityItem(worldIn , pos.getX() , pos.getY() , pos.getZ() , tileentity.handler.getStackInSlot(1)));
		worldIn.spawnEntity(new EntityItem(worldIn , pos.getX() , pos.getY() , pos.getZ() , tileentity.handler.getStackInSlot(2)));
		worldIn.spawnEntity(new EntityItem(worldIn , pos.getX() , pos.getY() , pos.getZ() , tileentity.handler.getStackInSlot(3)));
		worldIn.spawnEntity(new EntityItem(worldIn , pos.getX() , pos.getY() , pos.getZ() , tileentity.handler.getStackInSlot(4)));
		worldIn.spawnEntity(new EntityItem(worldIn , pos.getX() , pos.getY() , pos.getZ() , tileentity.handler.getStackInSlot(5)));
		super.breakBlock(worldIn, pos, state); 
	}
	

}
