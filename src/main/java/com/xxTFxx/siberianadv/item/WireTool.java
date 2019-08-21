package com.xxTFxx.siberianadv.item;

import java.util.List;

import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.tileentity.cables.TE_Connector;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WireTool extends ItemBase{
	
	public WireTool(String name) {
		super(name);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof TE_Connector)
		{
			TE_Connector te = (TE_Connector) tile;
			
				System.out.println("asd");
				NBTTagCompound nbt;
				nbt = player.getHeldItemMainhand().getTagCompound();
				if(nbt == null && !te.isConnected())
				{
					nbt = new NBTTagCompound();
					int linkFrom[] = {pos.getX() , pos.getY() , pos.getZ()};
					nbt.setIntArray("linkFrom", linkFrom);
					player.getHeldItemMainhand().setTagCompound(nbt);
					return EnumActionResult.SUCCESS;
				}
				else if(nbt.getTag("linkFrom") != null)
				{
					int tePosArray[] = nbt.getIntArray("linkFrom");
					BlockPos tePos = new BlockPos(tePosArray[0], tePosArray[1], tePosArray[2]);
					TE_Connector connector = (TE_Connector) worldIn.getTileEntity(tePos);
					connector.changeConnectionState();
					connector.setLinkTo(pos.getX(), pos.getY(), pos.getZ());
					player.getHeldItemMainhand().setTagCompound(new NBTTagCompound());
					return EnumActionResult.SUCCESS;
				}
				else if (!te.isConnected())
				{
					int linkFrom[] = {pos.getX() , pos.getY() , pos.getZ()};
					nbt.setIntArray("linkFrom", linkFrom);
					player.getHeldItemMainhand().setTagCompound(nbt);
					return EnumActionResult.SUCCESS;
				}					
		}		
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
		NBTTagCompound nbt = stack.getTagCompound();
		
		if(nbt != null)
		{
			if(nbt.getTag("linkFrom") != null)
			{
				String info1;
				int linkFrom[] = nbt.getIntArray("linkFrom");
				info1 = Integer.toString(linkFrom[0]) + " " + Integer.toString(linkFrom[1]) + " " + Integer.toString(linkFrom[2]);
				tooltip.add(info1);
				/*if(nbt.getTag("linkTo") != null)
			{
				String info2;
				int linkTo[] = nbt.getIntArray("linkTo");
				info2 = Integer.toString(linkTo[0]) + " " + Integer.toString(linkTo[1]) + " " + Integer.toString(linkTo[2]);
				tooltip.add(info2);
			}*/
			}			
		}
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
