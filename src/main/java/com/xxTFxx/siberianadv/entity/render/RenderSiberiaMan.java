package com.xxTFxx.siberianadv.entity.render;

import com.xxTFxx.siberianadv.entity.EntitySiberiaMan;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderVindicator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.util.ResourceLocation;

public class RenderSiberiaMan extends RenderLiving<EntityVindicator>{

	public RenderSiberiaMan(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityVindicator entity) {
		// TODO Auto-generated method stub
		return null;
	}
	

	

	
}
