/*******************************************************************************
 * Copyright 2011-2014 by SirSengir
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/.
 ******************************************************************************/
package forestry.core.genetics;


import java.util.ArrayList;
import java.util.Collection;

import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IMutation;

public abstract class Mutation implements IMutation {

	protected int chance;
	boolean isSecret = false;

	protected IAllele allele0;
	protected IAllele allele1;

	IAllele[] template;

	protected float minTemperature = 0.0f;
	protected float maxTemperature = 2.0f;
	protected float minRainfall = 0.0f;
	protected float maxRainfall = 2.0f;

	protected ArrayList<String> specialConditions = new ArrayList<String>();

	public Mutation(IAllele allele0, IAllele allele1, IAllele[] template, int chance) {
		this.allele0 = allele0;
		this.allele1 = allele1;
		this.template = template;
		this.chance = chance;
	}
	
	
	public Mutation setSpecialConditions(Collection<String> conditions) {
		specialConditions.addAll(conditions);
		return this;
	}
	
	public Collection<String> getSpecialConditions() {
		return specialConditions;
	}

	public Mutation setIsSecret() {
		isSecret = true;
		return this;
	}

	public Mutation setTemperature(float minTemperature, float maxTemperature) {
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		
		EnumTemperature temp1 = EnumTemperature.getFromValue(minTemperature);
		EnumTemperature temp2 = EnumTemperature.getFromValue(maxTemperature);
		if(temp1 != temp2)
			specialConditions.add(String.format("Temperature between %s and %s.", temp1, temp2));
		else
			specialConditions.add(String.format("Temperature %s required.", temp1));
		return this;
	}

	public Mutation setRainfall(float minRainfall, float maxRainfall) {
		this.minRainfall = minRainfall;
		this.maxRainfall = maxRainfall;
		
		EnumHumidity temp1 = EnumHumidity.getFromValue(minRainfall);
		EnumHumidity temp2 = EnumHumidity.getFromValue(maxRainfall);

		if(temp1 != temp2)
			specialConditions.add(String.format("Humidity between %s and %s.", temp1, temp2));
		else
			specialConditions.add(String.format("Humidity %s required.", temp1));
		return this;
	}

	public Mutation setTemperatureRainfall(float minTemperature, float maxTemperature, float minRainfall, float maxRainfall) {
		setTemperature(minTemperature, maxTemperature);
		setRainfall(minRainfall, maxRainfall);
		return this;
	}

	@Override
	public IAllele getAllele0() {
		return allele0;
	}

	@Override
	public IAllele getAllele1() {
		return allele1;
	}

	@Override
	public float getBaseChance() {
		return chance;
	}

	@Override
	public IAllele[] getTemplate() {
		return template;
	}

	@Override
	public boolean isPartner(IAllele allele) {
		return allele0.getUID().equals(allele.getUID()) || allele1.getUID().equals(allele.getUID());
	}

	@Override
	public IAllele getPartner(IAllele allele) {
		if (allele0.getUID().equals(allele.getUID()))
			return allele1;
		else
			return allele0;
	}

	@Override
	public boolean isSecret() {
		return isSecret;
	}

}
