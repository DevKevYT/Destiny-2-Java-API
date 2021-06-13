package com.sn1pe2win.definitions;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sn1pe2win.DestinyEntityObjects.DisplayProperties;
import com.sn1pe2win.core.DestinyEntity;

public class DestinyChecklistDefinition extends StaticDefinition {

	public class ChecklistEntry extends DestinyEntity {

		JsonObject object;
		
		@Override
		public JsonObject getRawJson() {
			return object;
		}

		@Override
		public void parse(JsonObject object) {
			this.object = object;
		}
		
		public DisplayProperties getDisplayProperties() {
			DisplayProperties p = new DisplayProperties();
			p.parse(object.getAsJsonObject("displayProperties"));
			return p;
		}
		
		public int getScope() {
			return object.getAsJsonPrimitive("scope").getAsInt();
		}
		
		public long getHash() {
			return object.getAsJsonPrimitive("hash").getAsLong();
		}
		
		//TODO Hash definitions
		public long getDestinationHash() {
			return optionalLong(getRawJson().getAsJsonPrimitive("destinationHash"), 0);
		}
		//TODO Hash definitions
		public long getLocationHash() {
			return optionalLong(getRawJson().getAsJsonPrimitive("locationHash"), 0);
		}
		
		//Whatever THAT is...
		public long getBubbleHash() {
			return optionalLong(getRawJson().getAsJsonPrimitive("bubbleHash"), 0);
		}
	}
	
	public DestinyChecklistDefinition(long identifier) {
		super(ManifestTables.ChecklistDefinition, identifier);
	}
	
	public String getViewActionString() {
		return getRawJson().getAsJsonPrimitive("viewActionString").getAsString();
	}
	
	public int getScope() {
		return getRawJson().getAsJsonPrimitive("scope").getAsInt();
	}

	public ChecklistEntry[] getEntries() {
		JsonArray arr = getRawJson().getAsJsonArray("entries");
		ChecklistEntry[] e = new ChecklistEntry[arr.size()];
		for(int i = 0; i < e.length; i++) {
			e[i] = new ChecklistEntry();
			e[i].parse(arr.get(i).getAsJsonObject());
		}
		return e;
	}
}
