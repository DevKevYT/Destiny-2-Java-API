package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyClassDefinition;
import com.sn1pe2win.definitions.DestinyGenderDefinition;
import com.sn1pe2win.definitions.DestinyInventoryItemDefinition;
import com.sn1pe2win.definitions.DestinyRaceDefinition;

public class PGCRPlayerEntry extends DestinyEntity {

	public class PGCRPlayer extends DestinyEntity {
		JsonObject object;
		
		@Override
		public JsonObject getRawJson() {
			return object;
		}

		@Override
		public void parse(JsonObject object) {
			this.object = object;
		}
		
		public DestinyUserInfo getDestinyUserInfo() {
			DestinyUserInfo u = new DestinyUserInfo();
			u.parse(object.getAsJsonObject("destinyUserInfo"));
			return u;
		}
		
		public String getCharacterClassReadable() {
			return object.getAsJsonPrimitive("characterClass").getAsString();
		}
		
		public long getDestinyClassHash() {
			return object.getAsJsonPrimitive("classHash").getAsLong();
		}
		
		@SuppressWarnings("unchecked")
		public Response<DestinyClassDefinition> getDestinyClass() {
			return (Response<DestinyClassDefinition>) new DestinyClassDefinition(getDestinyClassHash()).getAsResponse();
		}
		
		public long getRaceHash() {
			return object.getAsJsonPrimitive("raceHash").getAsLong();
		}
		
		@SuppressWarnings("unchecked")
		public Response<DestinyRaceDefinition> getRace() {
			return (Response<DestinyRaceDefinition>) new DestinyRaceDefinition(getRaceHash()).getAsResponse();
		}
		
		public long getGenderHash() {
			return object.getAsJsonObject("genderHash").getAsLong();
		}
		
		@SuppressWarnings("unchecked")
		public Response<DestinyGenderDefinition> getGender() {
			return (Response<DestinyGenderDefinition>) new DestinyGenderDefinition(getGenderHash()).getAsResponse();
		}
		
		public int getCharacterLevel() {
			return object.getAsJsonPrimitive("characterLevel").getAsInt();
		}
		
		public int getLightlevel() {
			return object.getAsJsonPrimitive("lightLevel").getAsInt();
		}
		
		public long getEmblemHash() {
			return object.getAsJsonPrimitive("emblemHash").getAsLong();
		}
		
		@SuppressWarnings("unchecked")
		public Response<DestinyInventoryItemDefinition> getEmblemAsItem() {
			return (Response<DestinyInventoryItemDefinition>) new DestinyInventoryItemDefinition(getEmblemHash()).getAsResponse();
		}
	}
	
	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	public int getStanding() {
		return object.getAsJsonPrimitive("standing").getAsInt();
	}
	
	public int getScore() {
		return object.getAsJsonObject("score").getAsJsonObject("basic").getAsJsonPrimitive("value").getAsInt();
	}
	
	public PGCRPlayer getPlayerInfo() {
		PGCRPlayer p = new PGCRPlayer();
		p.parse(object.getAsJsonObject("player"));
		return p;
	}

	public long getCharacterId() {
		return object.getAsJsonPrimitive("characterId").getAsLong();
	}
	
	public ActivityStats getStats() {
		ActivityStats a = new ActivityStats();
		a.parse(object.getAsJsonObject("values"));
		return a;
	}
	
	//TODO do "extended"
}
