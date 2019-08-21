package com.xxTFxx.siberianadv.gui;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.container.Cont_ElectricAnvil;
import com.xxTFxx.siberianadv.tileentity.TE_ElectricAnvil;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUI_ElectricAnvil extends GuiContainer{
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID + ":textures/gui/electric_anvil.png");
	private final TE_ElectricAnvil tileentity;
	private final InventoryPlayer player;

	public GUI_ElectricAnvil(InventoryPlayer player ,TE_ElectricAnvil tileentity)
	{
		super(new Cont_ElectricAnvil(player, tileentity));
		this.tileentity = tileentity;
		this.player = player;
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
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int k = this.getEnergyStoredScaled(75);
		this.drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 7 , 176, 21, 16, 75 - k);
		
	}
	
	private int getEnergyStoredScaled(int pixels)
	{
		int i = this.tileentity.getEnergyStored();
		int j = this.tileentity.getMaxEnergyStored();
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}

}
