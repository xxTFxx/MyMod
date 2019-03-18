package com.xxTFxx.siberianadv.block.machines;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.block.RotBlock;
import com.xxTFxx.siberianadv.init.FluidInit;
import com.xxTFxx.siberianadv.init.ModBlocks;
import com.xxTFxx.siberianadv.tabs.ModTab;
import com.xxTFxx.siberianadv.tileentity.TileEntityPortableGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class PortableGenerator extends Block{
	
	//public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public PortableGenerator(String name) {
		super(Material.IRON);
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setRegistryName(name);
		setCreativeTab(ModTab.Mod_Tab);
		setHardness(0.5F);
		ModBlocks.blocks.add(this);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		IFluidHandler handler = worldIn.getTileEntity(pos).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		TileEntityPortableGenerator tile = (TileEntityPortableGenerator)worldIn.getTileEntity(pos);

		if(playerIn.getHeldItemMainhand().getItem() == FluidUtil.getFilledBucket(new FluidStack(FluidInit.PETROLEUM_FLUID, Fluid.BUCKET_VOLUME)).getItem() && tile.canFillTank(1000))
			if(playerIn.isCreative())
			{
				handler.fill(new FluidStack(FluidInit.PETROLEUM_FLUID, 1000), true);
			}
			else
			{
				handler.fill(new FluidStack(FluidInit.PETROLEUM_FLUID, 1000), true);
				playerIn.getHeldItemMainhand().shrink(1);
				playerIn.addItemStackToInventory(new ItemStack(Items.BUCKET));
			}
		else if(!worldIn.isRemote) {
			playerIn.openGui(Main.MOD_ID, Main.GUI_PORTABLE_GENERATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
			System.out.println(tile.getEnergyStored());
			System.out.println(tile.getFluidAmount());
		}
		return true;	
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityPortableGenerator();
	}
	
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
    }
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(tileentity instanceof TileEntityPortableGenerator)
		{
			TileEntityPortableGenerator tileEntityPortableGenerator = (TileEntityPortableGenerator)tileentity;
			
			ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("Energy", tileEntityPortableGenerator.getEnergyStored());
            nbttagcompound.setInteger("Fuel", tileEntityPortableGenerator.getFluidAmount());            	

            itemstack.setTagCompound(nbttagcompound);

            spawnAsEntity(worldIn, pos, itemstack);

		}
		super.breakBlock(worldIn, pos, state);
	}
	
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		
		NBTTagCompound nbt = stack.getTagCompound();
		
		if(nbt != null)
		{
			TileEntityPortableGenerator tileGenerator = (TileEntityPortableGenerator)worldIn.getTileEntity(pos);
			tileGenerator.setEnergy(nbt.getInteger("Energy"));
			tileGenerator.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).fill(new FluidStack(FluidInit.PETROLEUM_FLUID, nbt.getInteger("Fuel")), true);
		}
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
}
