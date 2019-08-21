package com.xxTFxx.siberianadv.tileentity;

import javax.annotation.Nullable;

import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TE_ElectricAnvil extends TileEntity implements ITickable{
	
	public ItemStackHandler handler = new ItemStackHandler(3);
	private CustomEnergyStorage storage = new CustomEnergyStorage(10000 , 1000 , 0);
	private boolean shouldUpdate = false;
	private int energy;
	private int ENERGY_DRAIN = 40;
	
	@Override
	public void update() {
		
		if(energy != storage.getEnergyStored())
		{
			shouldUpdate = true;
			energy = storage.getEnergyStored();
		}
		
		if(!handler.getStackInSlot(0).isEmpty() && handler.getStackInSlot(0).getItem().isDamageable() && storage.getEnergyStored() >= ENERGY_DRAIN)
		{
			int dmg = handler.getStackInSlot(0).getItemDamage();
			if(dmg > 0)
			{
				shouldUpdate = true;
				if(dmg - 1 < 0)
				{
					handler.getStackInSlot(0).setItemDamage(0);
				}
				else
				{
					handler.getStackInSlot(0).setItemDamage(dmg - 1);
				}
				storage.consumeEnergy(ENERGY_DRAIN);
			}
		}
		
		if(shouldUpdate)
		{
			sendUpdates();
			shouldUpdate = false;
		}
		
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		if(capability.equals(CapabilityEnergy.ENERGY))
		{
			return true;
		}
		if(capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability.equals(CapabilityEnergy.ENERGY))
		{
			return (T)this.storage;
		}
		if(capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY))
		{
			return (T)this.handler;
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		this.storage.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.storage.readFromNBT(compound);
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
	
	public int getEnergyStored()
	{
		return this.storage.getEnergyStored();
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
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
