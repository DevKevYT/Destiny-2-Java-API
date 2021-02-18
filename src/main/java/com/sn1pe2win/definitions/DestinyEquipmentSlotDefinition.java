package com.sn1pe2win.definitions;

import com.sn1pe2win.core.Response;

public class DestinyEquipmentSlotDefinition extends StaticDefinition {

	public DestinyEquipmentSlotDefinition(long identifier) {
		super(ManifestTables.EquipmentSlotDefinition, identifier);
	}
	
	public long getEquipmentCategoryHash() {
		return getRawJson().getAsJsonPrimitive("equipmentCategoryHash").getAsLong();
	}
	
	public long getBucketTypeHash() {
		return getRawJson().getAsJsonPrimitive("bucketTypeHash").getAsLong();
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyInventoryBucketDefinition> getBucketType() {
		return (Response<DestinyInventoryBucketDefinition>) new DestinyInventoryBucketDefinition(getBucketTypeHash()).getAsResponse();
	}

	public boolean applyCustomArtDye() {
		return getRawJson().getAsJsonPrimitive("applyCustomArtDyes").getAsBoolean();
	}
	
	//TODO ArtDye channels
}
