package com.xxTFxx.siberianadv.gui;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.container.ContSimpleMiner;
import com.xxTFxx.siberianadv.tileentity.TESimpleMiner;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.ResourceLocation;

public class GUISimpleMiner extends GuiContainer{

	private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation(Main.MOD_ID + ":textures/gui/simple_miner.png");
	private final InventoryPlayer playerInventory;
	private final TESimpleMiner te;
    private final int inventoryRows;

    public GUISimpleMiner(InventoryPlayer playerInventory, TESimpleMiner chestInventory)
    {
        super(new ContSimpleMiner(playerInventory, chestInventory));
        this.playerInventory = playerInventory;
        this.te = chestInventory;
        this.allowUserInput = false;
        int i = 222;
        int j = 114;
        this.inventoryRows = this.te.getSizeInventory() / 9;
        this.ySize = 114 + this.inventoryRows * 18;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
    	if(mouseX > this.guiLeft + 190 && mouseY > this.guiTop + 7 && mouseX < this.guiLeft + 208 && mouseY < this.guiTop + 80)
		{
			this.drawHoveringText(Integer.toString(this.te.getEnergyStored()), mouseX - this.guiLeft, mouseY - this.guiTop);
		}
    	
        //this.fontRenderer.drawString(this.te.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        ////this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        //this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize + 39, this.ySize);
        
        int k = this.getEnergyStoredScaled(72);
		this.drawTexturedModalRect(this.guiLeft + 192, this.guiTop + 8, 215, 0, 16, 72 - k);
    }
    
    
    
    private int getEnergyStoredScaled(int pixels)
	{
		int i = this.te.getEnergyStored();
		int j = this.te.getMaxEnergyStored();
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
}
