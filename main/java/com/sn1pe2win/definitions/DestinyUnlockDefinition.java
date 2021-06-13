package com.sn1pe2win.definitions;

public class DestinyUnlockDefinition extends StaticDefinition {

	public DestinyUnlockDefinition(long identifier) {
		super(ManifestTables.UnlockDefinition, identifier);
	}

	public int getUnlockType() {
		return getRawJson().getAsJsonPrimitive("unlockType").getAsInt();
	}
	
	public int getScope() {
		return getRawJson().getAsJsonPrimitive("scope").getAsInt();
	}
}
