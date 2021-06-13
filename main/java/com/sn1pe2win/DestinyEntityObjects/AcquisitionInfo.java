package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyMaterialRequirementSetDefinition;

public class AcquisitionInfo extends DestinyEntity {

	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	public long getAcquireMaterialRequirementHash() {
		return optionalLong(object.getAsJsonPrimitive("acquireMaterialRequirementHash"), 0);
	}
	
	@SuppressWarnings("unchecked")
	public Response<DestinyMaterialRequirementSetDefinition> getAcquireMaterialRequirement() {
		long hash = getAcquireMaterialRequirementHash();
		if(hash != 0) {
			return (Response<DestinyMaterialRequirementSetDefinition>) new DestinyMaterialRequirementSetDefinition(hash).getAsResponse();
		} else return new Response<DestinyMaterialRequirementSetDefinition>(null, 404, "Not Found", "This key was not found inside this object, because this is an optional value.", 0);
	}
	
	public boolean runOnlyAcquisitionRewardSite() {
		return object.getAsJsonPrimitive("runOnlyAcquisitionRewardSite").getAsBoolean();
	}
}
