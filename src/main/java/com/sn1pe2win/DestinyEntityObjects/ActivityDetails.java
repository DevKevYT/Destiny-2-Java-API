package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyActivityDefinition;
import com.sn1pe2win.definitions.MembershipType;

public class ActivityDetails extends DestinyEntity {
	
	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	public long getActivityHash() {
		return object.getAsJsonPrimitive("directorActivityHash").getAsLong();
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyActivityDefinition> getActivity() {
		return (Response<DestinyActivityDefinition>) new DestinyActivityDefinition(getActivityHash()).getAsResponse();
	}
	
	/**Good for the pgcr*/ //TODO load pgcr from here
	public String getInstanceId() {
		return object.getAsJsonPrimitive("instanceId").getAsString();
	}
	
	//TODO enum names
	public int getMode() {
		return object.getAsJsonPrimitive("mode").getAsInt();
	}
	
	public int[] getModes() {
		return optionalIntArray(object.getAsJsonArray("modes"), new int[] {});
	}
	
	public boolean isPrivate() {
		return object.getAsJsonPrimitive("isPrivate").getAsBoolean();
	}
	
	public MembershipType getMembershipType() {
		return MembershipType.of(object.getAsJsonPrimitive("membershipType").getAsShort());
	}
}
