package com.sn1pe2win.definitions;

public class DestinyLoreDefinition extends StaticDefinition {

	public DestinyLoreDefinition(long identifier) {
		super(ManifestTables.LoreDefinition, identifier);
	}

	public String getSubtitle() {
		return getRawJson().getAsJsonPrimitive("subtitle").getAsString();
	}
}
