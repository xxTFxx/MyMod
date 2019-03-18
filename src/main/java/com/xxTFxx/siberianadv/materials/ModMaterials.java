package com.xxTFxx.siberianadv.materials;

import com.xxTFxx.siberianadv.Main;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ModMaterials {
	public static final ToolMaterial MOD_TOOL = EnumHelper.addToolMaterial(Main.MOD_ID + ":stalinium", 3, 1800, 9.0F, 4.0F, 15);
		
	public static final ArmorMaterial BEAR_MATERIAL = EnumHelper.addArmorMaterial("bear_material", Main.MOD_ID + ":bear_material", 14, new int[] {2,5,3,2}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);


}
