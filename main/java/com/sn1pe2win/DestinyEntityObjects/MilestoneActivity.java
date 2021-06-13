package com.sn1pe2win.DestinyEntityObjects;

import java.util.List;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyActivityDefinition;
import com.sn1pe2win.definitions.OptionalValue;

public class MilestoneActivity extends DestinyEntity {
	
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
		return object.getAsJsonPrimitive("activityHash").getAsLong();
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyActivityDefinition> getActivity() {
		return (Response<DestinyActivityDefinition>) new DestinyActivityDefinition(getActivityHash()).getAsResponse();
	}
	
	@OptionalValue
	public MilestoneActivityChallenge[] getChallenges() {
		List<MilestoneActivityChallenge> a = castArray(object.getAsJsonArray("challenges"), MilestoneActivityChallenge.class);
		return a.toArray(new MilestoneActivityChallenge[a.size()]);
	}
}
