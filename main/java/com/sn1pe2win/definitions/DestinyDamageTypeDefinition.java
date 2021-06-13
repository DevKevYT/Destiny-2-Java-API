package com.sn1pe2win.definitions;

public class DestinyDamageTypeDefinition extends StaticDefinition {

	public static final int STASIS = 6;
	public static final int RAID = 5;
	public static final int SOLAR = 3;
	public static final int ARC = 2;
	public static final int KINETIC = 1;
	public static final int VOID = 2;
	
	public DestinyDamageTypeDefinition(long identifier) {
		super(ManifestTables.DamageTypeDefinition, identifier);
	}
//TODO maybe load by enum not identifier
	public String getTransparentIconPath() {
		return getRawJson().getAsJsonPrimitive("transparentIconPath").getAsString();
	}
	
	public boolean showIcon() {
		return getRawJson().getAsJsonPrimitive("showIcon").getAsBoolean();
	}
	
	public int getEnumValue() {
		return getRawJson().getAsJsonPrimitive("enumValue").getAsInt();
	}
}
