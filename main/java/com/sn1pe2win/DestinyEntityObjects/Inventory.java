package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyInventoryBucketDefinition;
import com.sn1pe2win.definitions.OptionalValue;

public class Inventory extends DestinyEntity {

	public static final int TIER_TYPE_BASIC = 2;
	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	@OptionalValue
	public String getStackUniqueLabel() {
		return optionalString(object.getAsJsonPrimitive("stackUniqueLabel"), "");
	}

	public int getMaxStackSize() {
		return object.getAsJsonPrimitive("maxStackSize").getAsInt();
	}
	
	public long getBucketTypeHash() {
		return object.getAsJsonPrimitive("bucketTypeHash").getAsLong();
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyInventoryBucketDefinition> getBucketType() {
		return (Response<DestinyInventoryBucketDefinition>) new DestinyInventoryBucketDefinition(getBucketTypeHash()).getAsResponse();
	}
	
	@OptionalValue
	public long getRecoveryBucketTypeHash() {
		return object.getAsJsonPrimitive("recoveryBucketTypeHash").getAsLong();
	}
	
	//TODO definition
	public long getTierTypeHash() {
		return object.getAsJsonPrimitive("tierTypeHash").getAsLong();
	}
	
	public boolean isInstanceItem() {
		return object.getAsJsonPrimitive("isInstanceItem").getAsBoolean();
	}
	
	public boolean isNonTransferrableOriginal() {
		return object.getAsJsonPrimitive("nonTransferrableOriginal").getAsBoolean();
	}
	
	public String getTierTypeName() {
		return object.getAsJsonPrimitive("tierTypeName").getAsString();
	}
	
	/**{@link Inventory#TIER_TYPE_BASIC}<br>...*/
	public int getTierType() {
		return object.getAsJsonPrimitive("tierType").getAsInt();
	}
	
	public String getExpirationTooltip() {
		return object.getAsJsonPrimitive("expirationTooltip").getAsString();
	}
	
	public String getExpiredInActivityMessage() {
		return object.getAsJsonPrimitive("expiredInActivityMessage").getAsString();
	}
	
	public String getExpiredInOrbitMessage() {
		return object.getAsJsonPrimitive("expiredInOrbitMessage").getAsString();
	}
	
	public boolean isSuppressExpirationWhenObjectivesComplete() {
		return object.getAsJsonPrimitive("suppressExpirationWhenObjectivesComplete").getAsBoolean();
	}
}
