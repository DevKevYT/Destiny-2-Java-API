package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;

public class FactionVendorInformation extends DestinyEntity {

	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	public long getVendorHash() {
		return object.getAsJsonPrimitive("vendorHash").getAsLong();
	}
	
	public long getDestinationHash() {
		return object.getAsJsonPrimitive("destinationHash").getAsLong();
	}
	
	public String getBackgroundImagePath() {
		return object.getAsJsonPrimitive("backgroundImagePath").getAsString();
	}
	
	//TODO hashes as usual
}
