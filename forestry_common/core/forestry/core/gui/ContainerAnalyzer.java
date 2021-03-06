/*******************************************************************************
 * Copyright 2011-2014 by SirSengir
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 ******************************************************************************/
package forestry.core.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

import forestry.core.gadgets.TileAnalyzer;
import forestry.core.genetics.ItemGE;
import forestry.core.gui.slots.SlotCustom;
import forestry.core.gui.slots.SlotLiquidContainer;

public class ContainerAnalyzer extends ContainerLiquidTanks {

	private TileAnalyzer tile;

	public ContainerAnalyzer(InventoryPlayer player, TileAnalyzer tile) {
		super(tile, tile);

		this.tile = tile;

		// Input buffer
		for (int i = 0; i < 3; i++)
			for (int k = 0; k < 2; k++)
				addSlot(new SlotCustom(tile, TileAnalyzer.SLOT_INPUT_1 + i * 2 + k, 8 + k * 18, 28 + i * 18, new Object[] { ItemGE.class }));

		// Analyze slot
		addSlot(new SlotCustom(tile, TileAnalyzer.SLOT_ANALYZE, 73, 59, new Object[] {}));

		// Can slot
		addSlot(new SlotLiquidContainer(tile, TileAnalyzer.SLOT_CAN, 143, 24));

		// Output buffer
		for (int i = 0; i < 2; i++)
			for (int k = 0; k < 2; k++)
				addSlot(new SlotCustom(tile, TileAnalyzer.SLOT_OUTPUT_1 + i * 2 + k, 134 + k * 18, 48 + i * 18, new Object[] { ItemGE.class }));

		// Player inventory
		for (int i1 = 0; i1 < 3; i1++)
			for (int l1 = 0; l1 < 9; l1++)
				addSlot(new Slot(player, l1 + i1 * 9 + 9, 8 + l1 * 18, 94 + i1 * 18));
		// Player hotbar
		for (int j1 = 0; j1 < 9; j1++)
			addSlot(new Slot(player, j1, 8 + j1 * 18, 152));

	}

	@Override
	public void updateProgressBar(int i, int j) {
		tile.getGUINetworkData(i, j);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++)
			tile.sendGUINetworkData(this, (ICrafting) crafters.get(i));
	}

}
