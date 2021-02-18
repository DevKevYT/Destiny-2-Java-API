package com.sn1pe2win.definitions;

import com.google.gson.JsonArray;
import com.sn1pe2win.core.Response;

public class DestinyItemCategoryDefinition extends StaticDefinition {

	public DestinyItemCategoryDefinition(long identifier) {
		super(ManifestTables.ItemCategoryDefinition, identifier);
	}

	public boolean isVisible() {
		return getRawJson().getAsJsonPrimitive("visible").getAsBoolean();
	}
	
	public boolean isDeprected() {
		return getRawJson().getAsJsonPrimitive("deprecated").getAsBoolean();
	}
	
	public String getShortTitle() {
		return getRawJson().getAsJsonPrimitive("shortTitle").getAsString();
	}
	
	public String itemTypeRegex() {
		return getRawJson().getAsJsonPrimitive("itemTypeRegex").getAsString();
	}
	
	public String getOriginBucketIdentifier() {
		return getRawJson().getAsJsonPrimitive("originBucketIdentifier").getAsString();
	}
	
	public int getGrantDestinyBreakerType() {
		return getRawJson().getAsJsonPrimitive("grantDestinyBreakerType").getAsInt();
	}
	
	public int getDestinyItemType() {
		return getRawJson().getAsJsonPrimitive("grantDestinyItemType").getAsInt();
	}
	
	public int getGrantDestinySubType() {
		return getRawJson().getAsJsonPrimitive("grantDestinySubType").getAsInt();
	}
	
	public int grantDestinyClass() {
		return getRawJson().getAsJsonPrimitive("grantDestinyClass").getAsInt();
	}
	
	public long[] getGroupedCategoryHashes() {
		JsonArray jarr = getRawJson().getAsJsonArray("groupedCategoryHashes");
		long[] arr = new long[jarr.size()];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = jarr.get(i).getAsJsonPrimitive().getAsLong();
		}
		return arr;
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyItemCategoryDefinition>[] getGroupedCategories() {
		JsonArray jarr = getRawJson().getAsJsonArray("groupedCategoryHashes");
		Response<DestinyItemCategoryDefinition>[] arr = new Response[jarr.size()];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = (Response<DestinyItemCategoryDefinition>) new DestinyItemCategoryDefinition(jarr.get(i).getAsJsonPrimitive().getAsLong()).getAsResponse();
		}
		return arr;
	}
	
	public long[] getParentCategoryHashes() {
		return optionalLongArray(getRawJson().getAsJsonArray("parentCategoryHashes"), new long[] {});
	}
	
	public boolean isPlug() {
		return getRawJson().getAsJsonPrimitive("isPlug").getAsBoolean();
	}
	
	public boolean isGroupCategoryOnly() {
		return getRawJson().getAsJsonPrimitive("groupCategoryOnly").getAsBoolean();
	}
	
}
