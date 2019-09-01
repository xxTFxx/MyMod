package com.xxTFxx.siberianadv.item;

import java.util.List;

import com.xxTFxx.siberianadv.block.cables.Connector;
import com.xxTFxx.siberianadv.init.BlockInit;
import com.xxTFxx.siberianadv.tileentity.cables.TE_Connector;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
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
				NBTTagCompound nbt;
				nbt = player.getHeldItemMainhand().getTagCompound();
				if(nbt == null && !te.hasConnectionTo())
				{
					nbt = new NBTTagCompound();
					int linkFrom[] = {pos.getX() , pos.getY() , pos.getZ()};
					nbt.setIntArray("linkFrom", linkFrom);
					player.getHeldItemMainhand().setTagCompound(nbt);
					return EnumActionResult.SUCCESS;
				}
				else if(nbt.getTag("linkFrom") != null)
				{
					if(!te.hasConnectionFrom())
					{
						int tePosArray[] = nbt.getIntArray("linkFrom");
						BlockPos tePos = new BlockPos(tePosArray[0], tePosArray[1], tePosArray[2]);
						TileEntity tileFrom = worldIn.getTileEntity(tePos);
						if(tileFrom != null && tePos != pos)
						{
							if(tileFrom instanceof TE_Connector)
							{
							    double lenght = Math.sqrt( (te.getPos().getX() - tileFrom.getPos().getX()) * (te.getPos().getX() - tileFrom.getPos().getX()) + (te.getPos().getY() - tileFrom.getPos().getY()) * (te.getPos().getY() - tileFrom.getPos().getY())  + (te.getPos().getZ() - tileFrom.getPos().getZ()) * (te.getPos().getZ() - tileFrom.getPos().getZ()) );
								TE_Connector connector = (TE_Connector) worldIn.getTileEntity(tePos);						
								float angleZ = calculateZAngle(tePos, pos);
								float angleY = calculateYAngle(tePos, pos);
								if(rayTraceBlock(tePos, pos, worldIn , connector , angleZ , angleY))
								{
									connector.setZAngle(angleZ);
									connector.setYAngle(angleY);
									connector.setLinkTo(pos.getX(), pos.getY(), pos.getZ());
									connector.changeConnectionToState(true);
									te.changeConnectionFromState(true);
									te.setLinkFrom(tePos.getX(), tePos.getY(), tePos.getZ());
									nbt.removeTag("linkFrom");
									return EnumActionResult.SUCCESS;														
								}
							}
						}
						nbt.removeTag("linkFrom");
						return EnumActionResult.SUCCESS;
					}
				}
				else if (!te.hasConnectionTo())
				{
					int linkFrom[] = {pos.getX() , pos.getY() , pos.getZ()};
					nbt.setIntArray("linkFrom", linkFrom);
					player.getHeldItemMainhand().setTagCompound(nbt);
					return EnumActionResult.SUCCESS;
				}					
		}		
		return EnumActionResult.SUCCESS;
	}
	
	private boolean rayTraceBlock(BlockPos startPos , BlockPos endPos , World world , TE_Connector te , float angleZ , float angleY)
	{
		double length = Math.sqrt( (te.getPos().getX() - endPos.getX()) * (te.getPos().getX() - endPos.getX())); 
	    if(length == 0)
	    {
	    	length = Math.sqrt( (te.getPos().getZ() - endPos.getZ()) * (te.getPos().getZ() - endPos.getZ()) );
	    }
		double a = 1 / (25 * Math.sqrt(length));
		
		for(double i = 0 ; i <= length + 0.1 ; i += 0.2D)
	    {
			double y1 = a * (i) * ( i - length);			
			double z1;
	    	if(angleZ == 90)
	    	{
	    		z1 = -i;
	    	}
	    	else if(angleZ == -90)
	    	{
	    		z1 = i;
	    	}
	    	else
	    	{
	    		z1 = i * Math.tan(Math.toRadians(angleZ));
	    	}
	    	double y2 = Math.sqrt((i*i + z1*z1)) * Math.tan(Math.toRadians(angleY));
	    	Vec3d vec3;
	    	if( te.getPos().getX() - endPos.getX() > 0)
	    	{
	    		vec3 = new Vec3d(startPos.getX() + 0.5 - i, startPos.getY() + 0.5 + y1 - y2, startPos.getZ() + 0.5 + z1);
	    	}
	    	else if(te.getPos().getX() - endPos.getX() < 0)
	    	{
	    		vec3 = new Vec3d(startPos.getX() + 0.5 + i, startPos.getY() + 0.5 + y1 + y2, startPos.getZ() + 0.5 - z1);
	    	}
	    	else
	    	{
	    		y2 = Math.sqrt((i*i)) * Math.tan(Math.toRadians(angleY));
	    		vec3 = new Vec3d(startPos.getX() + 0.5 , startPos.getY() + 0.5 + y1 - y2, startPos.getZ() + 0.5 - z1);
	    	}
	    	BlockPos rayPos = new BlockPos(vec3);
	    	if(world.getBlockState(rayPos).getBlock() != Blocks.AIR && !rayPos.equals(startPos) && !rayPos.equals(endPos))
	    	{
	    		te.changeRayTracingState(true);
	    		te.setRayTraceResult(rayPos);
	    		return false;
	    	}    	
	    }
		return true;

	}
	
	private float calculateZAngle(BlockPos startPos , BlockPos endPos)
	{
		if(startPos.getZ() != endPos.getZ())
	    {
	    	double l_x = Math.sqrt( (startPos.getX() - endPos.getX()) * (startPos.getX() - endPos.getX()) );
	    	double l_z = Math.sqrt( (startPos.getZ() - endPos.getZ()) * (startPos.getZ() - endPos.getZ()) );
	    	double atan = Math.atan( l_z / l_x );
	    	double angle = Math.toDegrees(atan);
	    	if(startPos.getZ() - endPos.getZ() > 0) angle = -angle;
	    	if(startPos.getX() - endPos.getX() < 0) angle = -angle;
	    	return (float) angle;
	    }
		else return 0;
	}
	
	private float calculateYAngle(BlockPos startPos , BlockPos endPos)
	{
		double l_x = startPos.getX() - endPos.getX();
    	double l_z = startPos.getZ() - endPos.getZ();
    	double l_y = Math.sqrt( l_x * l_x + l_z * l_z);
    	double atan = Math.atan( (startPos.getY() - endPos.getY()) / l_y );
    	double angle = Math.toDegrees(atan);

    	if(Math.abs(startPos.getX()) - Math.abs(endPos.getX()) < 0)
    	{
    		angle = -angle;
    	}
    	return (float) angle;
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
			}			
		}
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
