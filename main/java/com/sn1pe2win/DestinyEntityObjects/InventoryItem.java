package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyInventoryBucketDefinition;
import com.sn1pe2win.definitions.DestinyInventoryItemDefinition;
import com.sn1pe2win.definitions.OptionalValue;

public class InventoryItem extends DestinyEntity {

	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}

	public long getItemHash() {
		return object.getAsJsonPrimitive("itemHash").getAsLong();
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyInventoryItemDefinition> getItem() {
		return (Response<DestinyInventoryItemDefinition>) new DestinyInventoryItemDefinition(getItemHash()).getAsResponse();
	}
	
	@OptionalValue
	public String getItemInstanceId() {
		return optionalString(object.getAsJsonPrimitive("itemInstanceId"), "");
	}
	
	public int getQuantity() {
		return object.getAsJsonPrimitive("quantity").getAsInt();
	}
	
	/**@see DestinyInventoryItemDefinition.BIND_STATUS_NOT_BOUND...*/
	public int getBindStatus() {
		return object.getAsJsonPrimitive("bindStatus").getAsInt();
	}
	
	/**@see DestinyInventoryItemDefinition.ITEM_LOCATION_INVENTORY...*/
	public int getLocation() {
		return object.getAsJsonPrimitive("location").getAsInt();
	}
	
	public long getBucketHash() {
		return object.getAsJsonPrimitive("bucketHash").getAsLong();
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyInventoryBucketDefinition> getBucket() {
		return (Response<DestinyInventoryBucketDefinition>) new DestinyInventoryBucketDefinition(getBucketHash()).getAsResponse();
	}
	
	public int getTransferstatus() {
		return object.getAsJsonPrimitive("transferStatus").getAsInt(); 
	}
	
	public boolean isLockable() {
		return object.getAsJsonPrimitive("lockable").getAsBoolean();
	}
	
	public int getState() {
		return object.getAsJsonPrimitive("state").getAsInt(); 
	}
	
	public int getDismantlePermission() {
		return object.getAsJsonPrimitive("dismantlePermission").getAsInt(); 
	}
	
	public boolean isWrapper() {
		return object.getAsJsonPrimitive("isWrapper").getAsBoolean();
	}
	
	@OptionalValue
	/**For some weird reason sometimes not present. Returns 0 anyways*/
	public int getVersionNumber() {
		return optionalInt(object.getAsJsonPrimitive("versionNumber"), 0);
	}
	
	@OptionalValue
	/**Ornaments*/
	public long getOverrideStyleItemHash() {
		return optionalLong(object.getAsJsonPrimitive("overrideStyleItemHash"), 0);
	}
	
	@SuppressWarnings("unchecked")
	@OptionalValue
	/**Ornaments*/
	public Response<DestinyInventoryItemDefinition> getOverrideStyle() {
		if(getOverrideStyleItemHash() != 0) 
			return (Response<DestinyInventoryItemDefinition>) new DestinyInventoryItemDefinition(getOverrideStyleItemHash()).getAsResponse();
		else return new Response<DestinyInventoryItemDefinition>(null, 404, "NotFound", "Value not present. This item may not have any ornaments equipped.", 0);
	}
}
