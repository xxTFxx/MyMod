package com.xxTFxx.siberianadv.item;

import com.xxTFxx.siberianadv.Main;
import com.xxTFxx.siberianadv.init.ItemInit;
import com.xxTFxx.siberianadv.models.ModelUshanka;
import com.xxTFxx.siberianadv.tabs.ModTab;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmourModel extends ItemArmor{

	//ModelUshanka model = new ModelUshanka();
	ModelBiped model;
	
	public ArmourModel(String name , ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlotIn , ModelBiped modelIn) {
		super(materialIn, 1, equipmentSlotIn);
		setUnlocalizedName(Main.MOD_ID + "." + name);
		setRegistryName(name);
		setCreativeTab(ModTab.Mod_Tab);
		setMaxStackSize(1);
		this.model = modelIn;
		ItemInit.items.add(this);
	}

	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot,
			ModelBiped _default) {
		return this.model;
	}
	
}
