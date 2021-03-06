/*******************************************************************************
 * Copyright 2011-2014 by SirSengir
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 ******************************************************************************/
package forestry.mail.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import forestry.core.gui.ContainerForestry;
import forestry.core.gui.slots.SlotClosed;
import forestry.core.gui.slots.SlotCustom;
import forestry.mail.gadgets.MachinePhilatelist;
import forestry.mail.items.ItemStamps;

public class ContainerPhilatelist extends ContainerForestry {

	public ContainerPhilatelist(InventoryPlayer player, MachinePhilatelist tile) {
		super(tile);

		// Filter
		addSlot(new SlotCustom(tile, MachinePhilatelist.SLOT_FILTER, 80, 19, new Object[] { ItemStamps.class }));

		// Collected Stamps
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlot(new SlotClosed(tile, j + i * 9 + MachinePhilatelist.SLOT_BUFFER_1, 8 + j * 18, 46 + i * 18));

		// Player inventory
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlot(new Slot(player, j + i * 9 + 9, 8 + j * 18, 111 + i * 18));
		// Player hotbar
		for (int i = 0; i < 9; i++)
			addSlot(new Slot(player, i, 8 + i * 18, 169));

	}

}
