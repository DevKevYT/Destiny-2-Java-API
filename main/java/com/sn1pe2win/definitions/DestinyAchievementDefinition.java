package com.sn1pe2win.definitions;

public class DestinyAchievementDefinition extends StaticDefinition {

	public DestinyAchievementDefinition(long identifier) {
		super(ManifestTables.AchievementDefinition, identifier);
	}
	
	public int getAcccumulatorThreshold() {
		return getRawJson().getAsJsonPrimitive("acccumulatorThreshold").getAsInt();
	}
	
	public int getPlatformIndex() {
		return getRawJson().getAsJsonPrimitive("platformIndex").getAsInt();
	}
}
