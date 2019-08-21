package com.xxTFxx.siberianadv.render;

import java.awt.Color;

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

	@Override
	public void render(TE_Connector te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {

		if(te.isConnected())
		{
			BlockPos linkTo = te.getLinkTo();
			
			Vec3d vec1 = new Vec3d(x + 0.5D, y + 0.5D,  z + 0.5D);
			Vec3d vec2 = new Vec3d(linkTo.getX() + 0.5D , linkTo.getY() + 0.5D, linkTo.getZ() + 0.5D);
			
			GlStateManager.pushMatrix();
			
			
			
			GlStateManager.disableTexture2D();
			GlStateManager.glLineWidth(2.0F);

		    Tessellator tessellator = Tessellator.getInstance();
		    BufferBuilder buffer = tessellator.getBuffer();
		    buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
		    double lenght = Math.sqrt( (te.getPos().getX() + 0.5D - vec2.x) * (te.getPos().getX() + 0.5D - vec2.x) + (te.getPos().getY() + 0.5D - vec2.y) * (te.getPos().getY() + 0.5D - vec2.y)  + (te.getPos().getZ() + 0.5D - vec2.z) * (te.getPos().getZ() + 0.5D - vec2.z) );
		    
		    double a = 1 / (25 * Math.sqrt(lenght));
		    
		    for(double i = 0 ; i <= lenght + 0.1D ; i += 0.2D)
		    {
		    	double y1 = a * (i) * (i - lenght);
		    	Vec3d vec3;
		    	if( te.getPos().getX() + 0.5D - vec2.x > 0 || te.getPos().getX() + 0.5D - vec2.x == 0 )
		    	{
		    		vec3 = new Vec3d(vec1.x - i, vec1.y + y1, vec1.z);
		    	}
		    	else
		    	{
		    		vec3 = new Vec3d(vec1.x + i, vec1.y + y1, vec1.z);
		    	}
		    	buffer.pos(vec3.x , vec3.y , vec3.z).color(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), Color.BLACK.getAlpha()).endVertex();
		    }
		    if(te.getPos().getZ() != linkTo.getZ())
		    {
		    	double l_x = Math.sqrt( (te.getPos().getX() + 0.5D - vec2.x) * (te.getPos().getX() + 0.5D - vec2.x) );
		    	double l_z = Math.sqrt( (te.getPos().getZ() + 0.5D - vec2.z) * (te.getPos().getZ() + 0.5D - vec2.z) );
		    	double atan = Math.atan( l_z / l_x );
		    	double angle = Math.toDegrees(atan);
		    	if(te.getPos().getZ() + 0.5D - vec2.z > 0) angle = -angle;
		    	if(te.getPos().getX() + 0.5D - vec2.x < 0) angle = -angle;
		    	if(angle != 0)
		    	{
		    		GlStateManager.translate(x  + 0.5D, 0, z  + 0.5D);
				    GlStateManager.rotate((float) angle, 0, 1, 0);
				    GlStateManager.translate(-x - 0.5D , 0, -z - 0.5D  );
		    	}
		    }
		    
		    if(te.getPos().getY() != linkTo.getY())
		    {
		    	double l_x = te.getPos().getX() + 0.5D - vec2.x;
		    	double l_z = te.getPos().getZ() + 0.5D - vec2.z;
		    	double l_y = Math.sqrt( l_x * l_x + l_z * l_z);
		    	double atan = Math.atan( (te.getPos().getY() + 0.5D - vec2.y) / l_y );
		    	double angle = Math.toDegrees(atan);
		    	//if(Math.abs(te.getPos().getZ()) + 0.5D - Math.abs(vec2.z) > 0) angle = -angle;
		    	//else if(Math.abs(te.getPos().getX()) + 0.5D - Math.abs(vec2.x) < 0) angle = -angle;
		    	//else if(te.getPos().getZ() == linkTo.getZ()) angle = -angle;
		    	if(Math.abs(te.getPos().getX()) + 0.5D - Math.abs(vec2.x) < 0)
		    	{
		    		/*if(Math.abs(te.getPos().getZ()) + 0.5D - Math.abs(vec2.z) < 0)
		    		{
		    		}*/
		    		angle = -angle;
		    	}
		    	if(angle != 0)
		    	{
		    		GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);
				    GlStateManager.rotate((float) angle, 0, 0, 1);
				    GlStateManager.translate(-x - 0.5D , -y - 0.5D , -z - 0.5D);
		    	}
		    }
		    
		    
		    tessellator.draw();
			GlStateManager.enableTexture2D();
			
			GlStateManager.popMatrix();
		}
		
		
		
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
	}
	
	
}
