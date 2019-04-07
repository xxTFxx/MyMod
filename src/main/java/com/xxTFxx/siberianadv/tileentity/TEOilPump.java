package com.xxTFxx.siberianadv.tileentity;

import com.xxTFxx.siberianadv.init.FluidInit;
import com.xxTFxx.siberianadv.block.BlockFluid;
import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;
import com.xxTFxx.siberianadv.init.BlockInit;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TEOilPump extends TileEntity implements ITickable{

	private CustomEnergyStorage storage = new CustomEnergyStorage(10000 , 500 , 0);
	private FluidTank tank = new FluidTank(FluidInit.PETROLEUM_FLUID , 0 , 16000);
	private int time = 200;
	private int currentTime = 0;
	private boolean working = false;
	private IBlockState state;
	private BlockPos oilPos;
	private int oilX = 5 , oilY = -1 , oilZ = 5;
	IFluidHandler handler;
	
	@Override
	public void update() {

		if(tank.getFluidAmount() + 1600 <= tank.getCapacity())
		{
			if(!working)
			{
				oilPos = new BlockPos(pos.getX() + oilX , pos.getY() + oilY , pos.getZ() + oilZ);
				state = world.getBlockState(oilPos);
				
				if(state.getBlock() == BlockInit.PETROLEUM_BLOCK && state.getBlock().getMetaFromState(state) == 0)
				{
					working = true;
					handler = FluidUtil.getFluidHandler(world, oilPos, null);
				}
				else
				{
					oilX--;
					if(oilX < -5)
					{
						oilX = 5;
						oilZ--;
						if(oilZ < -5)
						{
							oilZ = 5;
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

		
		if(working)
		{
			currentTime = currentTime + 1;
			tank.fill(new FluidStack(FluidInit.PETROLEUM_FLUID, 8), true);
		}
		
		if(currentTime == time)
		{		
			handler.drain(Fluid.BUCKET_VOLUME, true);
			currentTime = 0;
			working = false;
		}
					
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
		if(capability == CapabilityEnergy.ENERGY)
		{
			return (T)this.storage;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return true;
		}
		if(capability == CapabilityEnergy.ENERGY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		this.storage.writeToNBT(compound);
		this.tank.writeToNBT(compound);
		compound.setInteger("pumpX", this.oilX);
		compound.setInteger("pumpY", this.oilY);
		compound.setInteger("pumpZ", this.oilZ);
		compound.setInteger("CurrentTime", this.currentTime);
		super.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.storage.readFromNBT(compound);
		this.tank.readFromNBT(compound);
		this.oilX = compound.getInteger("pumpX");
		this.oilY = compound.getInteger("pumpY");
		this.oilZ = compound.getInteger("pumpZ");
		this.currentTime = compound.getInteger("CurrentTime");
	}
	
}
