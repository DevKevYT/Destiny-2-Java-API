package com.sn1pe2win.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**This interface should get implemented by every class that represents an object that handles
 * responses.
 * To make things mor general I call these recieved "json objects" DestinyEntity*/
public abstract class DestinyEntity {

	/**Just a tool for a better overwiew, so you can see what enpoints depend on what objects.
	 * Example: REQUEST_TYPE for a profile dataset could be "Destiny2.GetProfile" <br> or "/Platform/Destiny2/{platform}/Profile/{membershipId}/?components=100"*/
	public String REQUEST_TYPE;
	
	public abstract JsonObject getRawJson();
	
	/**Parse the json object into the java object*/
	public abstract void parse(JsonObject object);

	protected int optionalInt(JsonPrimitive value, int alternativeValue) {
		if(value == null) return alternativeValue;
		else return value.getAsInt();
	}
	
	protected long optionalLong(JsonPrimitive value, long alternativeValue) {
		if(value == null) return alternativeValue;
		else return value.getAsLong();
	}
	
	protected String optionalString(JsonPrimitive value, String alternativeValue) {
		if(value == null) return alternativeValue;
		else return value.getAsString();
	}
	
	protected int[] optionalIntArray(JsonArray array, int[] alternativeValue) {
		if(array == null) return alternativeValue;
		else {
			int[] constr = new int[array.size()];
			for(int i = 0; i < constr.length; i++) {
				if(array.get(i).isJsonPrimitive()) {
					constr[i] = array.get(i).getAsJsonPrimitive().getAsInt();
				} else return alternativeValue;
			}
			return constr;
		}
	}
	
	protected long[] optionalLongArray(JsonArray array, long[] alternativeValue) {
		if(array == null) return alternativeValue;
		else {
			long[] constr = new long[array.size()];
			for(int i = 0; i < constr.length; i++) {
				if(array.get(i).isJsonPrimitive()) {
					constr[i] = array.get(i).getAsJsonPrimitive().getAsLong();
				} else return alternativeValue;
			}
			return constr;
		}
	}
	
	public String toString() {
		return getRawJson() != null ? getRawJson().toString() : "null" ;
	}
}
