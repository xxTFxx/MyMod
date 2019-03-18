package com.xxTFxx.siberianadv.block.machines;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.block.RotBlock;
import com.xxTFxx.siberianadv.init.ModBlocks;
import com.xxTFxx.siberianadv.tabs.ModTab;
import com.xxTFxx.siberianadv.tileentity.TileEntityElectricFurnace_ITier;
import com.xxTFxx.siberianadv.tileentity.TileEntitySimpleGenerator;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElectricFurnace_ITier extends RotBlock{
	
	public static final PropertyBool BURNING = PropertyBool.create("burning");

	public ElectricFurnace_ITier(String name) {
		super(Material.IRON, SoundType.METAL, name, 1.0F);
	//	this.setDefaultState(this.blockState.getBaseState().withProperty(BURNING, false));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(!worldIn.isRemote) {
			playerIn.openGui(Main.MOD_ID, Main.GUI_ELECTRICFURNACE_1, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;	
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityElectricFurnace_ITier();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityElectricFurnace_ITier tileentity = (TileEntityElectricFurnace_ITier)worldIn.getTileEntity(pos);
		worldIn.spawnEntity(new EntityItem(worldIn , pos.getX() , pos.getY() , pos.getZ() , tileentity.handler.getStackInSlot(0)));
		super.breakBlock(worldIn, pos, state); 
	}
	
	public static void setState(boolean isActive , World worldIn , BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tile = worldIn.getTileEntity(pos);
		
		if(isActive)
		{
			worldIn.setBlockState(pos, ModBlocks.ELECTRIC_FURNACE_ITIER.getDefaultState().withProperty(BURNING, true) , 3);
		}
		else
		{
			worldIn.setBlockState(pos, ModBlocks.ELECTRIC_FURNACE_ITIER.getDefaultState().withProperty(BURNING, false) , 3);
		}
		
		if(tile != null)
		{
			tile.validate();
			worldIn.setTileEntity(pos, tile);
		}
	}
	
	@Override
	  protected BlockStateContainer createBlockState()
	  {
	    return new BlockStateContainer(this, new IProperty[] {FACING});
	  }
}
