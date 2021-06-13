package com.sn1pe2win.definitions;

public class DestinySandboxPerkDefinition extends StaticDefinition {

	public DestinySandboxPerkDefinition(long identifier) {
		super(ManifestTables.SandboxPerkDefinition, identifier);
	}

	public boolean isDisplayable() {
		return getRawJson().getAsJsonPrimitive("isDisplayable").getAsBoolean();
	}
	
	public int getDamageType() {
		return getRawJson().getAsJsonPrimitive("damageType").getAsInt();	
	}
}
