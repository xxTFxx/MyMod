package com.xxTFxx.siberianadv.block.machines;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.block.RotBlock;
import com.xxTFxx.siberianadv.tileentity.TEGrinder;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

public class Grinder extends RotBlock{
	
	public Grinder(String name) {
		super(Material.IRON, name);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TEGrinder();
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		TEGrinder tile = (TEGrinder)worldIn.getTileEntity(pos);
		
		if(!worldIn.isRemote) {
			playerIn.openGui(Main.MOD_ID, Main.GUI_GRINDER, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TEGrinder tileentity = (TEGrinder)worldIn.getTileEntity(pos);
		worldIn.spawnEntity(new EntityItem(worldIn , pos.getX() , pos.getY() , pos.getZ() , tileentity.handler.getStackInSlot(0)));
		worldIn.spawnEntity(new EntityItem(worldIn , pos.getX() , pos.getY() , pos.getZ() , tileentity.handler.getStackInSlot(1)));
		super.breakBlock(worldIn, pos, state);
	}

}
