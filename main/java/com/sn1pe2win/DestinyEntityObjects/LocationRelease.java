package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyDestinationDefinition;

public class LocationRelease extends DestinyEntity {

	JsonObject object;
	
	public static final int NAV_POINT_TYPE_INACTIVE = 0;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}

	public DisplayProperties getDisplayProperties() {
		return cast(object.getAsJsonObject("displayProperties"), DisplayProperties.class);
	}
	
	public int getSpawnPoint() {
		return object.getAsJsonPrimitive("spawnPoint").getAsInt();
	}
	
	public long getDestinationHash() {
		return object.getAsJsonPrimitive("destinationHash").getAsLong();
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyDestinationDefinition> getDestination() {
		return (Response<DestinyDestinationDefinition>) new DestinyDestinationDefinition(getDestinationHash()).getAsResponse();
	}
	
	public long getActivityGraphHash() {
		return object.getAsJsonPrimitive("activityGraphHash").getAsLong();
	}
	
	public long getActivityGraphNodeHash() {
		return object.getAsJsonPrimitive("activityGraphNodeHash").getAsLong();
	}
	
	public int getActivityBubbleName() {
		return object.getAsJsonPrimitive("activityBubbleName").getAsInt();
	}
	
	public int getActivityPathBundle() {
		return object.getAsJsonPrimitive("activityPathBundle").getAsInt();
	}
	
	public int getActivityPathDestination() {
		return object.getAsJsonPrimitive("activityPathBundle").getAsInt();
	}
	
	public int getNavPointType() {
		return object.getAsJsonPrimitive("activityPathBundle").getAsInt();
	}
	
	public int[] getWorldPosition() {
		return optionalIntArray(object.getAsJsonArray("worldPosition"), new int[] {});
	}
}
