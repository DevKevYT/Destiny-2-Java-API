package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;

public class ActivityStats extends DestinyEntity {
	JsonObject object;
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	/**This class only wraps the most common stats. 
	 * You can always get custom stats with this method*/
	public String getStatDisplayValue(String statId) {
		JsonObject stat = object.getAsJsonObject(statId);
		if(stat == null) return null;
		else {
			return stat.getAsJsonObject("basic").getAsJsonPrimitive("displayValue").getAsString();
		}
	}
	
	/**This class only wraps the most common stats. 
	 * You can always get custom stats with this method*/
	public int getStatValue(String statId) {
		JsonObject stat = object.getAsJsonObject(statId);
		if(stat == null) return 0;
		else {
			return stat.getAsJsonObject("basic").getAsJsonPrimitive("value").getAsInt();
		}
	}
	
	public int getDeaths() {
		return getStatValue("deaths");
	}
	
	public int getKills() {
		return getStatValue("kills");
	}
	
	public boolean completed() {
		return getStatValue("completed") == 1;
	}
	
	public int getTimePlayedSeconds() {
		return getStatValue("timePlayedSeconds");
	}
	
	public int getActivityDurationSeconds() {
		return getStatValue("activityDurationSeconds");
	}
	
	//TODO wrap methods
}