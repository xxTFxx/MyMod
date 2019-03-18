package com.xxTFxx.siberianadv.util.handlers;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.container.ContainerElectricFurnace_ITier;
import com.xxTFxx.siberianadv.container.ContainerEnergyStorage;
import com.xxTFxx.siberianadv.container.ContainerInductionFurnace;
import com.xxTFxx.siberianadv.container.ContainerPhotovoltaicPanel;
import com.xxTFxx.siberianadv.container.ContainerPortableGenerator;
import com.xxTFxx.siberianadv.container.ContainerSimpleGenerator;
import com.xxTFxx.siberianadv.gui.GUIElectricFurnace_ITier;
import com.xxTFxx.siberianadv.gui.GUIEnergyStorage;
import com.xxTFxx.siberianadv.gui.GUIInductionFurnace;
import com.xxTFxx.siberianadv.gui.GUIPhotovoltaicPanel;
import com.xxTFxx.siberianadv.gui.GUIPortableGenerator;
import com.xxTFxx.siberianadv.gui.GUISimpleGenerator;
import com.xxTFxx.siberianadv.tileentity.TileEntityElectricFurnace_ITier;
import com.xxTFxx.siberianadv.tileentity.TileEntityEnergyStorage_ITier;
import com.xxTFxx.siberianadv.tileentity.TileEntityInductionFurnace;
import com.xxTFxx.siberianadv.tileentity.TileEntityPhotovoltaicPanel;
import com.xxTFxx.siberianadv.tileentity.TileEntityPortableGenerator;
import com.xxTFxx.siberianadv.tileentity.TileEntitySimpleGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler{

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Main.GUI_SIMPLE_GENERATOR) return new GUISimpleGenerator(player.inventory, (TileEntitySimpleGenerator)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Main.GUI_ENERGYSTORAGE) return new GUIEnergyStorage((TileEntityEnergyStorage_ITier)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Main.GUI_PHOTOVOLTAICPANEL) return new GUIPhotovoltaicPanel((TileEntityPhotovoltaicPanel)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Main.GUI_ELECTRICFURNACE_1) return new GUIElectricFurnace_ITier(player.inventory , (TileEntityElectricFurnace_ITier)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Main.GUI_INDUCTIONFURNACE) return new GUIInductionFurnace(player.inventory , (TileEntityInductionFurnace)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Main.GUI_PORTABLE_GENERATOR) return new GUIPortableGenerator(player.inventory , (TileEntityPortableGenerator)world.getTileEntity(new BlockPos(x,y,z)));

		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Main.GUI_SIMPLE_GENERATOR) return new ContainerSimpleGenerator(player.inventory, (TileEntitySimpleGenerator)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Main.GUI_ENERGYSTORAGE) return new ContainerEnergyStorage((TileEntityEnergyStorage_ITier)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Main.GUI_PHOTOVOLTAICPANEL) return new ContainerPhotovoltaicPanel((TileEntityPhotovoltaicPanel)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Main.GUI_ELECTRICFURNACE_1) return new ContainerElectricFurnace_ITier(player.inventory , (TileEntityElectricFurnace_ITier)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Main.GUI_INDUCTIONFURNACE) return new ContainerInductionFurnace(player.inventory , (TileEntityInductionFurnace)world.getTileEntity(new BlockPos(x,y,z)));
		if(ID == Main.GUI_PORTABLE_GENERATOR) return new ContainerPortableGenerator(player.inventory , (TileEntityPortableGenerator)world.getTileEntity(new BlockPos(x,y,z)));

		return null;
	}
}
