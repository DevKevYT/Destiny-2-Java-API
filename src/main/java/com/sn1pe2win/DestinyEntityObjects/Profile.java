package com.sn1pe2win.DestinyEntityObjects;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyDateFormat;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyChecklistDefinition;
import com.sn1pe2win.definitions.DestinyClassDefinition;
import com.sn1pe2win.definitions.DestinyGenderDefinition;
import com.sn1pe2win.definitions.DestinyRaceDefinition;
import com.sn1pe2win.definitions.MembershipType;
import com.sn1pe2win.endpoints.GetActivityHistory;

public interface Profile {
	
	public enum ProfileSetType {
		
		NONE(0), 
		PROFILES(100), 
		VENDOR_RECEIPTS(101),
		PROFILE_INVENTORIES(102),
		PROFILE_CURRENCIES(103),
		PROFILE_PROGRESSION(104),
		PLATFORM_SILVER(105),
		CHARACTERS(200),
		CHARACTER_INVENTORY(201);
		
		public final int components;
		private ProfileSetType(int components) {
			this.components = components;
		}
	}
	
	public abstract class Component extends DestinyEntity {
		
		public static final byte PUBLIC = 1;
		public static final byte PRIVATE = 2;
		
		protected JsonObject data = new JsonObject();
		protected byte privacy = 0;
		
		@Override
		public final JsonObject getRawJson() {
			return data;
		}
		
		@Override
		public final void parse(JsonObject object) {
			privacy = object.getAsJsonPrimitive("privacy").getAsByte();
			this.data = object.getAsJsonObject("data");
			parseData(this.data);
		}
		
		/**Find enums at <br>
		 * {@link Component#PUBLIC} or <br>
		 * {@link Component#PRIVATE}, if this recouce is accessed privately*/
		public final byte getPrivacy() {
			return privacy;
		}
		
		protected abstract void parseData(JsonObject obj);
	}
	
	/**Profile components*/
	public class DestinyProfileComponent extends Component {
		
		public class UserInfo extends DestinyEntity {
			
			private MembershipType[] applicableMembershipTypes;
			JsonObject object = new JsonObject();
			
			public JsonObject getRawJson() {
				return object;
			}
			
			@Override
			public void parse(JsonObject object) {
				this.object = object;
			}
			
			public long getMembershipId() {
				return object.getAsJsonPrimitive("membershipId").getAsLong();
			}

			public MembershipType getCrossSaveOverride() {
				return MembershipType.of(object.getAsJsonPrimitive("crossSaveOverride").getAsShort());
			}

			public MembershipType getMembershipType() {
				return MembershipType.of(object.getAsJsonPrimitive("membershipType").getAsShort());
			}
			
			public boolean isPublic() {
				return object.getAsJsonPrimitive("isPublic").getAsBoolean();
			}
			
			public String getDisplayName() {
				return object.getAsJsonPrimitive("displayName").getAsString();
			}
			
			public MembershipType[] getApplicableMembershipTypes() {
				JsonArray arr = object.getAsJsonArray("applicableMembershipTypes");
				if(applicableMembershipTypes == null) {
					applicableMembershipTypes = new MembershipType[arr.size()];
					for(int i = 0; i < applicableMembershipTypes.length; i++) applicableMembershipTypes[i] = MembershipType.of(arr.get(i).getAsShort());
				}
				return applicableMembershipTypes;
			}
		}
		
		private UserInfo userInfo = null;
		private JsonObject object = new JsonObject();
		
		@Override
		protected void parseData(JsonObject obj) {
			this.object = obj;
		}
		
		public UserInfo getUserInfo() {
			if(userInfo == null) {
				userInfo = new UserInfo();
				userInfo.parse(object.getAsJsonObject("userInfo"));
			}
			return userInfo;
		}
		
		public Date getDateLastPlayed() throws ParseException {
			return DestinyDateFormat.toDate(optionalString(object.getAsJsonPrimitive("dateLastPlayed"), "0000-00-00T00:00:00Z"));
		}

		public int getVersionsOwned() {
			return optionalInt(object.getAsJsonPrimitive("versionsOwned"), 0x0);
		}

		public long[] getCharacterIds() {
			return optionalLongArray(object.getAsJsonArray("characterIds"), new long[] {});
		}

		public long[] getSeasonHashes() {
			return optionalLongArray(object.getAsJsonArray("seasonHashes"), new long[] {});
		}

