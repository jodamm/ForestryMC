/*******************************************************************************
 * Copyright 2011-2014 by SirSengir
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 ******************************************************************************/
package forestry.energy.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

import forestry.core.gui.ContainerForestry;
import forestry.core.gui.slots.SlotClosed;
import forestry.energy.gadgets.EngineCopper;

public class ContainerEngineCopper extends ContainerForestry {
	protected EngineCopper engine;

	public ContainerEngineCopper(InventoryPlayer player, EngineCopper tile) {
		super(tile);

		this.engine = tile;
		this.addSlot(new Slot(tile, 0, 44, 46));

		this.addSlot(new SlotClosed(tile, 1, 98, 35));
		this.addSlot(new SlotClosed(tile, 2, 98, 53));
		this.addSlot(new SlotClosed(tile, 3, 116, 35));
		this.addSlot(new SlotClosed(tile, 4, 116, 53));

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				this.addSlot(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int i = 0; i < 9; ++i)
			this.addSlot(new Slot(player, i, 8 + i * 18, 142));

	}

	@Override
	public void updateProgressBar(int i, int j) {
		if (engine != null)
			engine.getGUINetworkData(i, j);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++)
			engine.sendGUINetworkData(this, (ICrafting) crafters.get(i));
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return engine.isUseableByPlayer(entityplayer);
	}
}
