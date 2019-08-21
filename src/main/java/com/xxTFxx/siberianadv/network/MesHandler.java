package com.xxTFxx.siberianadv.network;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.xxTFxx.siberianadv.render.LineRender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MesHandler implements IMessageHandler<Mes, IMessage>{

	@Override
	public IMessage onMessage(Mes message, MessageContext ctx) {
		
		if (ctx.side != Side.CLIENT) 
		{
		      System.err.println("Message received on wrong side:" + ctx.side);
		      return null;
		}
		
		
		int x = message.x;
		int y = message.y;
		int z = message.z;
		
		Vec3d vec1 = new Vec3d(x, y, z);
		Vec3d vec2 = new Vec3d(x + 2, y , z );
		
		//IThreadListener mainThread = Minecraft.getMinecraft();
		Minecraft mainThread = Minecraft.getMinecraft();
		final WorldClient worldClient = mainThread.world;
		mainThread.addScheduledTask(new Runnable() {
			
			@Override
			public void run() {
				drawBoundingBox(vec1, vec2 , vec1);
				//LineRender.renderLightning(5, 5, 5, 16.0D);
				//processMessage(worldClient, message, vec1);
				System.out.println(x + "  " + y + "  " + z);
			}
		});    	
		
		return null;
	}

	
	void processMessage(WorldClient worldClient, Mes message , Vec3d targetCoordinates)
	  {
	    Random random = new Random();
	    final int NUMBER_OF_PARTICLES = 100;
	    final double HORIZONTAL_SPREAD = 1.5;
	    for (int i = 0; i < NUMBER_OF_PARTICLES; ++i) {
	      double spawnXpos = targetCoordinates.x + (2*random.nextDouble() - 1) * HORIZONTAL_SPREAD;
	      double spawnYpos = targetCoordinates.y;
	      double spawnZpos = targetCoordinates.z + (2*random.nextDouble() - 1) * HORIZONTAL_SPREAD;
	      worldClient.spawnParticle(EnumParticleTypes.SPELL_INSTANT, spawnXpos, spawnYpos, spawnZpos, 0, 0, 0);
	    }

	    return;
	  }
	
	public static void drawBoundingBox(Vec3d posA, Vec3d posB , Vec3d player) 
	{
		GlStateManager.pushMatrix();
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.glLineWidth(2.0F);
		GlStateManager.translate(-player.x, -player.y, -player.z);

	    Tessellator tessellator = Tessellator.getInstance();
	    BufferBuilder buffer = tessellator.getBuffer();
	    buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
	    buffer.pos(posA.x, posA.y, posA.z).color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha()).endVertex();
	    buffer.pos(posB.x, posB.y, posB.z).color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha()).endVertex();
	    
	    tessellator.draw();

		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.popMatrix();
		/*int x = (int)posA.x;
		int y = (int)posA.y;
		int h = 16;
		int w = 16;
		int colour = 16;
		
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(x, y+h, 0).color(colour >> 16&255, colour >> 8&255, colour&255, colour >> 24&255).endVertex();
		worldrenderer.pos(x+w, y+h, 0).color(colour >> 16&255, colour >> 8&255, colour&255, colour >> 24&255).endVertex();
		worldrenderer.pos(x+w, y, 0).color(colour >> 16&255, colour >> 8&255, colour&255, colour >> 24&255).endVertex();
		worldrenderer.pos(x, y, 0).color(colour >> 16&255, colour >> 8&255, colour&255, colour >> 24&255).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(GL11.GL_FLAT);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();*/
        
	}
	
}
