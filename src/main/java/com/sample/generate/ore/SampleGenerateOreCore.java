package com.sample.generate.ore;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;

@Mod(modid   = SampleGenerateOreCore.MODID, version = SampleGenerateOreCore.VERSION)
public class SampleGenerateOreCore
{
	public static final String MODID = "GenOre";
	public static final String VERSION = "0.0.0";

	public static Block blockOre;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		blockOre = (new BlockOre())
				.setBlockName("blockOre")
				.setBlockTextureName("ore:ore");

		GameRegistry.registerBlock(blockOre, "blockOre");

		/*
		 * IWorldGeneratorを実装したクラスのインスタンスを登録するメソッド.
		 * このメソッドに登録することでチャンク生成時に鉱石が追加生成されるようになる.
		 * 1.7から引数に重みが追加された. この重みは他のModで鉱石が追加されるときに,
		 * この重みがキーとなって生成順序がソートされる模様.
		 */
		GameRegistry.registerWorldGenerator(new OreGenerator(), 1);
	}
}
