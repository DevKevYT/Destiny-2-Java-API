package com.sn1pe2win.core;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sn1pe2win.DestinyEntityObjects.HashPair;

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
	
	protected boolean optionalBoolean(JsonPrimitive value, boolean alternativeValue) {
		if(value == null) return alternativeValue;
		else return value.getAsBoolean();
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
	
	protected HashPair[] optionalHashPair(JsonObject object) {
		if(object == null) return new HashPair[] {};
		else return HashPair.fromJsonObject(object);
	}
	
	public <T extends DestinyEntity> T cast(JsonObject object, Class<T> classToConvertTo) {
		T type;
		try {
			type = classToConvertTo.newInstance();
			((DestinyEntity) type).parse(object);
			return type;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public <T extends DestinyEntity> T optionalCast(JsonObject object, Class<T> classToConvertTo) {
		if(object == null) return null;
		else return cast(object, classToConvertTo);
	}

	/**Casts a jsonArray into a DestinyEntity list
	 * @param <T>
	 * @throws IllegalAccessException 
	 * @throws InstantiationException */
	public <T extends DestinyEntity> List<T> castArray(JsonArray array, Class<T> classToConvert) {
		ArrayList<T> list = new ArrayList<T>(array.size());
		for(int i = 0; i < array.size(); i++) {
			T type;
			try {
				type = classToConvert.newInstance();
				((DestinyEntity) type).parse(array.get(i).getAsJsonObject());
				list.add(type);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public <T extends DestinyEntity> List<T> optionalCastArray(JsonArray array, Class<T> classToConvert) {
		if(array == null) return new ArrayList<T>(); 
		else return castArray(array, classToConvert);
	}
	
	public String toString() {
		return getRawJson() != null ? getRawJson().toString() : "null" ;
	}
}
