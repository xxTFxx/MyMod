package com.xxTFxx.siberianadv.recipes;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.xxTFxx.siberianadv.init.ItemInit;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PressRecipes {
	private static final PressRecipes PRESS_BASE = new PressRecipes();
	private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Integer> outputAmountList = Maps.<ItemStack , Integer>newHashMap();
	private final Map<ItemStack, Integer> inputAmountList = Maps.<ItemStack , Integer>newHashMap();
	
	public static PressRecipes instance()
	{
		return PRESS_BASE;
	}
	
	private PressRecipes() {
		this.addRecipe(ItemInit.SAWDUST, new ItemStack(ItemInit.PELLET), 8 , 1);
	}
	
	public void addRecipe(Item input , ItemStack output , int inputAmount , int outputAmount)
	{
		this.addGrinderRecipe(new ItemStack(input), output , inputAmount , outputAmount);
	}
	
	public void addRecipeForBlock(Block input, ItemStack output , int inputAmount , int outputAmount)
	{
		this.addGrinderRecipe(new ItemStack(Item.getItemFromBlock(input)), output , inputAmount , outputAmount);
	}
	
	public void addGrinderRecipe(ItemStack input, ItemStack stack , int inputAmount , int outputAmount)
    {
        this.smeltingList.put(input, stack);
        this.outputAmountList.put(input, outputAmount);
        this.inputAmountList.put(input, inputAmount);
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
		 for(Entry<ItemStack, Integer> entry : this.outputAmountList.entrySet())
		 {
			 if(this.compareItemStacks(stack, entry.getKey()))
			 {
				 return entry.getValue();
			 }
		 }
		 
		 return 0;
	 }
	 
	 public int getInputAmount(ItemStack stack)
	 {
		 for(Entry<ItemStack, Integer> entry : this.inputAmountList.entrySet())
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
