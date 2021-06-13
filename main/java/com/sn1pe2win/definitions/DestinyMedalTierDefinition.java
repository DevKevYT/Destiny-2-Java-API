package com.sn1pe2win.definitions;

public class DestinyMedalTierDefinition extends StaticDefinition {

	public DestinyMedalTierDefinition(long identifier) {
		super(ManifestTables.MedalTierDefinition, identifier);
	}

	public String getTierName() {
		return getRawJson().getAsJsonPrimitive("tierName").getAsString();
	}
	
	public int getOrder() {
		return getRawJson().getAsJsonPrimitive("order").getAsInt();
	}
}
