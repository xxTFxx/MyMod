package com.xxTFxx.siberianadv.tileentity;

import com.xxTFxx.siberianadv.energy.CustomEnergyStorage;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntitySimpleGenerator extends TileEntity implements ITickable {

	public ItemStackHandler handler = new ItemStackHandler(1)
	{
		protected void onContentsChanged(int slot) {
			TileEntitySimpleGenerator.this.markDirty();
		};
	};
	private CustomEnergyStorage storage = new CustomEnergyStorage(100000 , 0 , 1000);
	private int capacity = storage.getMaxEnergyStored();
	private String customName;
	private int burnTime = 0 , totalBurnTime;
	private int output = 1000;
	private int ENERGY_GAIN;
	private boolean isTurned;
	
	@Override
	public void update() 
	{	

		
		if( !handler.getStackInSlot(0).isEmpty() )
		{
			if(isItemFuel(handler.getStackInSlot(0)))
			{
				if(getFuelValue(handler.getStackInSlot(0)) + storage.getEnergyStored() <= capacity)
				{
					if(burnTime == 0)
					{
						totalBurnTime = getTotalBurnTime(handler.getStackInSlot(0));
						ENERGY_GAIN = getFuelValue(handler.getStackInSlot(0)) / totalBurnTime;
						handler.getStackInSlot(0).shrink(1);				
						isTurned = true;
					}
					burnTime++;
					storage.addEnergy(ENERGY_GAIN);
					if(burnTime >= totalBurnTime )
					{
						burnTime = 0;
						isTurned = false;
						markDirty();
					}				
				}				
			}
		}
		else if(isTurned && ENERGY_GAIN + storage.getEnergyStored() <= capacity)
		{
			burnTime++;
			storage.addEnergy(ENERGY_GAIN);
			if(burnTime >= totalBurnTime )
			{
				burnTime = 0;
				isTurned = false;
				markDirty();
			}				
		}
		
		
		sendEnergy();
	}
	
	
	private void sendEnergy() {
		if(storage.getEnergyStored() >= output)
		{
			for(EnumFacing facing : EnumFacing.VALUES)
			{
				TileEntity tile = world.getTileEntity(pos.offset(facing));
				if(tile != null && tile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()))
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


	private int getTotalBurnTime(ItemStack stackIn)
	{
		if(!stackIn.isEmpty())
		{
			return getFuelValue(stackIn)/100;
		}
		return 1;
	}
	
	private int getFuelValue(ItemStack stackInSlot) {
		if (stackInSlot.isEmpty())
        {
            return 0;
        }
        else
        {
            int burnTime = net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stackInSlot);
            if (burnTime >= 0) return burnTime;
            Item item = stackInSlot.getItem();

            if (item == Item.getItemFromBlock(Blocks.WOODEN_SLAB))
            {
                return 400;
            }
            else if(item == item.getItemFromBlock(Blocks.PLANKS))
            {
            	return 2000;
            }
            else if (item == Items.COAL)
            {
                return 24000;
            }
            else if (item == Item.getItemFromBlock(Blocks.WOOL))
            {
                return 800;
            }
            else if (item == Item.getItemFromBlock(Blocks.LADDER))
            {
                return 100;
            }
            else if (item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON))
            {
                return 100;
            }
            else if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD)
            {
                return 2000;
            }
            else if (item == Item.getItemFromBlock(Blocks.COAL_BLOCK))
            {
                return 216000;
            }
            else if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName()))
            {
                return 2000;
            }
            else if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName()))
            {
                return 2000;
            }
            else if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName()))
            {
                return 2000;
            }
            else if (item == Items.STICK)
            {
                return 100;
            }
            else if (item == Items.SIGN)
            {
                return 400;
            }
            else if (item == Item.getItemFromBlock(Blocks.SAPLING))
            {
                return 400;
            }
            else if (item == Items.BLAZE_ROD)
            {
                return 24000;
            }
            else if (item instanceof ItemDoor && item != Items.IRON_DOOR)
            {
                return 2000;
            }

            else
            {
                return 0;
            }
        }
    }

	private boolean isItemFuel(ItemStack stackInSlot) {
		if(getFuelValue(stackInSlot) > 0)
		{
			return true;
		}
		return false;
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
		compound.setInteger("burnTime", this.burnTime);
		compound.setInteger("totalBurnTime", totalBurnTime);
		if(this.ENERGY_GAIN != 0)
		{
			compound.setInteger("ENERGY_GAIN", this.ENERGY_GAIN);
		}
		this.storage.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		this.burnTime = compound.getInteger("burnTime");
		this.totalBurnTime = compound.getInteger("totalBurnTime");
		if(compound.getTag("ENERGY_GAIN") != null)
		{
			this.ENERGY_GAIN = compound.getInteger("ENERGY_GAIN");
		}
		this.storage.readFromNBT(compound);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("container.simple_generator");
	}
	
	public int getEnergyStored()
	{
		return this.storage.getEnergyStored();
	}
	
	public int getMaxEnergyStored()
	{
		return this.storage.getMaxEnergyStored();
	}
	
	public int getField(int id)
	{
		switch (id) {
		case 0:
			return this.storage.getEnergyStored();
		case 1:
			return this.burnTime;
		case 2:
			return this.totalBurnTime;
		case 3:
			return this.capacity;
		default:
			return 0;
		}
	}
	
	public void setField(int id , int value)
	{
		switch (id) {
		case 0:
			this.storage.setEnergy(value);
		case 1:
			this.burnTime = value;

		}
	}
	
	@Override
	public final NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.getPos(), 1, this.getUpdateTag());
	}
	
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}
	
	
	
	
	
	
	
}
