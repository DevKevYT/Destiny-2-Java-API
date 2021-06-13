package com.sn1pe2win.definitions;

import com.google.gson.JsonArray;
import com.sn1pe2win.DestinyEntityObjects.ScaledStats;

public class DestinyStatGroupDefinition extends StaticDefinition {

	public DestinyStatGroupDefinition(long identifier) {
		super(ManifestTables.StatGroupDefinition, identifier);
	}
	
	public int getUIPosition() {
		return getRawJson().getAsJsonPrimitive("uiPosition").getAsInt();
	}
	
	public int getMaximumValue() {
		return getRawJson().getAsJsonPrimitive("maximumValue").getAsInt();
	}
	
	public ScaledStats[] getScaledStats() {
		JsonArray jstats = getRawJson().getAsJsonArray("scaledStats");
		ScaledStats[] stats = new ScaledStats[jstats.size()];
		
		for(int i = 0; i < jstats.size(); i++) {
			ScaledStats s = new ScaledStats();
			s.parse(jstats.get(i).getAsJsonObject());
			stats[i] = s;
		}
		
		return stats;
	}
	
	/**Usually empty, because somehow all objects from the database are empty*/
	public ScaledStats[] getStatOverride() {
		JsonArray jstats = getRawJson().getAsJsonArray("overrides");
		ScaledStats[] stats = new ScaledStats[jstats.size()];
		
		for(int i = 0; i < jstats.size(); i++) {
			ScaledStats s = new ScaledStats();
			s.parse(jstats.get(i).getAsJsonObject());
			stats[i] = s;
		}
		
		return stats;
	}
}
