package com.xxTFxx.siberianadv.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelUshanka extends ModelBiped {
	public ModelRenderer top;
    public ModelRenderer r1;
    public ModelRenderer l1;
    public ModelRenderer back;
    public ModelRenderer l2;
    public ModelRenderer r2;
    public ModelRenderer r3;
    public ModelRenderer l3;

    public ModelUshanka() {
    	super(0, 0.0F, 128, 128);
    	this.textureWidth = 128;
        this.textureHeight = 128;
        this.l1 = new ModelRenderer(this, 88, 13);
        this.l1.setRotationPoint(-4.0F, -8.0F, -4.0F);
        this.l1.addBox(8.0F, 0.0F, 0.0F, 1, 4, 9, 0.0F);
        this.r2 = new ModelRenderer(this, 108, 37);
        this.r2.setRotationPoint(-4.0F, -8.0F, -4.0F);
        this.r2.addBox(-1.0F, 4.0F, 1.0F, 1, 2, 8, 0.0F);
        this.top = new ModelRenderer(this, 88, 0);
        this.top.setRotationPoint(-4.0F, -8.0F, -4.0F);
        this.top.addBox(-1.0F, -3.0F, -1.0F, 10, 3, 10, 0.0F);
        this.r1 = new ModelRenderer(this, 108, 13);
        this.r1.setRotationPoint(-4.0F, -8.0F, -4.0F);
        this.r1.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 9, 0.0F);
        this.r3 = new ModelRenderer(this, 120, 2);
        this.r3.setRotationPoint(-4.0F, -8.0F, -4.0F);
        this.r3.addBox(-1.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
        this.back = new ModelRenderer(this, 90, 29);
        this.back.setRotationPoint(-4.0F, -8.0F, -4.0F);
        this.back.addBox(0.0F, 0.0F, 8.0F, 8, 6, 1, 0.0F);
        this.l3 = new ModelRenderer(this, 120, 7);
        this.l3.setRotationPoint(-4.0F, -8.0F, -4.0F);
        this.l3.addBox(8.0F, 0.0F, -1.0F, 1, 2, 1, 0.0F);
        this.l2 = new ModelRenderer(this, 109, 26);
        this.l2.setRotationPoint(-4.0F, -8.0F, -4.0F);
        this.l2.addBox(8.0F, 4.0F, 1.0F, 1, 2, 8, 0.0F);
        
        
        this.bipedHead.addChild(r2);
        this.bipedHead.addChild(r1);
        this.bipedHead.addChild(l1);
        this.bipedHead.addChild(l2);
        this.bipedHead.addChild(l3);
        this.bipedHead.addChild(r3);
        this.bipedHead.addChild(top);
        this.bipedHead.addChild(back);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        super.render(entity, f, f1, f2, f3, f4, f5);
        
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
