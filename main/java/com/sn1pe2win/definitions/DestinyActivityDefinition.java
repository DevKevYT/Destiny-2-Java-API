package com.sn1pe2win.definitions;

import com.google.gson.JsonArray;
import com.sn1pe2win.DestinyEntityObjects.DisplayProperties;
import com.sn1pe2win.DestinyEntityObjects.Matchmaking;
import com.sn1pe2win.core.Response;

public class DestinyActivityDefinition extends StaticDefinition {

	private DisplayProperties originalDisplayProperties;
	private DisplayProperties selectionScreenDisplayProperties;
	
	public final long completionUnlockHash;
	public final long destinationHash;
	public final long placeHash;
	public final long activityTypeHash;
	public final long directActivityModeHash;
	
	public final long[] modifierHashes;
	public final long[] activityModeHashes;
	
	/**Alle Namen klassifizierter Raids. Es wird sich bewusst nicht auf die Id's bezogen, da verschieden gewertete Aktivitäten gen gleichen
	 * namen tragen und sie somit verschieden gewertet werden*/
	public static final String[] CLASSIFIED_RAID = new String[] {
			"Scourge of the Past",
			"Leviathan, Eater of Worlds: Normal",
			"Leviathan, Spire of Stars: Normal",
			"Leviathan: Normal",
			"Crown of Sorrow: Normal",
			"Leviathan: Prestige",
			"Leviathan, Spire of Stars: Prestige",
			"Leviathan, Eater of Worlds: Prestige",
			"Leviathan, Eater of Worlds"
	};
	
	public DestinyActivityDefinition(long hash) {
		super(ManifestTables.ActivityDefinition, hash);
		completionUnlockHash = getRawJson().getAsJsonPrimitive("completionUnlockHash").getAsLong();
		
		placeHash = getRawJson().getAsJsonPrimitive("placeHash").getAsLong();
		destinationHash = getRawJson().getAsJsonPrimitive("destinationHash").getAsLong();
		activityTypeHash = getRawJson().getAsJsonPrimitive("activityTypeHash").getAsLong();
		
		directActivityModeHash = optionalLong(getRawJson().getAsJsonPrimitive("directActivityModeHash"), 0);
		
		JsonArray arr = getRawJson().getAsJsonArray("modifiers");
		modifierHashes = new long[arr.size()];
		for(int i = 0; i < modifierHashes.length; i++) 
			modifierHashes[i] = arr.get(i).getAsJsonObject().getAsJsonPrimitive("activityModifierHash").getAsLong();
		
		activityModeHashes = optionalLongArray(getRawJson().getAsJsonArray("activityModeHashes"), new long[] {});
	}
	
	//To prevent unnessecary object creation
	public DisplayProperties getOriginalDisplayProperties() {
		if(originalDisplayProperties == null) {
			originalDisplayProperties = new DisplayProperties();
			originalDisplayProperties.parse(getRawJson().getAsJsonObject("originalDisplayProperties"));
		}
		return originalDisplayProperties;
	}
	
	//To prevent unnessecary object creation
	public DisplayProperties getSelectionScreenDisplayProperties() {
		if(selectionScreenDisplayProperties == null) {
			selectionScreenDisplayProperties = new DisplayProperties();
			selectionScreenDisplayProperties.parse(getRawJson().getAsJsonObject("selectionScreenDisplayProperties"));
		}
		return selectionScreenDisplayProperties;
	}
	
	public String getReleaseIcon() {
		return getRawJson().getAsJsonPrimitive("releaseIcon").getAsString();
	}
	
	public int getReleaseTime() {
		return getRawJson().getAsJsonPrimitive("releaseTime").getAsInt();
	}
	
	public long getCompletionUnlockHash() {
		return getRawJson().getAsJsonPrimitive("completionUnlockHash").getAsLong();
	}
	
	public int getActivityLightLevel() {
		return getRawJson().getAsJsonPrimitive("activityLightLevel").getAsInt();
	}
	
