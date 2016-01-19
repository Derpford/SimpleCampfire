package com.SimpleCampfire;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class CampfireTileEntity extends TileEntity {

	Tessellator tess = new Tessellator();
	int particleTimer = 0;
	int fuelTimer = 0;
	int starterSticks = 4;
	ItemStack[] fuelItems = new ItemStack[3];
	ItemStack[] byproduct = new ItemStack[1];
	ItemStack[] result = new ItemStack[1];
	ItemStack[] input = new ItemStack[1];
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		// Standard NBT.
		super.readFromNBT(tag);
	}
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		// Nothing new here.
		super.writeToNBT(tag);
	}
	
	ArrayList<ItemStack> getDrops()
	{
		Item cobbleItem = Blocks.cobblestone.getItem(worldObj, xCoord, yCoord, zCoord);
		ItemStack cobbleStack = new ItemStack(cobbleItem);
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		ItemStack stickStack = new ItemStack(Items.stick, this.starterSticks);
		for(int i = 0; i < fuelItems.length; i++)
		{
			drops.add(fuelItems[i]);
		}
		for(int i = 0; i < byproduct.length; i++)
		{
			drops.add(byproduct[i]);
		}
		for(int i = 0; i < result.length; i++)
		{
			drops.add(result[i]);
		}
		for(int i = 0; i < input.length; i++)
		{
			drops.add(input[i]);
		}
		drops.add(cobbleStack);
		drops.add(stickStack);
		return drops;
	}
	
	ItemStack[] getDropsByArray()
	{
		int j = 0;
		World world = worldObj;
		Item cobbleItem = Blocks.cobblestone.getItem(worldObj, xCoord, yCoord, zCoord);
		ItemStack cobbleStack = new ItemStack(cobbleItem);
		ItemStack[] drops = new ItemStack[8];
		ItemStack stickStack = new ItemStack(Items.stick, this.starterSticks);
		if(!world.isRemote)
		{
			for(int i = 0; i < fuelItems.length; i++)
			{
				drops[j] = fuelItems[i];
				j++;
			}
			for(int i = 0; i < byproduct.length; i++)
			{
				drops[j] = byproduct[i];
				j++;
			}
			for(int i = 0; i < result.length; i++)
			{
				drops[j] = result[i];
				j++;
			}
			for(int i = 0; i < input.length; i++)
			{
				drops[j] = input[i];
				j++;
			}
			drops[j] = cobbleStack;
			j++;
			drops[j] = stickStack;
			j++;
		}
		return drops;
	}
	
	void particleStuff(int status, World world)
	{
		//Pick some velocities.
		float xVel=(float) ((Math.random()-0.5f)*0.05f);
		float zVel=(float) ((Math.random()-0.5f)*0.05f);
		/* Some handy definitions.
		int stateHostileNear=1;
		int stateNormal=2;
		int stateNoFuel=3;
		*/
		switch(status)
		{
		//If there's a baddie nearby...
		case 1:
			//Make some blue particles. Bilbo found this campfire in a troll cave.
			world.spawnParticle("reddust", this.xCoord+0.5f, this.yCoord+Math.random(), this.zCoord+0.5f, -1.0f, 1.0f, 2.0f);
			break;
			
		//If we're just dealing with the usual pigs...
		case 2:
			//Do some flames and smoke.
			world.spawnParticle("flame", this.xCoord+0.5f, this.yCoord+0.1f, this.zCoord+0.5f, xVel, Math.random()*0.1f, zVel);
			if(particleTimer > 4)
			{
				xVel = (float) ((Math.random()-0.5)*0.01f);
			zVel = (float) ((Math.random()-0.5)*0.01f);
			world.spawnParticle("largesmoke", this.xCoord+0.5f, this.yCoord+0.2f, this.zCoord+0.5f, xVel, Math.random()*0.1f, zVel);
			particleTimer = 0;
			}
			else
			{
				particleTimer++;
			}
			break;
		
		//If we're out of fuel...
		case 3:
			//Do nothing.
			break;
			
		//If we don't have any info...
		default:
			//Do nothing.
			break;
		}
	}
	

	
	@Override
	public void updateEntity()
	{
		//Set up some variables.
		World world = worldObj;
		int status = 2;
		//Make a bounding box, and grab some entities from it.
		AxisAlignedBB bounds = AxisAlignedBB.getBoundingBox(this.xCoord-16, this.yCoord, this.zCoord-16, this.xCoord+16, this.yCoord+16, this.zCoord+16);
		@SuppressWarnings("unchecked") //I'm pretty sure I'm putting the right variables in here. If I'm not, then...well, my bad.
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(null, bounds);
		
		//Go through the list of entities...
		for(int i = 0; i < entities.size(); i++)
		{
			//If it's got something in it...
			if(entities.size() > 0)
			{
				//And it's got a monster in it...
				if(entities.get(i) instanceof IMob)
				{
					//Set the status.
					status = 1;
				}
				//Otherwise...
				else
				{
					//Set the status to something else. I'll add something to set the status to noFuel later.
					status = 2;
				}
			}
		}
		
		//Do our particle stuff.
		particleStuff(status, world);
		
		//TODO: Make the furnace loop.
	}
}