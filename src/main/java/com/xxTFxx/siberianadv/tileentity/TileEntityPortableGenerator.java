package com.xxTFxx.siberianadv.tileentity;

import javax.annotation.Nullable;

import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;
import com.xxTFxx.siberianadv.gui.GUIPortableGenerator;
import com.xxTFxx.siberianadv.init.FluidInit;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityPortableGenerator extends TileEntity implements ITickable
{
	
	public ItemStackHandler handler = new ItemStackHandler(2)
	{
		protected void onContentsChanged(int slot) {
			TileEntityPortableGenerator.this.markDirty();
		};
	};
	
	private CustomEnergyStorage storage = new CustomEnergyStorage(40000);
	private FluidTank tank = new FluidTank(FluidInit.PETROLEUM_FLUID, 0, 5000);
	private int FLUID_DRAIN = 5;
	private int ENERGY_GAIN = 100;
	private boolean isTurned = false;
	private boolean shouldUpdate = false;
	
	@Override
	public void update() {
		
		
		if(!handler.getStackInSlot(0).isEmpty())
		{
			if(handler.getStackInSlot(1).isEmpty() || handler.getStackInSlot(1).getItem() == Items.BUCKET)
			{
				if(handler.getStackInSlot(0).getItem() == FluidUtil.getFilledBucket(new FluidStack(FluidInit.PETROLEUM_FLUID, Fluid.BUCKET_VOLUME)).getItem() && canFillTank(1000))
				{
					tank.fill(new FluidStack(FluidInit.PETROLEUM_FLUID, 1000), true);
					handler.getStackInSlot(0).shrink(1);
					if(handler.getStackInSlot(1).isEmpty())
					{
						handler.insertItem(1, new ItemStack(Items.BUCKET), false);
					}
					else
					{
						handler.getStackInSlot(1).grow(1);
					}
				}				
			}
		}

		if(isTurned)
		{
			if(tank.getFluidAmount() >= FLUID_DRAIN && storage.getEnergyStored() + ENERGY_GAIN <= storage.getMaxEnergyStored())
			{
				shouldUpdate = true;
				tank.drain(FLUID_DRAIN, true);
				storage.addEnergy(ENERGY_GAIN);
			}
			else
			{
				isTurned = !isTurned;
			}
		}
		if(shouldUpdate)
		{
			sendUpdates();
			shouldUpdate = false;
		}
	}
	
	
	public boolean isWorking()
	{
		return this.isTurned;
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	public int getEnergyStored()
	{
		return this.storage.getEnergyStored();
	}
	
	public int getFluidAmount()
	{
		return this.tank.getFluidAmount();
	}
	
	public int getMaxFluidAmount()
	{
		return this.tank.getCapacity();
	}
	
	public void setEnergy(int energy)
	{
		this.storage.setEnergy(energy);
	}
	
	public boolean canFillTank(int fluid) {
		if(this.tank.getFluidAmount() + fluid <= this.tank.getCapacity())
		{
			return true;
		}
		return false;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY)
		{
			return (T)this.storage;
		}
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return (T)this.tank;
		}
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T)this.handler;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		if(capability == CapabilityEnergy.ENERGY)
		{
			return true;
		}
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return true;
		}
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setBoolean("Working", this.isTurned);
		this.storage.writeToNBT(compound);
		this.tank.writeToNBT(compound);
		super.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.isTurned = compound.getBoolean("Working");
		this.storage.readFromNBT(compound);
		this.tank.readFromNBT(compound);
	}
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}

	public void onGuiButtonPress(int id) {
		this.isTurned = !this.isTurned;
	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}
	
	private void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
		markDirty();
	}
	


}