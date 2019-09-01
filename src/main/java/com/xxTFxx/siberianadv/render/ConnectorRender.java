package com.xxTFxx.siberianadv.render;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.xxTFxx.siberianadv.tileentity.cables.TE_Connector;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ConnectorRender extends TileEntitySpecialRenderer<TE_Connector>{

	private boolean asd = false;
	
	@Override
	public void render(TE_Connector te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {

		if(te.hasConnectionTo())
		{
			BlockPos linkTo = te.getLinkTo();
			
			Vec3d vec1 = new Vec3d(x + 0.5D, y + 0.5D,  z + 0.5D);
			Vec3d vec2 = new Vec3d(linkTo.getX() + 0.5D , linkTo.getY() + 0.5D, linkTo.getZ() + 0.5D);
			
			GlStateManager.pushMatrix();

			GlStateManager.disableTexture2D();
			GlStateManager.glLineWidth(4.0F);

		    Tessellator tessellator = Tessellator.getInstance();
		    BufferBuilder buffer = tessellator.getBuffer();
		    buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		    double length = Math.sqrt( (te.getPos().getX() - linkTo.getX()) * (te.getPos().getX() - linkTo.getX())); // + (te.getPos().getY() - linkTo.getY()) * (te.getPos().getY() - linkTo.getY()) );// + (te.getPos().getZ() - linkTo.getZ()) * (te.getPos().getZ() - linkTo.getZ()) );
		    if(length == 0)
		    {
		    	length = Math.sqrt( (te.getPos().getZ() - linkTo.getZ()) * (te.getPos().getZ() - linkTo.getZ()) );
		    }
		    double a = 1 / (25 * Math.sqrt(length));
		    
		    for(double i = 0 ; i <= length + 0.1 ; i += 0.2D)
		    {
		    	double y1 = a * (i) * (i - length);
		    	double z1 = i * Math.tan(Math.toRadians(te.getZAngle()));
		    	if(te.getZAngle() == 90)
		    	{
		    		z1 = -i;
		    	}
		    	else if(te.getZAngle() == -90)
		    	{
		    		z1 = i;
		    	}
		    	double y2 = Math.sqrt((i*i + z1*z1)) * Math.tan(Math.toRadians(te.getYAngle()));
		    	Vec3d vec3;
		    	if( te.getPos().getX() + 0.5D - vec2.x > 0)// || te.getPos().getX() + 0.5D - vec2.x == 0 )
		    	{
		    		vec3 = new Vec3d(vec1.x - i, vec1.y + y1 - y2, vec1.z + z1);
		    	}
		    	else if(te.getPos().getX() + 0.5D - vec2.x < 0)
		    	{
		    		vec3 = new Vec3d(vec1.x + i, vec1.y + y1 + y2, vec1.z - z1);
		    	}
		    	else if(te.getPos().getZ() + 0.5D - vec2.z > 0)
		    	{
		    		y2 = Math.sqrt((i*i)) * Math.tan(Math.toRadians(te.getYAngle()));
		    		vec3 = new Vec3d(vec1.x , vec1.y + y1 - y2, vec1.z - z1);
		    	}
		    	else
		    	{
		    		y2 = Math.sqrt((i*i)) * Math.tan(Math.toRadians(te.getYAngle()));
		    		vec3 = new Vec3d(vec1.x , vec1.y + y1 + y2, vec1.z - z1);
		    	}
		    	buffer.pos(vec3.x , vec3.y , vec3.z).color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha()).endVertex();
		    	
		    	if(!this.asd)
		    	{
		    		//System.out.println((vec3.x + te.getPos().getX() - x) + " " + (vec3.y + te.getPos().getY() - y) + " " + (vec3.z + te.getPos().getZ() - z) );
		    	}
		    }
		    
		    /*if(te.getZAngle() != 0)
		    {
		    	GlStateManager.translate(x  + 0.5D, 0, z  + 0.5D);
		    	GlStateManager.rotate((float) te.getZAngle(), 0, 1, 0);
				GlStateManager.translate(-x - 0.5D , 0, -z - 0.5D  );
		    }*/

		    this.asd = true;

		    /*if(te.getYAngle() != 0)
		    {
		    	GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);
				GlStateManager.rotate((float) te.getYAngle(), 0, 0, 1);
				GlStateManager.translate(-x - 0.5D , -y - 0.5D , -z - 0.5D);
		    }	    */
		    
		    tessellator.draw();
			GlStateManager.enableTexture2D();
			
			GlStateManager.popMatrix();
		}
		
		if(te.isAnyBlockRayTraced())
		{
			BlockPos tePos = te.rayTraceResult();
			GlStateManager.pushMatrix();
			GlStateManager.disableTexture2D();
			GlStateManager.glLineWidth(4.0F);
			GlStateManager.disableCull();
								
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tessellator.getBuffer();
			
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			
			Color c = new Color(255, 0, 0, 80);
			bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

			double addition = 1.02;
			double minus = 0.01;
			Vec3d posA = new Vec3d(x + (tePos.getX() - te.getPos().getX() - minus), y + (tePos.getY() - te.getPos().getY() - minus) , z +(tePos.getZ() - te.getPos().getZ()) - minus);
			Vec3d posB = posA.addVector(1, 1, 1);
			
			
			
			bufferBuilder.pos(posA.x, posA.y, posA.z + addition).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x, posA.y + addition, posA.z + addition).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x, posA.y + addition, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			
			bufferBuilder.pos(posA.x, posA.y + addition, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x + addition, posA.y + addition, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x + addition, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			
			bufferBuilder.pos(posA.x + addition, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x + addition, posA.y + addition, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x + addition, posA.y + addition, posA.z + addition).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x + addition, posA.y, posA.z + addition).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			
			bufferBuilder.pos(posA.x + addition, posA.y, posA.z + addition).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x, posA.y, posA.z + addition).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x, posA.y + addition, posA.z + addition).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x + addition, posA.y + addition, posA.z + addition).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			
			bufferBuilder.pos(posA.x, posA.y + addition, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x, posA.y + addition, posA.z + addition).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x + addition, posA.y + addition, posA.z + addition).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x + addition, posA.y + addition, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			
			bufferBuilder.pos(posA.x, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x, posA.y, posA.z + addition).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x + addition, posA.y, posA.z + addition).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			bufferBuilder.pos(posA.x + addition, posA.y, posA.z).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
			tessellator.draw();      
			
			GlStateManager.disableBlend();
			GlStateManager.enableCull();
			GlStateManager.enableTexture2D();
			GlStateManager.popMatrix();
		}
			
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}
	
	@Override
	public boolean isGlobalRenderer(TE_Connector te) {
		return true;
	}
	
}
