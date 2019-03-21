package com.xxTFxx.siberianadv.tabs;

import com.xxTFxx.siberianadv.init.FluidInit;
import com.xxTFxx.siberianadv.block.BlockFluid;
import com.xxTFxx.siberianadv.init.BlockInit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TEOilPump extends TileEntity implements ITickable{

	private FluidTank tank = new FluidTank(FluidInit.PETROLEUM_FLUID , 0 , 10000);
	private int time = 200;
	private int currentTime = 0;
	private boolean working = false;
	private IBlockState state;
	private BlockPos oilPos;
	private int oilX = 10 , oilY = -1 , oilZ = 10;
	IFluidHandler handler;
	
	@Override
	public void update() {

		if(tank.getFluidAmount() + 2000 <= tank.getCapacity())
		{
			if(!working)
			{
				/*for(int y = -1 ; y >= -10 ; y--  )
				{
					for(int z = 10 ; z >= -10 ; z--)
					{
						for(int x = 10 ; x >= -10 ; x--)
						{
							oilPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
							state = world.getBlockState(oilPos);
							//System.out.println(oilPos.getX() + "  " + oilPos.getY() + "   " + oilPos.getZ());
							if(state.getBlock() != null && state.getBlock() == BlockInit.PETROLEUM_BLOCK)
							{
								if(!working)
								{
									
									working = true;
									oilX = x;
									oilY = y;
									oilZ = z;
								}
								
								break;
							}
						}
					}
				}*/
				oilPos = new BlockPos(pos.getX() + oilX , pos.getY() + oilY , pos.getZ() + oilZ);
				state = world.getBlockState(oilPos);
				
				if(state.getBlock() == BlockInit.PETROLEUM_BLOCK && state.getBlock().getMetaFromState(state) == 0)
				{
					//if(state.getBlock().getMetaFromState(state) == 0)
					{
						working = true;
						handler = FluidUtil.getFluidHandler(world, oilPos, null);
					}
				}

				else
				{
					oilX--;
					if(oilX < -10)
					{
						oilX = 10;
						oilZ--;
						if(oilZ < -10)
						{
							oilZ = 10;
							oilY--;
							if(oilY < -10)
							{
								oilY = -1;
							}
						}
					}
				}
				
			}			
		}
		else if(working)
		{
			working = false;
			currentTime = 0;
		}
		
		if(working)
		{
			currentTime = currentTime + 1;
			tank.fill(new FluidStack(FluidInit.PETROLEUM_FLUID, 10), true);
		}
			
		if(currentTime == time)
		{
			//handler = FluidUtil.getFluidHandler(world, new BlockPos(pos.getX() + oilX , pos.getY() + oilY , pos.getZ() + oilZ), null);
			
			handler.drain(Fluid.BUCKET_VOLUME, true);
			currentTime = 0;
			working = false;
			//world.setBlockToAir(new BlockPos(pos.getX() + oilX , pos.getY() + oilY , pos.getZ() + oilZ));
		}
			
		//System.out.println(currentTime);
		
	}
	
	public int getFluidAmount()
	{
		return this.tank.getFluidAmount();
	}
	
	public int getMaxFluidAmount()
	{
		return this.tank.getCapacity();
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return (T)this.tank;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
}
