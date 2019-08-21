package com.xxTFxx.siberianadv.gui;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.container.ContTableSaw;
import com.xxTFxx.siberianadv.tileentity.TETableSaw;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUITableSaw extends GuiContainer{

	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID + ":textures/gui/table_saw.png");
	private final InventoryPlayer player;
	private final TETableSaw te;
	
	public GUITableSaw(InventoryPlayer player , TETableSaw te) {
		super(new ContTableSaw(player, te));
		this.te = te;
		this.player = player;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		this.fontRenderer.drawString(Integer.toString(this.te.getEnergyStored()), 115, 72, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int k = this.getEnergyStoredScaled(75);
		this.drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 7, 176, 31, 16, 73 - k);
		
		int l = this.getBurnProgressScaled(24);
		this.drawTexturedModalRect(this.guiLeft + 64, this.guiTop + 32, 176, 12, l, 19);
		
	}
	
	private int getEnergyStoredScaled(int pixels)
	{
		int i = this.te.getEnergyStored();
		int j = this.te.getMaxEnergyStored();
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	private int getBurnProgressScaled(int pixels)
	{
		int i = this.te.getTime();
		int j = this.te.getTotalTime();
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
}
