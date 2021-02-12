package com.sn1pe2win.definitions;

public class DestinyGenderDefinition extends StaticDefinition {

	public DestinyGenderDefinition(long identifier) {
		super(ManifestTables.GenderDefinition, identifier);
	}

	public int getGenderType() {
		return getRawJson().getAsJsonPrimitive("genderType").getAsInt();
	}
}
