package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyInventoryItemDefinition;

public class ExtendedPGCRWeapons extends DestinyEntity {
	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	public long getItemHash() {
		return object.getAsJsonPrimitive("referenceId").getAsLong();
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyInventoryItemDefinition> getItem() {
		return (Response<DestinyInventoryItemDefinition>) new DestinyInventoryItemDefinition(getItemHash()).getAsResponse();
	}
	
	public int getKills() {
		return object.getAsJsonObject("values").getAsJsonObject("uniqueWeaponKills").getAsJsonObject("basic").getAsJsonPrimitive("value").getAsInt();
	}
	
	public int getPrecisionKills() {
		return object.getAsJsonObject("values").getAsJsonObject("uniqueWeaponPrecisionKills").getAsJsonObject("basic").getAsJsonPrimitive("value").getAsInt();
	}
	
	public int getPrecisionKillsPercentage() {
		return object.getAsJsonObject("values").getAsJsonObject("uniqueWeaponKillsPrecisionKills").getAsJsonObject("basic").getAsJsonPrimitive("value").getAsInt();
	}
	
	public float getStatValue(String statName) {
		return object.getAsJsonObject("values").getAsJsonObject(statName).getAsJsonObject("basic").getAsJsonPrimitive("value").getAsFloat();
	}
	
	public String getStatDisplayValue(String statName) {
		return object.getAsJsonObject("values").getAsJsonObject(statName).getAsJsonObject("basic").getAsJsonPrimitive("displayValue").getAsString();
	}
}