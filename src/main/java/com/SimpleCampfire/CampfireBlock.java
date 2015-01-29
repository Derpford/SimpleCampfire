package com.SimpleCampfire;
//Extra thanks to Flenix for his custom rendering tutorial.

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CampfireBlock extends BlockContainer {

	TileEntity campTE;
	protected CampfireBlock(Material material) {
		super(material);
		//Set the important stuff
		this.setBlockName(Main.MODID + "_" + "blockCampfire");
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setHardness(2.0f);
		this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
		this.setLightLevel(1.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int beans) {
		// Standard TE setting.
		campTE = new CampfireTileEntity();
		return campTE;
	}
	
	@Override
    public int getRenderType() {
		//Can't render it properly unless this is set.
            return -1;
    }
	
    @Override
    public boolean isOpaqueCube() {
    	//It's not just a block.
            return false;
    }
    
    public boolean renderAsNormalBlock() {
    	//It definitely doesn't render normally.
            return false;
    }
    
    public void registerIcons(IIconRegister icon)
    {
    	//TODO: Make this an actual icon.
    	this.blockIcon = icon.registerIcon(Main.MODID+":textures/blocks/campfire.png");
    }
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int beans) {
    	//Tell the block to break.
    	//super.breakBlock(world, x, y, z, block, beans);
    	ItemStack[] drops = getDropsByArray(world, x, y, z, 0, 0);
    	if(!world.isRemote)
    	{
    		Main.doDrops(world, x, y, z, drops);
    	}
    	world.setBlockToAir(x, y, z);
    	//TODO: Make the campfire only drop items once.
    }
    
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
    		int metadata, int fortune) {
    	// TODO Auto-generated method stub
    	if(!world.isRemote)
    	{
    		ArrayList<ItemStack> drops =((CampfireTileEntity) campTE).getDrops();
    		if(drops != null)
    		{
    			return drops;
    		}
    		else
    		{
    			return super.getDrops(world, x, y, z, metadata, fortune);
    		}
    	}
    	else
    	{
    		return null;
    	}
    }	
    
    public ItemStack[] getDropsByArray(World world, int x, int y, int z,
        		int metadata, int fortune) {
        	// TODO Auto-generated method stub
        	ItemStack[] drops =((CampfireTileEntity) campTE).getDropsByArray();
        	return drops;
    }
}