	//TODO destinationHashes, activityTypes
	
	@SuppressWarnings("unchecked")
	public Response<DestinyPlaceDefinition> getPlace() {
		return (Response<DestinyPlaceDefinition>) new DestinyPlaceDefinition(getRawJson().getAsJsonPrimitive("placeHash").getAsLong()).getAsResponse();
	}
	
	public int getTier() {
		return getRawJson().getAsJsonPrimitive("tier").getAsInt();
	}
	
	/**To get an url out of this path, put https://bungie.net in front of it.
	 * It seemed unnessecary for me to write another funtion for it*/
	public String getPCGRImagePath() {
		return getRawJson().getAsJsonPrimitive("pgcrImage").getAsString();
	}
	
	//TODO rewards (Item hashed etc...)
	
	public boolean isPlaylist() {
		return getRawJson().getAsJsonPrimitive("isPlaylist").getAsBoolean();
	}
	
	public Matchmaking getMatchmakingInfo() {
		Matchmaking m = new Matchmaking();
		m.parse(getRawJson().getAsJsonObject("matchmaking"));
		return m;
	}
	
	/**If you just want to load specific modifiers, use the hashes stored in {@link DestinyActivityDefinition#modifierHashes}
	 * and load your own with {@link DestinyActivityModifierDefinition#DestinyActivityModifierDefinition(long)}*/
	@SuppressWarnings("unchecked")
	public Response<DestinyActivityModifierDefinition>[] getModifiers() {
		Response<DestinyActivityModifierDefinition>[] m = new Response[modifierHashes.length];
		for(int i = 0; i < modifierHashes.length; i++) 
			m[i] = (Response<DestinyActivityModifierDefinition>) new DestinyActivityModifierDefinition(modifierHashes[i]).getAsResponse();
		return m;
	}
	
	public boolean inheritFromFreeRoam() {
		return getRawJson().getAsJsonPrimitive("inheritFromFreeRoam").getAsBoolean();
	}
	
	public boolean suppressOtherRewards() {
		return getRawJson().getAsJsonPrimitive("suppressOtherRewards").getAsBoolean();
	}
	
	/**Returns the general mode id from this activity.
	 * Example: type 2 would be STORY, type 4 would be RAID*/
	public int getDirectActivityModeType() {
		return getRawJson().getAsJsonPrimitive("directActivityModeType").getAsInt();
	}
	
	public int[] getActivityModeTypes() {
		JsonArray arr = getRawJson().getAsJsonArray("activityModeTypes");
		int[] types = new  int[arr.size()];
		for(int i = 0; i < types.length; i++)
			types[i] = arr.get(i).getAsInt();
		return types;
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyActivityModeDefinition>[] getActivityModes() {
		Response<DestinyActivityModeDefinition>[] m = new Response[activityModeHashes.length];
		for(int i = 0; i < activityModeHashes.length; i++) 
			m[i] = (Response<DestinyActivityModeDefinition>) new DestinyActivityModeDefinition(activityModeHashes[i]).getAsResponse();
		return m;
	}
	
	/**Returns a detailed mode from a new loaded activityMode*/
	@SuppressWarnings("unchecked")
	public Response<DestinyActivityModeDefinition> getDirectActivityMode() {
		return (Response<DestinyActivityModeDefinition>) new DestinyActivityModeDefinition(getRawJson().getAsJsonPrimitive("directActivityModeHash").getAsLong()).getAsResponse();
	}
	
	public boolean isPvP() {
		return getRawJson().getAsJsonPrimitive("isPvP").getAsBoolean();
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyActivityTypeDefinition> getActivityType() {
		return (Response<DestinyActivityTypeDefinition>) new DestinyActivityTypeDefinition(activityTypeHash).getAsResponse();
	}
	
	public boolean isClassifiedRaid() {
		for(String s : CLASSIFIED_RAID) {
			if(getDisplayProperties().getName().equals(s)) return true;
		}
		return false;
	}
	
	//TODO challenges
	
}
