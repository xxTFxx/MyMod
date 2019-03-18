package com.xxTFxx.siberianadv.gui;

import java.awt.Container;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.container.ContainerEnergyStorage;
import com.xxTFxx.siberianadv.container.ContainerSimpleGenerator;
import com.xxTFxx.siberianadv.tileentity.TileEntityEnergyStorage_ITier;
import com.xxTFxx.siberianadv.tileentity.TileEntitySimpleGenerator;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIEnergyStorage extends GuiContainer{
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID + ":textures/gui/simple_generator.png");
	private final TileEntityEnergyStorage_ITier tileentity;
	
	public GUIEnergyStorage(TileEntityEnergyStorage_ITier tileentity) {
		super(new ContainerEnergyStorage(tileentity));
		this.tileentity = tileentity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		//String tileName = this.tileentity.getDisplayName().getUnformattedText();
		//this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) -5, 6, 4210752);
		//this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 7, this.ySize - 96 + 2, 4210752);
		this.fontRenderer.drawString(Integer.toString(this.tileentity.getEnergyStored()), 115, 72, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int k = this.getEnergyStoredScaled(75);
		this.drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 7, 176, 32, 16, 75 - k);
		
	}
	
	private int getEnergyStoredScaled(int pixels)
	{
		int i = this.tileentity.getField(0);
		int j = this.tileentity.getField(1);
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	
}
