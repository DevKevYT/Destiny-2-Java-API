package com.sn1pe2win.definitions;

import com.sn1pe2win.core.Response;

public class DestinyDestinationDefinition extends StaticDefinition {

	public DestinyDestinationDefinition(long identifier) {
		super(ManifestTables.DestinationDefinition, identifier);
	}
	
	public long getPlaceHash() {
		return getRawJson().getAsJsonPrimitive("placeHash").getAsLong();
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyPlaceDefinition> getPlace() {
		return (Response<DestinyPlaceDefinition>) new DestinyPlaceDefinition(getPlaceHash()).getAsResponse();
	}
	
	public long getDefaultFreeroamActivityHash() {
		return getRawJson().getAsJsonPrimitive("defaultFreeroamActivityHash").getAsLong();
	}
    //TODO the other variables seem unessecary
}
