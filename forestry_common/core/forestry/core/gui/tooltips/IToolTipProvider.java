/*******************************************************************************
 * Copyright 2011-2014 by SirSengir
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 ******************************************************************************/
package forestry.core.gui.tooltips;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public interface IToolTipProvider {

	ToolTip getToolTip();

	boolean isToolTipVisible();

	boolean isMouseOver(int mouseX, int mouseY);
}
