package com.xxTFxx.siberianadv.tileentity.cables;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TE_Connector extends TileEntity{
	
	private boolean connected = false;
	private BlockPos linkTo;
	
	public boolean isConnected()
	{
		return this.connected;
	}
	
	public void changeConnectionState()
	{
		this.connected = !this.connected;
	}
	
	public void setLinkTo(int x , int y , int z)
	{
		this.linkTo = new BlockPos(x, y, z);
	}
	
	public BlockPos getLinkTo()
	{
		return this.linkTo;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		//return new AxisAlignedBB(this.pos.getX() - 32, this.pos.getY() -32, this.pos.getZ() - 32, this.pos.getX() + 32, this.pos.getY() + 32, this.pos.getZ() + 32);
		return INFINITE_EXTENT_AABB;
	}
	
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("hasConnection", this.connected);
		if(this.connected)
		{
			compound.setInteger("xTo", this.linkTo.getX());
			compound.setInteger("yTo", this.linkTo.getY());
			compound.setInteger("zTo", this.linkTo.getZ());			
		}
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.connected = compound.getBoolean("hasConnection");
		if(this.connected)
		{
			this.setLinkTo(compound.getInteger("xTo"), compound.getInteger("yTo"), compound.getInteger("zTo"));			
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
