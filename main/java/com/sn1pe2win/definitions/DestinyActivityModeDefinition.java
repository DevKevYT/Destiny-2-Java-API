package com.sn1pe2win.definitions;

import com.google.gson.JsonArray;
import com.sn1pe2win.core.Response;

public class DestinyActivityModeDefinition extends StaticDefinition {

	public final long[] parentHashes;
	
	public DestinyActivityModeDefinition(long identifier) {
		super(ManifestTables.ActivityModeDefinition, identifier);
		
		JsonArray arr = getRawJson().getAsJsonArray("parentHashes");
		if(arr != null) {
			parentHashes = new long[arr.size()];
			for(int i = 0; i < parentHashes.length; i++) 
				parentHashes[i] = arr.get(i).getAsJsonPrimitive().getAsLong();
		} else parentHashes = new long[] {};
	}
	
	/**A little bit confusing at first, but: If THIS type is: STORY a parent hash
	 * woulbe of a type named: PvE, because story can only happen in PvE*/
	@SuppressWarnings("unchecked")
	public Response<DestinyActivityModeDefinition>[] getParentHashes() {
		Response<DestinyActivityModeDefinition>[] m = new Response[parentHashes.length];
		for(int i = 0; i < parentHashes.length; i++) 
			m[i] = (Response<DestinyActivityModeDefinition>) new DestinyActivityModeDefinition(parentHashes[i]).getAsResponse();
		return m;
	}
	
	public int getModeType() {
		return getRawJson().getAsJsonPrimitive("modeType").getAsInt();
	}
	
	public String getPGCRImage() {
		return getRawJson().getAsJsonPrimitive("pgcrImage").getAsString();
	}

	public boolean isTeamBased() {
		return getRawJson().getAsJsonPrimitive("isTeamBased").getAsBoolean();
	}
	
	public int getTier() {
		return getRawJson().getAsJsonPrimitive("tier").getAsInt();
	}
	
	public boolean isAggregateMode() {
		return getRawJson().getAsJsonPrimitive("isAggregateMode").getAsBoolean();
	}
	
	public String getFriendlyName() {
		return getRawJson().getAsJsonPrimitive("friendlyName").getAsString();
	}
	
	public boolean supportsFeedFiltering() {
		return getRawJson().getAsJsonPrimitive("supportsFeedFiltering").getAsBoolean();
	}
	
	public boolean display() {
		return getRawJson().getAsJsonPrimitive("display").getAsBoolean();
	}
	
	public int getOrder() {
		return getRawJson().getAsJsonPrimitive("order").getAsInt();
	}
}
