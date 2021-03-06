/*******************************************************************************
 * Copyright 2011-2014 by SirSengir
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 ******************************************************************************/
package forestry.arboriculture.worldgen;

import net.minecraft.init.Blocks;

import forestry.api.world.ITreeGenData;
import forestry.core.worldgen.BlockType;

public class WorldGenOak extends WorldGenTree {

	public WorldGenOak(ITreeGenData tree) {
		super(tree);
	}

	@Override
	public BlockType getWood() {
		return new BlockType(Blocks.log, 0);
	}

}
