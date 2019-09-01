package com.xxTFxx.siberianadv.tileentity.cables;


import javax.annotation.Nullable;

import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TE_Connector extends TileEntity implements ITickable{
	
	private boolean connectionTo = false;
	private boolean connectionFrom = false;
	private boolean rayTraceRender = false;
	private BlockPos linkTo;
	private BlockPos linkFrom;
	private BlockPos rayTrace;
	private CustomEnergyStorage storage = new CustomEnergyStorage(100, 100);
	private int output = 100;
	private float angleZ = 0;
	private float angleY = 0;
	
	@Override
	public void update() {
		
		if(storage.getEnergyStored() >= output)
		{
			if(connectionTo)
			{
				TileEntity tile = world.getTileEntity(linkTo);
				if(tile instanceof TE_Connector)
				{
					if(tile.hasCapability(CapabilityEnergy.ENERGY, null))
					{
						IEnergyStorage handler = tile.getCapability(CapabilityEnergy.ENERGY, null);
						if(handler != null && handler.getEnergyStored() + output <= handler.getMaxEnergyStored())
						{
							handler.receiveEnergy(output, false);
							storage.consumeEnergy(output);
						}
					}
				}
			}		
		}
		
		sendEnergy();
		
	}
	
	public boolean hasConnectionTo()
	{		
		return this.connectionTo;
	}
	
	public boolean hasConnectionFrom()
	{
		return this.connectionFrom;
	}
	
	public boolean isAnyBlockRayTraced()
	{
		return this.rayTraceRender;
	}
	
	public void changeConnectionToState(boolean state)
	{
		this.connectionTo = state;
	}
	
	public void changeConnectionFromState(boolean state)
	{
		this.connectionFrom = state;
	}
	
	public void changeRayTracingState(boolean state)
	{
		this.rayTraceRender = state;
	}
	
	public void setLinkTo(int x , int y , int z)
	{
		this.linkTo = new BlockPos(x, y, z);
	}
	
	public void setLinkFrom(int x , int y , int z)
	{
		this.linkFrom = new BlockPos(x, y, z);
	}
	
	public void setRayTraceResult(BlockPos pos)
	{
		this.rayTrace = pos;
	}
	
	public BlockPos getLinkTo()
	{
		return this.linkTo;
	}
	
	public BlockPos getLinkFrom()
	{
		return this.linkFrom;
	}
	
	public BlockPos rayTraceResult()
	{
		return this.rayTrace;
	}

	public void setZAngle(float angle)
	{
		this.angleZ = angle;
	}
	
	public void setYAngle(float angle)
	{
		this.angleY = angle;
	}
	
	public float getZAngle()
	{
		return this.angleZ;
	}
	
	public float getYAngle()
	{
		return this.angleY;
	}
	
	private void sendEnergy() {
		if(storage.getEnergyStored() >= output )
		{
			for(EnumFacing facing : EnumFacing.VALUES)
			{
				TileEntity tile = world.getTileEntity(pos.offset(facing));
				if(tile != null && !(tile instanceof TE_Connector) && tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))
				{
					IEnergyStorage handler = tile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
					if(handler != null && handler.canReceive() && handler.getEnergyStored() + output <= handler.getMaxEnergyStored())
					{
						handler.receiveEnergy(output, false);
						storage.consumeEnergy(output);
					}
					else return;
				}
			}
			markDirty();
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(this.pos.getX() - 32, this.pos.getY() -32, this.pos.getZ() - 32, this.pos.getX() + 32, this.pos.getY() + 32, this.pos.getZ() + 32);
		//return INFINITE_EXTENT_AABB;
	}
	
	public IBlockState getState() {
		return world.getBlockState(pos);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		
		if(capability.equals(CapabilityEnergy.ENERGY))
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
		return super.getCapability(capability, facing);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		this.storage.writeToNBT(compound);
		compound.setBoolean("hasConnection", this.connectionTo);
		compound.setBoolean("hasConnectionFrom", this.connectionFrom);
		compound.setFloat("angleY", this.angleY);
		compound.setFloat("angleZ", this.angleZ);
		if(this.connectionTo)
		{
			compound.setInteger("xTo", this.linkTo.getX());
			compound.setInteger("yTo", this.linkTo.getY());
			compound.setInteger("zTo", this.linkTo.getZ());			
		}
		if(this.connectionFrom)
		{
			compound.setInteger("xFrom", this.linkFrom.getX());
			compound.setInteger("yFrom", this.linkFrom.getY());
			compound.setInteger("zFrom", this.linkFrom.getZ());			
		}
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.storage.readFromNBT(compound);
		this.connectionTo = compound.getBoolean("hasConnection");
		this.connectionFrom = compound.getBoolean("hasConnectionFrom");
		this.angleY = compound.getFloat("angleY");
		this.angleZ = compound.getFloat("angleZ");
		if(this.connectionTo)
		{
			this.setLinkTo(compound.getInteger("xTo"), compound.getInteger("yTo"), compound.getInteger("zTo"));			
		}
		if(this.connectionFrom)
		{
			this.setLinkFrom(compound.getInteger("xFrom"), compound.getInteger("yFrom"), compound.getInteger("zFrom"));	
		}
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

}
