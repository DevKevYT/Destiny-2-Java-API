package com.sn1pe2win.definitions;

import com.google.gson.JsonObject;

public class DestinyClassDefinition extends StaticDefinition {

	public final long[] genderHashes;
	
	public DestinyClassDefinition(long identifier) {
		super(ManifestTables.ClassDefinition, identifier);
		
		JsonObject arr = getRawJson().getAsJsonObject("genderedClassNamesByGenderHash");
		genderHashes = new long[arr.size()];
		int i = 0;
		for(String e : arr.keySet()) {
			genderHashes[i] = Long.valueOf(e);
			i++;
		}
	}
	
	public int getType() {
		return getRawJson().getAsJsonPrimitive("classType").getAsInt();
	}

	public String getMaleClassName() {
		return getRawJson().getAsJsonObject("genderedClassNames").getAsJsonPrimitive("Male").getAsString();
	}
	
	public String getFemaleClassName() {
		return getRawJson().getAsJsonObject("genderedClassNames").getAsJsonPrimitive("Female").getAsString();
	}
	
	public String getNameByGenderHash(long genderHash) {
		JsonObject arr = getRawJson().getAsJsonObject("genderedClassNamesByGenderHash");
		for(String e : arr.keySet()) {
			if( Long.valueOf(e) == genderHash) {
				return arr.getAsJsonPrimitive(e).getAsString();
			}
		}
		return "";
	}
}
