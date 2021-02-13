package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyInventoryBucketDefinition;

public class ItemComponent extends DestinyEntity {

	JsonObject obj = new JsonObject();
	
	@Override
	public JsonObject getRawJson() {
		return obj;
	}

	@Override
	public void parse(JsonObject object) {
		this.obj = object;
	}
	
	public long getItemHash() {
		return obj.getAsJsonPrimitive("itemHash").getAsLong();
	}
	
	public int getQuantity() {
		return obj.getAsJsonPrimitive("quantity").getAsInt();
	}
	//TODO item enums
	public int getBindStatus() {
		return obj.getAsJsonPrimitive("bindStatus").getAsInt();
	}
	
	public int getLocation() {
		return obj.getAsJsonPrimitive("location").getAsInt();
	}
	
	public int getState() {
		return obj.getAsJsonPrimitive("state").getAsInt();
	}
	
	public long getBucketHash() {
		return obj.getAsJsonPrimitive("bucketHash").getAsLong();
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyInventoryBucketDefinition> getBucket() {
		return (Response<DestinyInventoryBucketDefinition>) new DestinyInventoryBucketDefinition(obj.getAsJsonPrimitive("bucketHash").getAsLong()).getAsResponse();
	}
	
	public int getTransferStatus() {
		return obj.getAsJsonPrimitive("transferStatus").getAsInt();
	}
	
	public boolean isLockable() {
		return obj.getAsJsonPrimitive("lockable").getAsBoolean();
	}
	
	public int getDismantlePermission() {
		return obj.getAsJsonPrimitive("dismantlePermission").getAsInt();
	}
	
	public boolean isWrapper() {
		return obj.getAsJsonPrimitive("isWrapper").getAsBoolean();
	}
}
