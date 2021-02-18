package com.sn1pe2win.definitions;

public class DestinyBondDefinition extends StaticDefinition {

	public DestinyBondDefinition(long identifier) {
		super(ManifestTables.BondDefinition, identifier);
	}

	public long getUnlockHash() {
		return getRawJson().getAsJsonPrimitive("providedUnlockHash").getAsLong();
	}
	
	public int getUnlockHashValue() {
		return getRawJson().getAsJsonPrimitive("providedUnlockValueHash").getAsInt();
	}
}
