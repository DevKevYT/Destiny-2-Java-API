package com.sn1pe2win.endpoints;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;
import com.sn1pe2win.DestinyEntityObjects.ActivityDetails;
import com.sn1pe2win.DestinyEntityObjects.PGCRPlayerEntry;
import com.sn1pe2win.core.DestinyDateFormat;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Gateway;
import com.sn1pe2win.core.Response;

public class GetPostGameCarnageReport extends DestinyEntity {
	
	Response<?> originalResponse;
	JsonObject object;
	
	/**This is a string, since the api also provides this as a string (for some weird reason)*/
	public GetPostGameCarnageReport(String instanceId) {
		
		super.REQUEST_TYPE = "Destiny2.GetPostGameCarnageReport";
		
		Response<JsonObject> request = Gateway.sendFullGet("https://stats.bungie.net/Platform/Destiny2/Stats/PostGameCarnageReport/" + instanceId + "/");
		if(request.success()) parse(request.getResponseData());
		
		originalResponse = request;
	}
	
	public Response<?> getResponse() {
		return originalResponse;
	}
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object.getAsJsonObject("Response");
	}
	
	public Date getPeriod() {
		try {
			return DestinyDateFormat.toDate(getRawJson().getAsJsonPrimitive("period").getAsString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
	
	public int getStartingphaseIndex() {
		return getRawJson().getAsJsonPrimitive("startingPhaseIndex").getAsInt();
	}
	
	public PGCRPlayerEntry[] getPlayers() {
		List<PGCRPlayerEntry> p = castArray(getRawJson().getAsJsonArray("entries"), PGCRPlayerEntry.class);
		return p.toArray(new PGCRPlayerEntry[p.size()]);
	}
	
	public ActivityDetails getActivityDetails() {
		ActivityDetails d = new ActivityDetails();
		d.parse(getRawJson().getAsJsonObject("activityDetails"));
		return d;
	}
	
}
