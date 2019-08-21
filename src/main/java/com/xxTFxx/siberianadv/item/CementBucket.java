package com.xxTFxx.siberianadv.item;

import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.network.Mes;
import com.xxTFxx.siberianadv.network.PacketHandler;
import com.xxTFxx.siberianadv.render.LineRender;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CementBucket extends ItemBase{

	public CementBucket(String name) {
		super(name);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		IBlockState state = worldIn.getBlockState(pos);
		
		if(state.getBlock() == BlockInit.REBAR_FRAME)
		{
			worldIn.setBlockState(pos, BlockInit.WET_CONCRETE.getDefaultState());
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
}
