package com.xxTFxx.siberianadv.gui;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.container.ContainerPortableGenerator;
import com.xxTFxx.siberianadv.network.MessageOnButtonPressed;
import com.xxTFxx.siberianadv.network.MyMessage;
import com.xxTFxx.siberianadv.network.PacketHandler;
import com.xxTFxx.siberianadv.tileentity.TileEntityPortableGenerator;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIPortableGenerator extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Main.MOD_ID + ":textures/gui/portable_generator.png");
	private final TileEntityPortableGenerator tileentity;
	private final InventoryPlayer player;
	
	public GUIPortableGenerator(InventoryPlayer player , TileEntityPortableGenerator tileentity) {
		super(new ContainerPortableGenerator(player, tileentity));
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
	public void initGui() {
		super.initGui();
		
		this.buttonList.add(new GuiButton(0, this.guiLeft + 20, this.guiTop + 20 , 30 , 20 , this.setButtonText()));
	}
	
	private String setButtonText()
	{
		if(this.tileentity.isWorking())
		{
			return "OFF";
		}
		else return "ON";
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		
		if(this.tileentity.isWorking())
		{
			this.selectedButton.displayString = "ON";
		}
		else
		{
			this.selectedButton.displayString = "OFF";
		}
		
		PacketHandler.INSTANCE.sendToServer(new MessageOnButtonPressed(this.tileentity, 0));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		if(mouseX > this.guiLeft + 150 && mouseY > this.guiTop + 5 && mouseX < this.guiLeft + 169 && mouseY < this.guiTop + 80)
		{
			this.drawHoveringText(Integer.toString(this.tileentity.getEnergyStored()), mouseX - this.guiLeft, mouseY - this.guiTop);
		}	
		if(mouseX > this.guiLeft + 128 && mouseY > this.guiTop + 5 && mouseX < this.guiLeft + 147 && mouseY < this.guiTop + 80)
		{
			this.drawHoveringText(Integer.toString(this.tileentity.getFluidAmount()), mouseX - this.guiLeft, mouseY - this.guiTop);
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
		
		int l = this.getFluidStored(75);
		this.drawTexturedModalRect(this.guiLeft + 130, this.guiTop + 7, 176, 32, 16, 75 - l);
	}
	
	private int getEnergyStoredScaled(int pixels)
	{
		int i = this.tileentity.getEnergyStored();
		int j = this.tileentity.getMaxEnergyStored();
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
	
	private int getFluidStored(int pixels)
	{
		int i = this.tileentity.getFluidAmount();
		int j = this.tileentity.getMaxFluidAmount();
		return i != 0 && j != 0 ? i * pixels / j : 0; 
	}
}