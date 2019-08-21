package com.xxTFxx.siberianadv.block.machines;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.block.RotBlock;
import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.tabs.ModTab;
import com.xxTFxx.siberianadv.tileentity.TE_ElectricFurnace;
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

public class ElectricFurnace extends RotBlock{
	
	public static final PropertyBool BURNING = PropertyBool.create("burning");
    private static boolean keepInventory;

	public ElectricFurnace(String name) {
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
		return new TE_ElectricFurnace();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		
		if(!keepInventory)
		{
			TE_ElectricFurnace tileentity = (TE_ElectricFurnace)worldIn.getTileEntity(pos);
			worldIn.spawnEntity(new EntityItem(worldIn , pos.getX() , pos.getY() , pos.getZ() , tileentity.handler.getStackInSlot(0)));
			worldIn.spawnEntity(new EntityItem(worldIn , pos.getX() , pos.getY() , pos.getZ() , tileentity.handler.getStackInSlot(1)));			
		}
		super.breakBlock(worldIn, pos, state); 
	}
	
	public static void setState(boolean isActive , World worldIn , BlockPos pos)
	{
        IBlockState iblockstate = worldIn.getBlockState(pos);
		TileEntity tile = worldIn.getTileEntity(pos);
		
		keepInventory = true;
		
		if(isActive)
		{
			worldIn.setBlockState(pos, BlockInit.ELECTRIC_FURNACE_LIT.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)) , 3);
		}
		else
		{
			worldIn.setBlockState(pos, BlockInit.ELECTRIC_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)) , 3);
		}
		
		keepInventory = false;
		
		if(tile != null)
		{
			tile.validate();
			worldIn.setTileEntity(pos, tile);
		}
	}
	

}
