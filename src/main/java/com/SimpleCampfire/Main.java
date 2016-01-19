package com.SimpleCampfire;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main
{
    public static final String MODID = "simplecampfire";
    public static final String VERSION = "0.1";
    @SidedProxy(clientSide="com.SimpleCampfire.client.SimpleCampfireClientProxy", serverSide="com.simplecampfire.SimpleCampfireCommonProxy")
    public static SimpleCampfireCommonProxy proxy;
    public static Block campfireBlock;
    public static Item knifeWooden;
    public static Item knifeStone;
    public static Item knifeIron;
    public static Item knifeGold;
    public static Item knifeDiamond;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		//Add the render stuff
        proxy.registerRenderThings();
        //Add TEs
        GameRegistry.registerTileEntity(CampfireTileEntity.class, "tileEntityCampfire");
        //Add some blocks and items
        campfireBlock = new CampfireBlock(Material.rock);
        knifeWooden = new itemKnife(0, ToolMaterial.WOOD, null).setUnlocalizedName("woodenKnife");
        GameRegistry.registerItem(knifeWooden, "woodenKnife");
        GameRegistry.registerBlock(campfireBlock, "blockCampfire");
        //Register the event handler
        MinecraftForge.EVENT_BUS.register(new CampEventHandler());
        //OreDict the knives
        OreDictionary.registerOre("knife", knifeWooden);
        //OreDictionary.registerOre("knife", knifeStone);
        //OreDictionary.registerOre("knife", knifeIron);
        //OreDictionary.registerOre("knife", knifeGold);
        //OreDictionary.registerOre("knife", knifeDiamond);
        
    }
    
    public static boolean doDrops(World world, int x, int y, int z, ItemStack[] drops)
    {
    	if(world.isRemote)//Only on the server side!
    	{
    		return false;
    	}
    	else if(!world.isRemote)
    	{
    		for(int i = 0; i < drops.length; i++)
    			{
    				if(drops[i] != null)
    				{
    					System.out.print(drops[i].getDisplayName() + ":");
    					System.out.print(drops[i].stackSize + "\n");
    					Entity dropItem = new EntityItem(world, x, y, z, drops[i]);
    					world.spawnEntityInWorld(dropItem);
    				}
    			}
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
}
