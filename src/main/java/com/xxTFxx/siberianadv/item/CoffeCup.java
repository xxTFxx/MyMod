package com.xxTFxx.siberianadv.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;

public class CoffeCup extends ItemBase{

	private FoodStats food = new FoodStats();
	
	public CoffeCup(String name) {
		super(name);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		 playerIn.setActiveHand(handIn);
	     return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		EntityPlayer player = (EntityPlayer) entityLiving;
		player.addPotionEffect(new PotionEffect(MobEffects.HASTE , 2000));
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED , 2000));
		food = player.getFoodStats();
		food.setFoodLevel(food.getFoodLevel() + 2);
		stack.shrink(1);
		return stack;

	}
}

