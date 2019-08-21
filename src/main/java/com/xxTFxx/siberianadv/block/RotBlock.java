package com.xxTFxx.siberianadv.block;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.init.ItemInit;
import com.xxTFxx.siberianadv.tabs.ModTab;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RotBlock extends Block{

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public RotBlock(Material materialIn , SoundType sound , String name , float hardness) {
		super(materialIn);
		setSoundType(sound);
		setHardness(hardness);
		setRegistryName(name);
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setCreativeTab(ModTab.Mod_Tab);
		BlockInit.blocks.add(this);
		ItemInit.items.add(new ItemBlock(this).setRegistryName(name));
	}
	
	public RotBlock(Material materialIn, String name , boolean itemBlock) {
		super(materialIn);
		setSoundType(SoundType.STONE);
		setHardness(1.0F);
		setRegistryName(name);
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setCreativeTab(ModTab.Mod_Tab);
		BlockInit.blocks.add(this);
		if(itemBlock)
		{
			ItemInit.items.add(new ItemBlock(this).setRegistryName(name));			
		}
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

}
