package com.xxTFxx.siberianadv.gui;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.container.ContainerSimpleGenerator;
import com.xxTFxx.siberianadv.tileentity.TileEntitySimpleGenerator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUISimpleGenerator extends GuiContainer{
	
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID + ":textures/gui/simple_generator.png");
	private final InventoryPlayer player;
	private final TileEntitySimpleGenerator tileentity;
	
	public GUISimpleGenerator(InventoryPlayer player , TileEntitySimpleGenerator tileentity) {
		super(new ContainerSimpleGenerator(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
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
		String tileName = this.tileentity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) -5, 6, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 7, this.ySize - 96 + 2, 4210752);
		
		if(mouseX > this.guiLeft + 150 && mouseY > this.guiTop + 5 && mouseX < this.guiLeft + 170 && mouseY < this.guiTop + 82)
		{
			this.drawHoveringText(Integer.toString(this.tileentity.getField(0)), mouseX - this.guiLeft, mouseY - this.guiTop);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		int k = this.getEnergyStoredScaled(75);
		this.drawTexturedModalRect(this.guiLeft + 152, this.guiTop + 7, 176, 32, 16, 75 - k);
		
		int l = this.getBurnProgressScaled(13);
		this.drawTexturedModalRect(this.guiLeft + 114, this.guiTop + 33 + 12 - l, 176, 13 - l, 14, l + 1);
	}
	
	private int getEnergyStoredScaled(int pixels)
	{
		int i = this.tileentity.getField(0);
		int j = this.tileentity.getField(3);
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	private int getBurnProgressScaled(int pixels)
	{
		
		int i = this.tileentity.getField(2);

        if (i == 0)
        {
            i = 100;
        }

        return this.tileentity.getField(1) * pixels / i;
	}
	
}
