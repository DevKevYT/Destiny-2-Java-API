package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;

public class ItemAction extends DestinyEntity {

	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}

	public String getDescription() {
		return object.getAsJsonPrimitive("verbDescription").getAsString();
	}
	
	public String getActionTypeLabel() {
		return object.getAsJsonPrimitive("actionTypeLabel").getAsString();
	}
	
	/**I assume if the action is not "positive", (For example the dismantle bar) the bar would be red*/
	public boolean isPositive() {
		return object.getAsJsonPrimitive("isPositive").getAsBoolean();
	}
	
	public int getRequiredCooldownSeconds() {
		return object.getAsJsonPrimitive("requiredCooldownSeconds").getAsInt();
	}
	
	public long getRewardSheetHash() {
		return object.getAsJsonPrimitive("rewardSheetHash").getAsLong();
	}
	
	public long getRewardItemHash() {
		return object.getAsJsonPrimitive("rewardItemHash").getAsLong();
	}
	
	public long getRewardSiteHash() {
		return object.getAsJsonPrimitive("rewardSiteHash").getAsLong();
	}
	
	public long getRequiredCooldownHash() {
		return object.getAsJsonPrimitive("requiredCooldownHash").getAsLong();
	}
	
	public boolean deleteOnAction() {
		return object.getAsJsonPrimitive("deleteOnAction").getAsBoolean();
	}
	
	public boolean consumeEntireStack() {
		return object.getAsJsonPrimitive("consumeEntireStack").getAsBoolean();
	}
	
	public boolean useOnAquire() {
		return object.getAsJsonPrimitive("useOnAcquire").getAsBoolean();
	}
}