		public long getCurrentSeasonHash() {
			return optionalLong(object.getAsJsonPrimitive("currentSeasonHash"), 0);
		}

		public int getCurrentSeasonRewardPowerCap() {
			return optionalInt(object.getAsJsonPrimitive("currentSeasonRewardPowerCap"), 0);
		}
		
		/**@param activityType - The activity type enum.
		 * @param count - The amount of entries to load.
		 * @see GetActivityHistory#getCharacterActivityHistory(int, int, MembershipType, long, long...)
		 * @see Profile.CharacterComponent.Character#getCharacterHistory(int, int)
		 * @return The last activities from any character. If you want to handle single characters, use The function below*/
		public Response<PlayerActivity[]> getActivityHistory(int activityType, int count) {
			return GetActivityHistory.getCharacterActivityHistory(activityType, count, getUserInfo().getMembershipType(), getUserInfo().getMembershipId(), getCharacterIds());
		}
	}
	
	public class VendorReceiptsComponent extends Component {

		@Override
		public void parseData(JsonObject obj) {
		}

		//TODO unknown type
		public JsonArray getReceipts() {
			return data.getAsJsonArray("receipts");
		}
	}
	
	public class ProfileInventories extends Component {
		
		
		@Override
		protected void parseData(JsonObject obj) {
		}
		
		public ItemComponent[] getItems() {
			JsonArray arr = data.getAsJsonArray("items");
			ItemComponent[] items = new ItemComponent[arr.size()];
			for(int i = 0; i < arr.size(); i++) {
				items[i] = new ItemComponent();
				items[i].parse(arr.get(i).getAsJsonObject());
			}
			return items;
		}
	}
	
	public class ProfileCurrenciesComponent extends Component {

		@Override
		protected void parseData(JsonObject obj) {
		}
		
		/**NOTE: This function may return an empty array, if the endpoint was
		 * not called with authorisation.*/
		public ItemComponent[] getItems() {
			if(data == null) return new ItemComponent[] {};
			
			JsonArray arr = data.getAsJsonArray("items");
			ItemComponent[] items = new ItemComponent[arr.size()];
			for(int i = 0; i < arr.size(); i++) {
				items[i] = new ItemComponent();
				items[i].parse(arr.get(i).getAsJsonObject());
			}
			return items;
		}
	}
	
	public class ProfileProgressionComponent extends Component {

		public class CheckList extends DestinyEntity {
			
			public class CheckListEntry {
				
				private long hashIdentifier = 0;
				private boolean obtained = false;
				
				public long getHashIdentifier() {
					return hashIdentifier;
				}
				
				public boolean obtained() {
					return obtained;
				}
			}
			
			JsonObject checklist;
			private long hashIdentifier;
			
			@Override
			public JsonObject getRawJson() {
				return checklist;
			}
			
			@Override
			public void parse(JsonObject object) {
				this.checklist = object;
			}
			
			public long getHashIdentifier() {
				return hashIdentifier;
			}
			
			public CheckListEntry[] getChecklistEntries() {
				Object[] set = checklist.entrySet().toArray();
				CheckListEntry[] checklist = new CheckListEntry[set.length];
				
				for(int i = 0; i < set.length; i++) {
					@SuppressWarnings("unchecked")
					Entry<String, JsonElement> entry = (Entry<String, JsonElement>) set[i];
					checklist[i] = new CheckListEntry();
					checklist[i].hashIdentifier = Long.valueOf(entry.getKey());
					checklist[i].obtained = entry.getValue().getAsBoolean();
					//This should be every character
				}
				
				return checklist;
			}
			
			@SuppressWarnings("unchecked")
			public Response<DestinyChecklistDefinition> getChecklistDefinition() {
				return (Response<DestinyChecklistDefinition>) new DestinyChecklistDefinition(hashIdentifier).getAsResponse();
			}
		}
		
		public class SeasonalArtifact extends DestinyEntity {
			
			JsonObject object;
			@Override
			public JsonObject getRawJson() {
				return object;
			}

			@Override
			public void parse(JsonObject object) {
				this.object = object;
			}
			
			public long getArtifactHash() {
				return object.getAsJsonPrimitive("artifactHash").getAsLong();
			}
			
			public int getPointsAquired() {
				return object.getAsJsonPrimitive("pointsAcquired").getAsInt();
			}
			
			public int getPowerbonus() {
				return object.getAsJsonPrimitive("powerBonus").getAsInt();
			}
			
