package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;

public class StateInfo extends DestinyEntity {

	JsonObject object;
	
	public class Requirements extends DestinyEntity {
		
		JsonObject object;
		
		@Override
		public JsonObject getRawJson() {
			return object;
		}

		@Override
		public void parse(JsonObject object) {
			this.object = object;
		}
		
		public String getEntitlementUnavailableMessage() {
			return object.getAsJsonPrimitive("entitlementUnavailableMessage").getAsString();
		}
	}
	
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}

	public Requirements getRequirements() {
		Requirements r = new Requirements();
		r.parse(object.getAsJsonObject("requirements"));
		return r;
	}
}
