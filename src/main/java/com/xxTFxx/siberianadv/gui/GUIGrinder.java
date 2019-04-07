package com.xxTFxx.siberianadv.gui;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.container.ContGrinder;
import com.xxTFxx.siberianadv.tileentity.TEGrinder;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIGrinder extends GuiContainer{
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID + ":textures/gui/electric_furnace.png");
	private final InventoryPlayer player;
	private final TEGrinder tileentity;
	
	public GUIGrinder(InventoryPlayer player, TEGrinder tileentity) {
		super(new ContGrinder(player, tileentity));
		this.tileentity = tileentity;
		this.player = player;
	}
	
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		if(mouseX > this.guiLeft + 150 && mouseY > this.guiTop + 5 && mouseX < this.guiLeft + 170 && mouseY < this.guiTop + 82)
		{
			this.drawHoveringText(Integer.toString(this.tileentity.getEnergyStored()) + " / " + Integer.toString(this.tileentity.getMaxEnergyStored()), mouseX - this.guiLeft, mouseY - this.guiTop);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int l = this.getBurnProgressScaled(24);
		this.drawTexturedModalRect(this.guiLeft + 61, this.guiTop + 32, 176, 14, l + 1, 16);
		
		int k = this.getEnergyStoredScaled(75);
		this.drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 7, 176, 32, 16, 75 - k);
	}
	
	private int getEnergyStoredScaled(int pixels)
	{
		int i = this.tileentity.getEnergyStored();
		int j = this.tileentity.getMaxEnergyStored();
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	private int getBurnProgressScaled(int pixels)
	{
		int i = this.tileentity.getTotalTime();

        if (i == 0)
        {
            i = 100;
        }

        return this.tileentity.getCurrentTime() * pixels / i;
	}
}
