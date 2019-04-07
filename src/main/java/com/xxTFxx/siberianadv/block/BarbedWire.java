package com.xxTFxx.siberianadv.block;

import java.util.Random;

import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.init.ItemInit;
import com.xxTFxx.siberianadv.util.handlers.DamageSourceHandler;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BarbedWire extends BasicBlock{
	
	public static final AxisAlignedBB BarbedWireAABBn = new AxisAlignedBB(0D, 0D, 0.1875D, 1D , 0.5635D, 0.75D);
	public static final AxisAlignedBB BarbedWireAABBe = new AxisAlignedBB(0.25D, 0D, 0D, 0.8125D , 0.5635D, 1D);
	public static final AxisAlignedBB BarbedWireAABBs = new AxisAlignedBB(0D, 0D, 0.25D, 1D , 0.5635D, 0.8125D);
	public static final AxisAlignedBB BarbedWireAABBw = new AxisAlignedBB(0.1875D, 0D, 0D, 0.75D , 0.5635D, 1D);
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	
	public BarbedWire(String name) {
		super(Material.ROCK, SoundType.METAL, name);
		setHardness(20.0F);
		BlockInit.blocks.add(this);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
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
		EnumFacing facing = (EnumFacing)state.getValue(FACING);
		if(facing.equals(EnumFacing.WEST) )
		{
			return BarbedWireAABBw;
		}
		else if(facing.equals(EnumFacing.EAST))
		{
			return BarbedWireAABBe;
		}
		else if(facing.equals(EnumFacing.NORTH))
		{
			return BarbedWireAABBn;
		}
		else if(facing.equals(EnumFacing.SOUTH))
		{
			return BarbedWireAABBs;
		}
		return null;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	  {
	    EnumFacing facing = EnumFacing.getHorizontal(meta);
	    return this.getDefaultState().withProperty(FACING, facing);
	  }
	
	@Override
	  public int getMetaFromState(IBlockState state)
	  {
	    EnumFacing facing = (EnumFacing)state.getValue(FACING);
	    int facingbits = facing.getHorizontalIndex();
	    return facingbits;
	  }
	
	@Override
	  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing blockFaceClickedOn, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	  {
	    EnumFacing enumfacing = (placer == null) ? EnumFacing.NORTH : EnumFacing.fromAngle(placer.rotationYaw);

	    return this.getDefaultState().withProperty(FACING, enumfacing);
	  }
	
	@Override
	  protected BlockStateContainer createBlockState()
	  {
	    return new BlockStateContainer(this, new IProperty[] {FACING});
	  }
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.setInWeb();
		entityIn.attackEntityFrom(DamageSourceHandler.BARBED_WIRE, 2);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
		EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		

		if(playerIn.getHeldItemMainhand().getItem() == ItemInit.STALINIUM_INGOT) {
			playerIn.addItemStackToInventory(new ItemStack(BlockInit.BARBEDWIRE));
			worldIn.setBlockToAir(pos);
			return true;
		}
		
		return false;
	}
	
	
}
