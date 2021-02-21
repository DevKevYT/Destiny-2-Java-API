package com.sn1pe2win.DestinyEntityObjects;

import java.text.ParseException;
import java.util.Date;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyDateFormat;
import com.sn1pe2win.core.DestinyEntity;

public class PlayerActivity extends DestinyEntity {
	
	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	/**The raw jsonObject with "Response"*/
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	public Date getPeriod() {
		try {
			return DestinyDateFormat.toDate(object.getAsJsonPrimitive("period").getAsString());
		} catch (ParseException e) {
		}
		return new Date();
	}
	
	public ActivityDetails getActivityDetails() {
		ActivityDetails a = new ActivityDetails();
		a.parse(object.getAsJsonObject("activityDetails"));
		return a;
	}

	public ActivityStats getActivityStats() {
		ActivityStats a = new ActivityStats();
		a.parse(object.getAsJsonObject("values"));
		return a;
	}
}
