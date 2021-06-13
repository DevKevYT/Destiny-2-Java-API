package com.sn1pe2win.definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DestinyMaterialRequirementSetDefinition extends StaticDefinition {

	public class Material {
		
		public final long itemHash;
		public final boolean deleteOnAction;
		public final int count;
		public final boolean omitFromRequirements;
		
		private Material(long i, boolean d, int c, boolean o) {
			itemHash = i;
			deleteOnAction = d;
			count = c;
			omitFromRequirements = o;
		}
	}
	
	public DestinyMaterialRequirementSetDefinition(long identifier) {
		super(ManifestTables.MaterialRequirementSetDefinition, identifier);
	}

	public Material[] getMaterials() {
		JsonArray arr = getRawJson().getAsJsonArray("materials");
		Material[] m = new Material[arr.size()];
		for(int i = 0; i < m.length; i++) {
			JsonObject co = arr.get(i).getAsJsonObject();
			long it = co.getAsJsonPrimitive("itemHash").getAsLong();
			boolean d = co.getAsJsonPrimitive("deleteOnAction").getAsBoolean();
			int c = co.getAsJsonPrimitive("count").getAsInt();
			boolean o = co.getAsJsonPrimitive("omitFromRequirements").getAsBoolean();
			m[i] = new Material(it, d, c, o);
		}
		return m;
	}
}
