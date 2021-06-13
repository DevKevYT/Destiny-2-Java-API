package com.sn1pe2win.definitions;

public class DestinyInventoryBucketDefinition extends StaticDefinition {

	public DestinyInventoryBucketDefinition(long identifier) {
		super(ManifestTables.InventoryBucketDefinition, identifier);
	}

	public int getScope() {
		return getRawJson().getAsJsonPrimitive("scope").getAsInt();
	}
	
	public int getCategory() {
		return getRawJson().getAsJsonPrimitive("category").getAsInt();
	}
	
	public int getBucketOrder() {
		return getRawJson().getAsJsonPrimitive("bucketOrder").getAsInt();
	}
	
	public int getItemCount() {
		return getRawJson().getAsJsonPrimitive("itemCount").getAsInt();
	}
	
	public int getLocation() {
		return getRawJson().getAsJsonPrimitive("location").getAsInt();
	}
	
	public boolean hasTranserDestination() {
		return getRawJson().getAsJsonPrimitive("hasTransferDestination").getAsBoolean();
	}
	
	public boolean enabled() {
		return getRawJson().getAsJsonPrimitive("enabled").getAsBoolean();
	}
	
	/**Whatever THAT means...*/
	public boolean fifo() {
		return getRawJson().getAsJsonPrimitive("fifo").getAsBoolean();
	}
}
