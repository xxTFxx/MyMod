package com.xxTFxx.siberianadv.block.machines;

import com.xxTFxx.siberianadv.block.BasicBlock;
import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.tileentity.TE_CeilingLight;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CeilingLight extends BasicBlock{
	
	public static final AxisAlignedBB AABB = new AxisAlignedBB(0D, 0.8125D, 0D, 1D , 1D, 1D);
    private final boolean isOn;

	public CeilingLight(String name , boolean isOn) {
		super(Material.GLASS, name);
		
		this.isOn = isOn;

        if (isOn)
        {
            this.setLightLevel(1.0F);
        }
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		 if (!worldIn.isRemote)
	        {
	            TE_CeilingLight tile = (TE_CeilingLight)worldIn.getTileEntity(pos);
	            tile.changeMode();
	        }
		return true;
		
	}
	
	public static void setState(boolean active, World worldIn, BlockPos pos)
    {
		TileEntity tile = worldIn.getTileEntity(pos);
		
		if(active)
		{
			worldIn.setBlockState(pos, BlockInit.CEILINGLIGHT_LIT.getDefaultState() ,3);
		}
		else
		{
			worldIn.setBlockState(pos, BlockInit.CEILINGLIGHT.getDefaultState() , 3);
		}
		
		if (tile != null)
        {
			tile.validate();
            worldIn.setTileEntity(pos, tile);
        }
    }
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TE_CeilingLight();
	}
	
}
