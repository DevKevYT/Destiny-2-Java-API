package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.definitions.MembershipType;

public class DestinyUserInfo extends DestinyEntity {
	
	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	public String getIconPath() {
		return object.getAsJsonPrimitive("iconPath").getAsString();
	}
	
	public MembershipType getCrossSaveOverride() {
		return MembershipType.of(object.getAsJsonPrimitive("crossSaveOverride").getAsShort());
	}
	
	public boolean isPublic() {
		return object.getAsJsonPrimitive("isPublic").getAsBoolean();
	}
	
	public MembershipType getMembershipType() {
		return MembershipType.of(object.getAsJsonPrimitive("membershipType").getAsShort());
	}
	
	public long getMembershipId() {
		return object.getAsJsonPrimitive("membershipId").getAsLong();
	}
	
	public String getDisplayName() {
		return object.getAsJsonPrimitive("displayName").getAsString();
	}
}
