package com.xxTFxx.siberianadv.recipes;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.xxTFxx.siberianadv.init.ItemInit;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GrinderRecipes {

	private static final GrinderRecipes GRINDER_BASE = new GrinderRecipes();
	private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Integer> amountList = Maps.<ItemStack , Integer>newHashMap();
	
	public static GrinderRecipes instance()
	{
		return GRINDER_BASE;
	}
	
	private GrinderRecipes() {
		this.addRecipeForBlock(Blocks.IRON_ORE, new ItemStack(ItemInit.IRON_DUST), 2);
	}
	
	public void addRecipe(Item input , ItemStack output , int amount)
	{
		this.addGrinderRecipe(new ItemStack(input), output , amount);
	}
	
	public void addRecipeForBlock(Block input, ItemStack output , int amount)
	{
		this.addGrinderRecipe(new ItemStack(Item.getItemFromBlock(input)), output , amount);
	}
	
	public void addGrinderRecipe(ItemStack input, ItemStack stack , int amount)
    {
        this.smeltingList.put(input, stack);
        this.amountList.put(input, amount);
    }
	
	 public ItemStack getResult(ItemStack stack)
	    {
	        for (Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet())
	        {
	            if (this.compareItemStacks(stack, entry.getKey()))
	            {
	                return entry.getValue();
	            }
	        }

	        return ItemStack.EMPTY;
	    }
	 
	 public int getResultAmount(ItemStack stack)
	 {
		 for(Entry<ItemStack, Integer> entry : this.amountList.entrySet())
		 {
			 if(this.compareItemStacks(stack, entry.getKey()))
			 {
				 return entry.getValue();
			 }
		 }
		 
		 return 0;
	 }
	 
	 private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	    {
	        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	    }
	
	
	
}
