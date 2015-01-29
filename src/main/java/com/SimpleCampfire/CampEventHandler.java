package com.SimpleCampfire;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CampEventHandler {

	@SubscribeEvent
	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		//Set the variables.
		World world = event.entityPlayer.worldObj;
		//If we're actually right-clicking
		if(event.action == Action.RIGHT_CLICK_BLOCK)
		{
			//Set more variables!
			ItemStack item =  event.entityPlayer.getHeldItem();
			Block block = event.world.getBlock(event.x, event.y, event.z);
			Block cobble = Blocks.cobblestone;
			Block fire = Main.campfireBlock;
			int xCoord = event.x;
			int yCoord = event.y;
			int zCoord = event.z;
			//Is it cobblestone? Can't make a fire without cobblestone.
			if(block == cobble)
			{
				//Make sure we've got enough sticks!
				if(item.getItem() == Items.stick && item.stackSize >= 4)
				{
					//Make the campfire, and take those sticks! You could poke your eye out with that thing.
					world.setBlock(xCoord, yCoord, zCoord, Main.campfireBlock);
					item.stackSize -= 4;
				}
			}
		}
	}
}
