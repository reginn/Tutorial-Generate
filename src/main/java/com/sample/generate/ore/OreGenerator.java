package com.sample.generate.ore;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

/*
 * IWorldGeneratorインタフェースを実装する.
 */
public class OreGenerator implements IWorldGenerator
{
	/*
	 * IWorldGeneratorでオーバーライドするメソッド.
	 * このメソッドの中で生成処理を行う.
	 * 引数は(Random, chunkのX座標, chunkのZ座標, world, 現在のChunkのChunkProvider, これから生成するChunkのChunkProvider
	 */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		/*
		 * chunkXとchunkZはチャンクの座標なので, 4bit左シフト=16倍してブロックの座標に変換する.
		 * worldProviderをチェックして, 地上, ネザー, エンドにそれぞれ同じ鉱石を生成する.
		 */
		if (world.provider instanceof WorldProviderSurface)
		{
			this.generateOre(world, random, chunkX << 4, chunkZ << 4);
		}

		/*
		 * ネザーかどうかの判定
		 * if (world.provider.dimensionId == -1)でもよい
		 */
		if (world.provider instanceof WorldProviderHell)
		{
			this.generateOreHell(world, random, chunkX << 4, chunkZ << 4);
		}

		/*
		 * エンドかどうかの判定
		 * if (world.provider.dimensionId == 1)でもよい
		 */
		if (world.provider instanceof WorldProviderEnd)
		{
			this.generateOreEnd(world, random, chunkX << 4, chunkZ << 4);
		}

		/*
		 * 他のModで追加されるディメンションでも生成したい場合, 条件をNetherとEnd以外なら, にする.
		 * if (!world.provider.dimensionId == -1 && !world.provider.dimensionId == 1) {
		 *     generateOre(world, random, chunkX << 4, chunkZ << 4);
		 * }
		 * ただし, 追加されるディメンションで, 置換範囲内に置換対象のブロックがある場合に, 鉱石が置換される処理だという点に注意.
		 */
	}

	/*
	 * 鉱石を生成するメソッド
	 */
	private void generateOre(World world, Random random, int x, int z)
	{
		/*
		 * 1Chunkでどれだけ生成するか, 今回は30回試行する.
		 */
		for (int i = 0; i < 30; ++i)
		{
			/*
			 * 鉱石を生成する座標を決める.
			 * x, zは+0~+15の間, yは10~59(10+49)の間にランダムで決まる.
			 */
			int genX =  x + random.nextInt(16);
			int genY = 10 + random.nextInt(50);
			int genZ =  z + random.nextInt(16);

			/*
			 * 鉱石を生成するメソッド.
			 * WorldGenMinableのコンストラクタの引数は(ブロックID, まとめて生成される最大数).
			 * generateの引数は(world, random, x, y, z).
			 * 通常は石ブロックが鉱石と置換される.
			 */
			(new WorldGenMinable(SampleGenerateOreCore.blockOre, 4)).generate(world, random, genX, genY, genZ);
		}
	}

	private void generateOreHell(World world, Random random, int x, int z)
	{
		for (int i = 0; i < 30; ++i)
		{
			int genX =  x + random.nextInt(16);
			int genY = 10 + random.nextInt(50);
			int genZ =  z + random.nextInt(16);

			/*
			 * 鉱石を生成するメソッド.
			 * WorldGenMinableのコンストラクタの引数は(ブロックID, メタデータ, まとめて生成される最大数, 置換対象のブロックID).
			 * generateの引数は(world, random, x, y, z).
			 * Netherなので石ブロックではなくネザーラックと置換するようにする.
			 */
			(new WorldGenMinable(SampleGenerateOreCore.blockOre, 0, 4, Blocks.netherrack)).generate(world, random, genX, genY, genZ);
		}
	}

	private void generateOreEnd(World world, Random random, int x, int z)
	{
		for (int i = 0; i < 30; ++i)
		{
			int genX =  x + random.nextInt(16);
			int genY = 10 + random.nextInt(50);
			int genZ =  z + random.nextInt(16);

			/*
			 * 鉱石を生成するメソッド.
			 * WorldGenMinableのコンストラクタの引数は(ブロックID, メタデータ, まとめて生成される最大数, 置換対象のブロックID).
			 * generateの引数は(world, random, x, y, z).
			 * Endなので石ブロックではなくエンドストーンと置換するようにする.
			 */
			(new WorldGenMinable(SampleGenerateOreCore.blockOre, 0, 4, Blocks.end_stone)).generate(world, random, genX, genY, genZ);
		}
	}
}
