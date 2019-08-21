package com.xxTFxx.siberianadv.tileentity;

import java.util.Random;

import javax.annotation.Nullable;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.container.ContSimpleMiner;
import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;
import com.xxTFxx.siberianadv.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TESimpleMiner extends BasicEnergyTile implements ITickable{
	
	public ItemStackHandler handler = new ItemStackHandler(27)
	{
		protected void onContentsChanged(int slot) {
			TESimpleMiner.this.markDirty();
		};
	};
	
	private CustomEnergyStorage storage = new CustomEnergyStorage(20000 , 500 , 0);
	private BlockPos blockPos;
	private IBlockState state;
	private int blockX = 1;
	private int blockY = 1;
	private int blockZ = 1;
	private int timer = 0;
	private int totalTime;
	private int ENERGY_DRAIN = 20;
	private boolean working = false;
	private boolean isTurned = true;
	private boolean shouldUpdate = false;
	
	@Override
	public void update() {
		if(!working && storage.getEnergyStored() >= ENERGY_DRAIN && isTurned)
		{
			blockPos = new BlockPos(pos.getX() - blockX, pos.getY() - blockY, pos.getZ() - blockZ);
			state = world.getBlockState(blockPos);
			
			while(state.getBlock() == Blocks.AIR || state.getBlock() instanceof BlockLiquid)
			{
				nextBlock();
				blockPos = new BlockPos(pos.getX() - blockX, pos.getY() - blockY, pos.getZ() - blockZ);
				state = world.getBlockState(blockPos);
			}
			
			totalTime = (int) world.getBlockState(blockPos).getBlock().getBlockHardness(state, world, blockPos)*10;
			working = true;
			
			if(state.getBlock() == Blocks.BEDROCK)
			{
				if(!scanBottom())
				{
					isTurned = false;
				}
			}
			
			
		}
		
		if(working && storage.getEnergyStored() >= ENERGY_DRAIN && isTurned)
		{
			timer++;
			storage.consumeEnergy(ENERGY_DRAIN);
			shouldUpdate = true;
		}
		else if(working)
		{
			working = false;
		}
		
		if(timer >= totalTime)
		{
			if(state != null && state.getBlock() != Blocks.BEDROCK && state.getBlock() != Blocks.AIR)
			{
				NonNullList<ItemStack> list = NonNullList.<ItemStack>create();				
				state.getBlock().getDrops(list, world, blockPos, state, 0);

				for(ItemStack stack : list)
				{
					if(Utils.isThereSpaceForStack(handler, stack))
					{
						Utils.addStackToInventory(handler, stack, false);						
					}
					else
					{
						state.getBlock().dropBlockAsItem(world, pos, state, 0);
					}
				}
				world.setBlockToAir(blockPos);				
			}
			shouldUpdate = true;
			
			nextBlock();
			timer = 0;
			working = false;
			
		}
		
		if(shouldUpdate)
		{
			sendUpdates();
			shouldUpdate = false;
		}
	}
	
	private boolean scanBottom()
	{
		BlockPos scanPos;
		Block block;
		for(int x = 1 ; x >= -1 ; x--)
		{
			for(int z = 1 ; z >= -1 ; z--)
			{
				for(int y = 0 ; y < 3 ; y++)
					{
						scanPos = new BlockPos(this.pos.getX() - x, this.blockY - y, this.pos.getZ() - z);
						block = world.getBlockState(scanPos).getBlock();
						if(block != Blocks.BEDROCK && block != Blocks.AIR)
						{
							return true;
						}
					}
			}
		}
		return false;
	}
	
	private void nextBlock()
	{
		this.blockX--;
		if(this.blockX < -1)
		{
			this.blockX = 1;
			this.blockZ--;
			if(this.blockZ < -1)
			{
				this.blockZ = 1;
				this.blockY++;
				if(this.blockY > 150)
				{
					this.blockY = 2;
				}
			}
		}
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	public int getEnergyStored()
	{
		return this.storage.getEnergyStored();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		this.storage.writeToNBT(compound);
		compound.setTag("Inventory", this.handler.serializeNBT());
		compound.setInteger("MineX", this.blockX);
		compound.setInteger("MineY", this.blockY);
		compound.setInteger("MineZ", this.blockZ);
		compound.setInteger("CurrentTime", this.timer);
		compound.setBoolean("Working", this.working);
		compound.setBoolean("isTurned", this.isTurned);
		super.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.storage.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.blockX = compound.getInteger("MineX");
		this.blockY = compound.getInteger("MineY");
		this.blockZ = compound.getInteger("MineZ");
		this.timer = compound.getInteger("CurrentTime");
		this.working = compound.getBoolean("Working");
		this.isTurned = compound.getBoolean("isTurned");
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
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY)
		{
			return (T)this.storage;
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
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public int getSizeInventory() {
		return 27;
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
