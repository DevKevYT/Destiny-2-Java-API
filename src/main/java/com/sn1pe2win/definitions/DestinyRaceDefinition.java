package com.sn1pe2win.definitions;

import com.google.gson.JsonObject;

public class DestinyRaceDefinition extends StaticDefinition {

	public final long[] genderRaceHashes;
	
	public DestinyRaceDefinition(long identifier) {
		super(ManifestTables.RaceDefinition, identifier);
		
		JsonObject arr = getRawJson().getAsJsonObject("genderedRaceNamesByGenderHash");
		genderRaceHashes = new long[arr.size()];
		int i = 0;
		for(String e : arr.keySet()) {
			genderRaceHashes[i] = Long.valueOf(e);
			i++;
		}
	}

	public int getRaceType() {
		return getRawJson().getAsJsonPrimitive("raceType").getAsInt();
	}

	public String getMaleRaceName() {
		return getRawJson().getAsJsonObject("genderedRaceNames").getAsJsonPrimitive("Male").getAsString();
	}
	
	public String getFemaleRaceName() {
		return getRawJson().getAsJsonObject("genderedRaceNames").getAsJsonPrimitive("Female").getAsString();
	}
	
	public String getNameByGenderHash(long genderHash) {
		JsonObject arr = getRawJson().getAsJsonObject("genderedRaceNamesByGenderHash");
		for(String e : arr.keySet()) {
			if( Long.valueOf(e) == genderHash) {
				return arr.getAsJsonPrimitive(e).getAsString();
			}
		}
		return "";
	}
}
