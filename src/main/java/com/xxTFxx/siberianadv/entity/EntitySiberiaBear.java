package com.xxTFxx.siberianadv.entity;

import com.google.common.base.Predicate;
import com.xxTFxx.siberianadv.util.handlers.LootTableHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntitySiberiaBear extends EntityPolarBear{

	public EntitySiberiaBear(World worldIn) {
		super(worldIn);

	}
	
	 protected void applyEntityAttributes()
	    {
	        super.applyEntityAttributes();
	        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
	    }
	 
	 @Override
	 protected void initEntityAI()
	    {
	        super.initEntityAI();
	        this.targetTasks.taskEntries.clear();
	        targetTasks.addTask(0, new EntityAINearestAttackableTarget(this , EntityPlayer.class , true));
	    }
	 
	 @Override
	protected ResourceLocation getLootTable() {
		return LootTableHandler.SIBERIAN_BEAR;
	}
	
}
