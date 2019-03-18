package com.xxTFxx.siberianadv.gui;

import java.awt.Container;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.container.ContainerPhotovoltaicPanel;
import com.xxTFxx.siberianadv.tileentity.TileEntityPhotovoltaicPanel;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIPhotovoltaicPanel extends GuiContainer{
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID + ":textures/gui/solar.png");
	private final TileEntityPhotovoltaicPanel tileentity;
	
	public GUIPhotovoltaicPanel(TileEntityPhotovoltaicPanel tileentity) {
		super(new ContainerPhotovoltaicPanel(tileentity));
		this.tileentity = tileentity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		this.fontRenderer.drawString(Integer.toString(this.tileentity.getEnergyStored()), 100, 72, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect((this.width - 88) / 2, (this.height - this.ySize)/2 , 0, 0, 88, 83);
		
		int k = this.getEnergyStoredScaled(75);
		this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 5 , 88, 0, 16, 75 - k);
		
	}
	
	private int getEnergyStoredScaled(int pixels)
	{
		int i = this.tileentity.getField(0);
		int j = this.tileentity.getField(1);
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	
}
