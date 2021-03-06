/*******************************************************************************
 * Copyright 2011-2014 by SirSengir
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 ******************************************************************************/
package forestry.mail;

import java.util.Locale;

import forestry.api.mail.IPostalState;

public enum EnumDeliveryState implements IPostalState {
	OK, NO_MAILBOX, NOT_MAILABLE, ALREADY_MAILED, NOT_POSTPAID, MAILBOX_FULL, RESPONSE_NOT_MAILABLE;

	@Override
	public boolean isOk() {
		return this == OK;
	}

	@Override
	public String getIdentifier() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}
}
