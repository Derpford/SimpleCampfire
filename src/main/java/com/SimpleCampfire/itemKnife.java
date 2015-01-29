package com.SimpleCampfire;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class itemKnife extends ItemShears {

	protected itemKnife(float beans, ToolMaterial toolMaterial,
			Set aSet) {
		super();
		this.setMaxDamage(toolMaterial.getMaxUses());
		this.setCreativeTab(CreativeTabs.tabTools);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side, float hitX, float hitY,
			float hitZ) {
		// Check to see if this is a campfire...
		Block target = world.getBlock(x, y, z);
		if(target == Main.campfireBlock)
		{
			//Break the campfire.
			if(!world.isRemote)
			{
				//world.setBlockToAir(x, y, z);
				target.breakBlock(world, x, y, z, target, 0);
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}
