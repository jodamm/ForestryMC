/*******************************************************************************
 * Copyright 2011-2014 by SirSengir
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 ******************************************************************************/
package forestry.factory.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class InventoryCraftingAuto extends InventoryCrafting {

	private ContainerCarpenter eventHandler;
	public ItemStack stackList[];
	private int inventoryWidth;

	public InventoryCraftingAuto(ContainerCarpenter container, int i, int j) {
		super(container, i, j);
		int k = i * j;
		stackList = new ItemStack[k];
		eventHandler = container;
		inventoryWidth = i;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i >= getSizeInventory())
			return null;
		else
			return stackList[i];
	}

	@Override
	public ItemStack getStackInRowAndColumn(int i, int j) {
		if (i < 0 || i >= inventoryWidth)
			return null;
		else {
			int k = i + j * inventoryWidth;
			return getStackInSlot(k);
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (stackList[i] != null) {
			if (stackList[i].stackSize <= j) {
				ItemStack itemstack = stackList[i];
				stackList[i] = null;
				eventHandler.onCraftMatrixChanged(this, i);
				return itemstack;
			}
			ItemStack itemstack1 = stackList[i].splitStack(j);
			if (stackList[i].stackSize == 0)
				stackList[i] = null;
			eventHandler.onCraftMatrixChanged(this, i);
			return itemstack1;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		stackList[i] = itemstack;
		eventHandler.onCraftMatrixChanged(this, i);
	}

	@Override
	public String getInventoryName() {
		return "Crafting";
	}

	@Override
	public int getSizeInventory() {
		return stackList.length;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

}
