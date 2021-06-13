package com.sn1pe2win.DestinyEntityObjects;

import java.util.List;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyClassDefinition;
import com.sn1pe2win.definitions.DestinyGenderDefinition;
import com.sn1pe2win.definitions.DestinyInventoryItemDefinition;
import com.sn1pe2win.definitions.DestinyRaceDefinition;

public class PGCRPlayerEntry extends DestinyEntity {
	
	public class ExtendedPGCRStats extends DestinyEntity {
		JsonObject object;
		
		@Override
		public JsonObject getRawJson() {
			return object;
		}

		@Override
		public void parse(JsonObject object) {
			this.object = object;
		}
		
		/**@return null, if the played did not played with this weapon.*/
		public ExtendedPGCRWeapons getUniqueWeaponKills(long itemHash) {
			for(ExtendedPGCRWeapons p : getUniqueWeaponKills()) {
				if(p.getItemHash() == itemHash) return p;
			}
			return null;
		}
		
		public ExtendedPGCRWeapons[] getUniqueWeaponKills() {
			List<ExtendedPGCRWeapons> l = optionalCastArray(object.getAsJsonArray("weapons"), ExtendedPGCRWeapons.class);
			return l.toArray(new ExtendedPGCRWeapons[l.size()]);
		}
		
		public int getTotalPrecisionKills() {
			return object.getAsJsonObject("values").getAsJsonObject("precisionKills").getAsJsonObject("basic").getAsJsonPrimitive("value").getAsInt();
		}
		
		public int getTotalGrenadeKills() {
			return object.getAsJsonObject("values").getAsJsonObject("weaponKillsGrenade").getAsJsonObject("basic").getAsJsonPrimitive("value").getAsInt();
		}
		
		public int getTotalMeleeKills() {
			return object.getAsJsonObject("values").getAsJsonObject("weaponKillsMelee").getAsJsonObject("basic").getAsJsonPrimitive("value").getAsInt();
		}
		
		public int getTotalSuperKills() {
			return object.getAsJsonObject("values").getAsJsonObject("weaponKillsSuper").getAsJsonObject("basic").getAsJsonPrimitive("value").getAsInt();
		}
		
		public int getTotalAbillityKills() {
			return object.getAsJsonObject("values").getAsJsonObject("weaponKillsAbility").getAsJsonObject("basic").getAsJsonPrimitive("value").getAsInt();
		}
		
		public float getStatValue(String statName) {
			return object.getAsJsonObject("values").getAsJsonObject(statName).getAsJsonObject("basic").getAsJsonPrimitive("value").getAsFloat();
		}
		
		public String getStatDisplayValue(String statName) {
			return object.getAsJsonObject("values").getAsJsonObject(statName).getAsJsonObject("basic").getAsJsonPrimitive("displayValue").getAsString();
		}
	}
	
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
	
	/**Contains unique weapon kills etc.*/
	public ExtendedPGCRStats getExtended() {
		ExtendedPGCRStats e = new ExtendedPGCRStats();
		e.parse(object.getAsJsonObject("extended"));
		return e;
	}
	
	//TODO do "extended"
}
