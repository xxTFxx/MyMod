package com.xxTFxx.siberianadv.gui;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.container.ContConcreteMixer;
import com.xxTFxx.siberianadv.tileentity.TE_ConcreteMixer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUI_ConcreteMixer extends GuiContainer{
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID + ":textures/gui/electric_furnace.png");
	private final InventoryPlayer player;
	private final TE_ConcreteMixer tile;
	
	public GUI_ConcreteMixer(InventoryPlayer player , TE_ConcreteMixer tile) 
	{
		super(new ContConcreteMixer(player, tile));
		this.tile = tile;
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
		// TODO Auto-generated method stub
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

}
