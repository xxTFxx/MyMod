package com.xxTFxx.siberianadv.render;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LineRender {
	
	//@SideOnly(Side.CLIENT)
	public static void drawBoundingBox(Vec3d player_pos, Vec3d posA, Vec3d posB, float width) 
	{
		//GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		//GL11.glTranslated(-player_pos.x, -player_pos.y, -player_pos.z);
		//System.out.println(posA.x + "  " + posA.y + "  " + posA.z);

		Color c = new Color(255, 0, 0, 255);
		GL11.glColor4d(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
		GL11.glLineWidth(width);
		GL11.glDepthMask(false);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		bufferBuilder.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

		double dx = Math.abs(posA.x - posB.x);
		double dy = Math.abs(posA.y - posB.y);
		double dz = Math.abs(posA.z - posB.z);
		

		//AB
		bufferBuilder.pos(posA.x, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();          //A
		bufferBuilder.pos(posA.x, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //B
		//BC
		bufferBuilder.pos(posA.x, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //B
		bufferBuilder.pos(posA.x+dx, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //C
		//CD
		bufferBuilder.pos(posA.x+dx, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //C
		bufferBuilder.pos(posA.x+dx, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //D
		//DA
		bufferBuilder.pos(posA.x+dx, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //D
		bufferBuilder.pos(posA.x, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();          //A
		//EF
		bufferBuilder.pos(posA.x, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //E
		bufferBuilder.pos(posA.x, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //F
		//FG
		bufferBuilder.pos(posA.x, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //F
		bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex(); //G
		//GH
		bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex(); //G
		bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //H
		//HE
		bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //H
		bufferBuilder.pos(posA.x, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //E
		//AE
		bufferBuilder.pos(posA.x, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();          //A
		bufferBuilder.pos(posA.x, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //E
		//BF
		bufferBuilder.pos(posA.x, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //B
		bufferBuilder.pos(posA.x, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //F
		//CG
		bufferBuilder.pos(posA.x+dx, posA.y, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //C
		bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z+dz).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex(); //G
		//DH
		bufferBuilder.pos(posA.x+dx, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();       //D
		bufferBuilder.pos(posA.x+dx, posA.y+dy, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();    //H

		tessellator.draw();      


		GL11.glDepthMask(true);
		//GL11.glPopAttrib();
	}
	
	public static void drawLine(Vec3d posA, Vec3d posB) 
	{
		GlStateManager.disableTexture2D();
		GlStateManager.glLineWidth(2.0F);

	    Tessellator tessellator = Tessellator.getInstance();
	    BufferBuilder buffer = tessellator.getBuffer();
	    buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
	    buffer.pos(posA.x , posA.y , posA.z).color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha()).endVertex();
	    buffer.pos(posB.x , posB.y , posB.z).color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha()).endVertex();
	    
	    tessellator.draw();

		GlStateManager.enableTexture2D();
	}

	//@SideOnly(Side.CLIENT)
	public static void renderLightning(final int number, final int NumberOfBranches, final int NumberOfPossibleSubBranches, final double scale) {
		GlStateManager.depthMask(true);
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);

		final Tessellator tessellator = Tessellator.getInstance();
		final BufferBuilder bufferbuilder = tessellator.getBuffer();

		final double scale16 = scale / 16;

		final double[] translateXArray = new double[8];
		final double[] translateZArray = new double[8];
		double tempX = 0.0D;
		double tempZ = 0.0D;
		final Random random = new Random(number);

		for (int counter_ = 7; counter_ >= 0; --counter_) {
			translateXArray[counter_] = tempX;
			translateZArray[counter_] = tempZ;
			tempX += random.nextInt(11) - 5;
			tempZ += random.nextInt(11) - 5;
		}

		for (int shells = 0; shells < 4; ++shells) {
			final Random random1 = new Random(number);
			for (int branches = 0; branches < NumberOfBranches; branches++) {
				for (int possibleSubBranches = 0; possibleSubBranches < (NumberOfPossibleSubBranches + 1); ++possibleSubBranches) {
					int position = 7;
					int decendingHeight = 0;

					if (possibleSubBranches > 0) {
						position = Math.abs((7 - possibleSubBranches) % translateXArray.length);
					}

					if (possibleSubBranches > 0) {
						decendingHeight = position - 2;
					}

					double topTranslateX = translateXArray[position];
					double topTranslateZ = translateZArray[position];

					for (int yPos = position; yPos >= decendingHeight; --yPos) {
						final double bottomTranslateX = topTranslateX;
						final double bottomTranslateZ = topTranslateZ;

						if (possibleSubBranches == 0) { // Main branch
							topTranslateX += random1.nextInt(11) - 5;
							topTranslateZ += random1.nextInt(11) - 5;
						} else {// Sub branch
							topTranslateX += random1.nextInt(31) - 15;
							topTranslateZ += random1.nextInt(31) - 15;
						}

						bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
						double topWidth = 0.1D + (shells * 0.2D);

						if (yPos == 0) {
							topWidth *= (yPos * 0.1D) + 1.0D;
						}

						double bottomWidth = 0.1D + (shells * 0.2D);

						if (yPos == 0) {
							bottomWidth *= ((yPos - 1) * 0.1D) + 1.0D;
						}

						topWidth *= (scale / 16);
						bottomWidth *= (scale / 16);

						for (int side = 0; side < 5; ++side) {
							double topOffsetX = -topWidth;
							double topOffsetZ = -topWidth;

							if ((side == 1) || (side == 2)) {
								topOffsetX += topWidth * 2.0D;
							}

							if ((side == 2) || (side == 3)) {
								topOffsetZ += topWidth * 2.0D;
							}

							double bottomOffsetX = -bottomWidth;
							double bottomOffsetZ = -bottomWidth;

							if ((side == 1) || (side == 2)) {
								bottomOffsetX += bottomWidth * 2.0D;
							}

							if ((side == 2) || (side == 3)) {
								bottomOffsetZ += bottomWidth * 2.0D;
							}

							bufferbuilder.pos(bottomOffsetX + (topTranslateX * scale16), yPos * scale, bottomOffsetZ + (topTranslateZ * scale16)).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
							bufferbuilder.pos(topOffsetX + (bottomTranslateX * scale16), (yPos + 1) * scale, topOffsetZ + (bottomTranslateZ * scale16)).color(0.45F, 0.45F, 0.5F, 0.3F).endVertex();
						}

						tessellator.draw();

					}
				}
			}

		}

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
	}
	
}