			public LevelProgression getPointProgression() {
				LevelProgression p = new LevelProgression();
				p.parse(object.getAsJsonObject("pointProgression"));
				return p;
			}
			
			public LevelProgression getPowerBonusProgression() {
				LevelProgression p = new LevelProgression();
				p.parse(object.getAsJsonObject("powerBonusProgression"));
				return p;
			}
		}
		
		@Override
		protected void parseData(JsonObject obj) {
		}
		
		public CheckList[] getChecklists() {
			Object[] set = data.getAsJsonObject("checklists").entrySet().toArray();
			ArrayList<CheckList> checklist = new ArrayList<CheckList>(set.length);
			
			for(int i = 0; i < set.length; i++) {
				@SuppressWarnings("unchecked")
				Entry<String, JsonElement> entry = (Entry<String, JsonElement>) set[i];
				
				if(!entry.getValue().getAsJsonObject().entrySet().isEmpty()) {
					CheckList list = new CheckList();
					list.hashIdentifier = Long.valueOf(entry.getKey());
					list.parse(entry.getValue().getAsJsonObject());
					checklist.add(list);
				}
				//This should be every character
			}
			
			return checklist.toArray(new CheckList[checklist.size()]);
		}
		
		public SeasonalArtifact getSeasonalArtifact() {
			SeasonalArtifact a = new SeasonalArtifact();
			a.parse(data.getAsJsonObject("seasonalArtifact"));
			return a;
		}
	}
	
	public class PlatformSilverComponent extends Component {

		@Override
		protected void parseData(JsonObject obj) {
			
		}
		
		/**@return null, if this recourse was accessed without an access token*/
		public ItemComponent getPSNSilver() {
			if(data == null) return null;
			ItemComponent psn = new ItemComponent();
			psn.parse(data.getAsJsonObject("platformSilver").getAsJsonObject("TigerPsn"));
			return psn;
		}
		
		/**@return null, if this recourse was accessed without an access token*/
		public ItemComponent getXBOXSilver() {
			if(data == null) return null;
			ItemComponent psn = new ItemComponent();
			psn.parse(data.getAsJsonObject("platformSilver").getAsJsonObject("TigerXbox"));
			return psn;
		}
		
		/**@return null, if this recourse was accessed without an access token*/
		public ItemComponent getBlizzardSilver() {
			if(data == null) return null;
			ItemComponent psn = new ItemComponent();
			psn.parse(data.getAsJsonObject("platformSilver").getAsJsonObject("TigerBlizzard"));
			return psn;
		}
		
		/**@return null, if this recourse was accessed without an access token*/
		public ItemComponent getStadiaSilver() {
			if(data == null) return null;
			ItemComponent psn = new ItemComponent();
			psn.parse(data.getAsJsonObject("platformSilver").getAsJsonObject("TigerStadia"));
			return psn;
		}
		
		/**@return null, if this recourse was accessed without an access token*/
		public ItemComponent getSteamSilver() {
			if(data == null) return null;
			ItemComponent psn = new ItemComponent();
			psn.parse(data.getAsJsonObject("platformSilver").getAsJsonObject("TigerSteam"));
			return psn;
		}
		
		/**@return null, if this recourse was accessed without an access token*/
		public ItemComponent getBungieNextSilver() {
			if(data == null) return null;
			ItemComponent psn = new ItemComponent();
			psn.parse(data.getAsJsonObject("platformSilver").getAsJsonObject("BungieNext"));
			return psn;
		}
	}
	
	public class CharacterComponent extends Component {
		
		private Character[] characters;
		
		public class Character extends DestinyEntity {
			
			private long characterId;
			private JsonObject object;
			
			@Override
			public JsonObject getRawJson() {
				return object;
			}

			@Override
			public void parse(JsonObject object) {
				this.object = object;
				
			}
			
			public Response<PlayerActivity[]> getCharacterHistory(int activityType, int count) {
				return GetActivityHistory.getCharacterActivityHistory(activityType, count, getMembershipType(), getMembershipId(), characterId);
			}
			
			public long getCharacterId() {
				return characterId;
			}
			
			public long getMembershipId() {
				return object.getAsJsonPrimitive("membershipId").getAsLong();
			}
			
			public MembershipType getMembershipType() {
				return MembershipType.of(object.getAsJsonPrimitive("membershipType").getAsShort());
			}
			
			public Date getDateLastPlayed() throws ParseException {
				return DestinyDateFormat.toDate(object.getAsJsonPrimitive("characterId").getAsString());
			}
			
