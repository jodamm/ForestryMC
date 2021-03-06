/*******************************************************************************
 * Copyright 2011-2014 by SirSengir
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 ******************************************************************************/
package forestry.mail;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import com.mojang.authlib.GameProfile;

import forestry.api.mail.EnumPostage;
import forestry.api.mail.ILetter;
import forestry.api.mail.IPostOffice;
import forestry.api.mail.IPostalCarrier;
import forestry.api.mail.IPostalState;
import forestry.api.mail.IStamps;
import forestry.api.mail.ITradeStation;
import forestry.api.mail.MailAddress;
import forestry.api.mail.PostManager;
import forestry.core.config.ForestryItem;

public class PostOffice extends WorldSavedData implements IPostOffice {

	// / CONSTANTS
	public static final String SAVE_NAME = "ForestryMail";
	private final int[] collectedPostage = new int[EnumPostage.values().length];

	// / CONSTRUCTOR
	public PostOffice(String s) {
		super(s);
	}

	public PostOffice() {
		super(SAVE_NAME);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		for (int i = 0; i < collectedPostage.length; i++) {
			if (nbttagcompound.hasKey("CPS" + i))
				collectedPostage[i] = nbttagcompound.getInteger("CPS" + i);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		for (int i = 0; i < collectedPostage.length; i++) {
			nbttagcompound.setInteger("CPS" + i, collectedPostage[i]);
		}
	}

	/* TRADE STATION MANAGMENT */
	private LinkedHashMap<GameProfile, ITradeStation> activeTradeStations;

	@Override
	public LinkedHashMap<GameProfile, ITradeStation> getActiveTradeStations(World world) {
		if (activeTradeStations == null)
			refreshActiveTradeStations(world);

		return this.activeTradeStations;
	}

	private void refreshActiveTradeStations(World world) {
		activeTradeStations = new LinkedHashMap<GameProfile, ITradeStation>();
		if (world == null || world.getSaveHandler() == null)
			return;
		File worldSave = world.getSaveHandler().getMapFileFromName("dummy");
		if (worldSave == null)
			return;
		File file = worldSave.getParentFile();
		if (!file.exists() || !file.isDirectory())
			return;

		for (String str : file.list()) {
			if (!str.startsWith(TradeStation.SAVE_NAME))
				continue;
			if (!str.endsWith(".dat"))
				continue;

			String[] parts = str.replace(TradeStation.SAVE_NAME, "").replace(".dat", "").split("_");
			if (parts.length != 2) continue;

			ITradeStation trade = PostManager.postRegistry.getTradeStation(world, new GameProfile(UUID.fromString(parts[0]), parts[1]));
			if (trade == null)
				continue;

			registerTradeStation(trade);
		}
	}

	@Override
	public void registerTradeStation(ITradeStation trade) {
		if (activeTradeStations == null)
			return;

		if (!activeTradeStations.containsKey(trade.getMoniker()))
			activeTradeStations.put(trade.getMoniker(), trade);

	}

	@Override
	public void deregisterTradeStation(ITradeStation trade) {
		if (activeTradeStations == null)
			return;

		activeTradeStations.remove(trade.getMoniker());
	}

	// / STAMP MANAGMENT
	@Override
	public ItemStack getAnyStamp(int max) {
		return getAnyStamp(EnumPostage.values(), max);
	}

	@Override
	public ItemStack getAnyStamp(EnumPostage postage, int max) {
		return getAnyStamp(new EnumPostage[] { postage }, max);
	}

	@Override
	public ItemStack getAnyStamp(EnumPostage[] postages, int max) {

		for (EnumPostage postage : postages) {

			int collected = 0;
			if (collectedPostage[postage.ordinal()] <= 0)
				continue;

			if (max >= collectedPostage[postage.ordinal()]) {
				collected = collectedPostage[postage.ordinal()];
				collectedPostage[postage.ordinal()] = 0;
			} else {
				collected = max;
				collectedPostage[postage.ordinal()] -= max;
			}

			if (collected > 0)
				return ForestryItem.stamps.getItemStack(collected, postage.ordinal() - 1);
		}

		return null;
	}

	// / DELIVERY
	@Override
	public IPostalState lodgeLetter(World world, ItemStack itemstack, boolean doLodge) {
		ILetter letter = PostManager.postRegistry.getLetter(itemstack);

		if (letter.isProcessed())
			return EnumDeliveryState.ALREADY_MAILED;

		if (!letter.isPostPaid())
			return EnumDeliveryState.NOT_POSTPAID;

		if (!letter.isMailable())
			return EnumDeliveryState.NOT_MAILABLE;

		IPostalState state = EnumDeliveryState.NOT_MAILABLE;
		for (MailAddress address : letter.getRecipients()) {
			IPostalCarrier carrier = PostManager.postRegistry.getCarrier(address.getType());
			if (carrier == null)
				continue;
			state = carrier.deliverLetter(world, this, address.getProfile(), itemstack, doLodge);
			if (!state.isOk())
				break;
		}
		/*
		 for (MailAddress address : letter.getRecipients())
		 if (address.isPlayer())
		 state = storeInPOBox(world, address, itemstack, doLodge);
		 else if (address.getType().equals(EnumAddressee.TRADER.toString()))
		 state = handleTradeLetter(world, address, itemstack, doLodge);
		 */

		if (!state.isOk())
			return state;

		collectPostage(letter.getPostage());

		markDirty();
		return EnumDeliveryState.OK;

	}

	@Override
	public void collectPostage(ItemStack[] stamps) {
		for (ItemStack stamp : stamps) {
			if (stamp == null)
				continue;

			if (stamp.getItem() instanceof IStamps) {
				EnumPostage postage = ((IStamps) stamp.getItem()).getPostage(stamp);
				collectedPostage[postage.ordinal()] += stamp.stackSize;
			}
		}
	}
}
