/*******************************************************************************
 * Copyright 2011-2014 by SirSengir
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 ******************************************************************************/
package forestry.apiculture.gadgets;

import forestry.api.apiculture.IAlvearyComponent;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;

public class TileAlvearyStabiliser extends TileAlveary implements IBeeModifier {

	public static final int BLOCK_META = 6;

	public TileAlvearyStabiliser() {
		super(BLOCK_META);
	}

	/* UPDATING */
	@Override
	public void initialize() {
		super.initialize();
		
		if(!hasMaster() || !isIntegratedIntoStructure())
			return;
		
		((IAlvearyComponent)getCentralTE()).registerBeeModifier(this);

	}
	
	@Override
	protected void updateServerSide() {
		super.updateServerSide();
		if(worldObj.getTotalWorldTime() % 200 != 0)
			return;
		
		if(!hasMaster() || !isIntegratedIntoStructure())
			return;
		
		((IAlvearyComponent)getCentralTE()).registerBeeModifier(this);
	}
	
	@Override
	public String getInventoryName() {
		return "tile.alveary.6";
	}

	@Override
	public boolean hasFunction() {
		return true;
	}

	/* TEXTURES */
	public int getIcon(int side, int metadata) {
		if(side == 0 || side == 1)
			return BlockAlveary.BOTTOM;

		return BlockAlveary.STABILISER;
	}

	/* IBEEMODIFIER */
	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		return 0.0f;
	}

	@Override
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
		return 1.0f;
	}

	@Override
	public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
		return 1.0f;
	}

	@Override
	public boolean isSealed() {
		return false;
	}

	@Override
	public boolean isSelfLighted() {
		return false;
	}

	@Override
	public boolean isSunlightSimulated() {
		return false;
	}

	@Override
	public boolean isHellish() {
		return false;
	}

}
