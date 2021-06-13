package com.sn1pe2win.DestinyEntityObjects;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**A class that stores a hash as a long value and a hash value as an integer.
 * There are a lot of appereances from the api that this class can take advantage of*/
public class HashPair {

	private final long hashIdentifier;
	private final int hashValue;
	
	public HashPair(long hashIdentifier, int hashValue) {
		this.hashIdentifier = hashIdentifier;
		this.hashValue = hashValue;
	}
	
	/**A neat wrapper for hash pairs in json objects.*/
	public static final HashPair[] fromJsonObject(JsonObject obj) {
		Object[] set = obj.entrySet().toArray();
		HashPair[] pair = new HashPair[set.length];
		
		for(int i = 0; i < set.length; i++) {
			@SuppressWarnings("unchecked")
			Entry<String, JsonElement> entry = (Entry<String, JsonElement>) set[i];
			pair[i] = new HashPair(Long.valueOf(entry.getKey()), entry.getValue().getAsJsonPrimitive().getAsInt());
		}
		return pair;
	}

	public long getHash() {
		return hashIdentifier;
	}
	
	public int getHashValue() {
		return hashValue;
	}
}
