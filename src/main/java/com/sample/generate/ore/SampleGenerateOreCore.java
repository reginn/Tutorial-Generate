package com.sample.generate.ore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(
	modid   = "GenOre",
	name    = "GenOre",
	version = "0.0.0"
)
public class SampleGenerateOreCore {

	public static Block blockOre;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

		blockOre = (new Block(4007, Material.rock))
				.setUnlocalizedName("blockOre")
				.setTextureName("ore:ore");

		GameRegistry.registerBlock(blockOre, "blockOre");

		LanguageRegistry.addName(blockOre, "Sample Ore Block");

		/*
		 * IWorldGeneratorを実装したクラスのインスタンスを登録するメソッド.
		 * このメソッドに登録することでチャンク生成時に鉱石が追加生成されるようになる.
		 */
		GameRegistry.registerWorldGenerator(new OreGenerator());
	}
}
