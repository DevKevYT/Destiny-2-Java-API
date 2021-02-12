package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;

public class Matchmaking extends DestinyEntity {

	private boolean isMatchmade = false;
	private int minParty = 0;
	private int maxParty = 0;
	private int maxPlayers = 0;
	private boolean requiresGuadianOath = false;
	
	public void parse(JsonObject obj) {
		if(obj == null) return;
		
		isMatchmade = obj.getAsJsonPrimitive("isMatchmade") == null ? false : obj.getAsJsonPrimitive("isMatchmade").getAsBoolean();
		minParty = obj.getAsJsonPrimitive("minParty").getAsInt();
		maxParty = obj.getAsJsonPrimitive("maxParty").getAsInt();
		maxPlayers = obj.getAsJsonPrimitive("maxPlayers").getAsInt();
		requiresGuadianOath = obj.getAsJsonPrimitive("requiresGuardianOath").getAsBoolean();
	}

	@Override
	public JsonObject getRawJson() {
		return null;
	}

	public boolean isMatchmade() {
		return isMatchmade;
	}

	public int getMinParty() {
		return minParty;
	}

	public int getMaxParty() {
		return maxParty;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public boolean isRequiresGuadianOath() {
		return requiresGuadianOath;
	}

}