			public int getMinutesPlayedLastSession() {
				return object.getAsJsonPrimitive("minutesPlayedThisSession").getAsInt();
			}
			
			public int getMinutesPlayedTotal() {
				return object.getAsJsonPrimitive("minutesPlayedTotal").getAsInt();
			}
			
			public int getLightLevel() {
				return object.getAsJsonPrimitive("light").getAsInt();
			}
			
			/**Stuff like discipline, strength, intellect etc.*/
			public HashPair[] getStats() {
				return HashPair.fromJsonObject(object.getAsJsonObject("stats"));
			}
			
			@SuppressWarnings("unchecked")
			public Response<DestinyRaceDefinition> getRace() {
				return (Response<DestinyRaceDefinition>) new DestinyRaceDefinition(object.getAsJsonPrimitive("raceHash").getAsLong()).getAsResponse();
			}
			
			@SuppressWarnings("unchecked")
			public Response<DestinyGenderDefinition> getGender() {
				return (Response<DestinyGenderDefinition>) new DestinyGenderDefinition(object.getAsJsonPrimitive("genderHash").getAsLong()).getAsResponse();
			}
			
			@SuppressWarnings("unchecked")
			public Response<DestinyClassDefinition> getClassType() {
				return (Response<DestinyClassDefinition>) new DestinyClassDefinition(object.getAsJsonPrimitive("classHash").getAsLong()).getAsResponse();
			}
			
			public String getEmblemPath() {
				return object.getAsJsonPrimitive("emblemPath").getAsString();
			}
			
			public String getEmblemBackground() {
				return object.getAsJsonPrimitive("emblemBackgroundPath").getAsString();
			}
			
			//TODO item hash
			public long getEmblemItemHash() {
				return object.getAsJsonPrimitive("emblemHash").getAsLong();
			}
			
			public Color getEmblemColor() {
				Color c = new Color();
				c.parse(object.getAsJsonObject("emblemColor"));
				return c;
			}
			
			public LevelProgression getLevelProgression() {
				return cast(object.getAsJsonObject("levelProgression"), LevelProgression.class);
			}
			
			public int getBaseCharacterLevel() {
				return object.getAsJsonPrimitive("baseCharacterLevel").getAsInt(); 
			}
			
			public int getPercentToNextLevel() {
				return object.getAsJsonPrimitive("percentToNextLevel").getAsInt(); 
			}
		}
		
		@Override
		protected void parseData(JsonObject obj) {
			REQUEST_TYPE = "Destiny2.GetProfile:Characters";
		}
		
		public Character[] getCharacters() {
			if(characters == null) {
				Object[] set = data.entrySet().toArray();
				characters = new Character[set.length];
				
				for(int i = 0; i < set.length; i++) {
					@SuppressWarnings("unchecked")
					Entry<String, JsonElement> entry = (Entry<String, JsonElement>) set[i];
					
					characters[i] = new Character();
					characters[i].characterId = Long.valueOf(entry.getKey());
					characters[i].parse(entry.getValue().getAsJsonObject());
				}
			}
			return characters;
		}
	}
	
	public class CharacterInventoryComponent extends Component {

		public class CharacterInventory extends DestinyEntity {

			JsonObject object;
			private long characterId = 0;
			
			@Override
			public JsonObject getRawJson() {
				return object;
			}

			@Override
			public void parse(JsonObject object) {
				this.object = object;
			}
			
			public long getCharacterId() {
				return characterId;
			}
			
			public InventoryItem[] getItems() {
				List<InventoryItem> l = castArray(object.getAsJsonArray("items"), InventoryItem.class);
				return l.toArray(new InventoryItem[l.size()]);
			}
		}
		
		@Override
		protected void parseData(JsonObject obj) {
			REQUEST_TYPE = "Destiny2.GetProfile:CharacterInventories";
		}
		
		public CharacterInventory[] getCharacters() {
			Object[] set = data.entrySet().toArray();
			CharacterInventory[] inv = new CharacterInventory[set.length];
			
			for(int i = 0; i < set.length; i++) {
				@SuppressWarnings("unchecked")
				Entry<String, JsonElement> entry = (Entry<String, JsonElement>) set[i];
				
				inv[i] = new CharacterInventory();
				inv[i].characterId = Long.valueOf(entry.getKey());
				inv[i].parse(entry.getValue().getAsJsonObject());
			}
			return inv;
		}
	}
}
