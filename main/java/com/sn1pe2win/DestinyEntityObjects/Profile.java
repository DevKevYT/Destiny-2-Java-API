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
import com.sn1pe2win.definitions.DestinyActivityDefinition;
import com.sn1pe2win.definitions.DestinyActivityModeDefinition;
import com.sn1pe2win.definitions.DestinyChecklistDefinition;
import com.sn1pe2win.definitions.DestinyClassDefinition;
import com.sn1pe2win.definitions.DestinyGenderDefinition;
import com.sn1pe2win.definitions.DestinyRaceDefinition;
import com.sn1pe2win.definitions.MembershipType;
import com.sn1pe2win.definitions.OptionalValue;
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
		CHARACTER_INVENTORY(201),
		CHARACTER_ACTIVITIES(204),
		PROFILE_RECORDS(900),
		COLLECTIBLES(800),
		ITEM_OBJECTIVES(301),
		PROFILE_METRICS(1100);
		
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
				return DestinyDateFormat.toDate(object.getAsJsonPrimitive("dateLastPlayed").getAsString());
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
	
	public class CharacterActivitiesComponent extends Component {
		
		public class CharacterActivity extends DestinyEntity {
			
			JsonObject object;
			private long characterId;
			
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
			
			public Date getDateActivityStarted() {
				try {
					return DestinyDateFormat.toDate(object.getAsJsonPrimitive("dateActivityStarted").getAsString());
				} catch (ParseException e) {
					e.printStackTrace();
					return new Date();
				}
			}
			
			public AvailableActivities[] getAvailableActivities() {
				List<AvailableActivities> l = castArray(object.getAsJsonArray("availableActivities"), AvailableActivities.class);
				return l.toArray(new AvailableActivities[l.size()]);
			}
			
			@OptionalValue
			public long getCurrentActivityHash() {
				return object.getAsJsonPrimitive("currentActivityHash").getAsLong();
			}
			
			@SuppressWarnings("unchecked")
			public Response<DestinyActivityDefinition> getCurrentActivity() {
				return (Response<DestinyActivityDefinition>) new DestinyActivityDefinition(getCurrentActivityHash()).getAsResponse();
			}
			
			@OptionalValue
			public long getActivityModeHash() {
				return object.getAsJsonPrimitive("currentActivityModeHash").getAsLong();
			}
			
			/**Optional, since the "Orbit" has no mode for some reason. The hash exists anyway, pointing nowhere*/
			@SuppressWarnings("unchecked")
			@OptionalValue
			public Response<DestinyActivityModeDefinition> getActivityMode() {
				return (Response<DestinyActivityModeDefinition>) new DestinyActivityModeDefinition(getActivityModeHash()).getAsResponse();
			}
			
			@OptionalValue
			public long getCurrentPlaylistActivityHash() {
				return optionalLong(object.getAsJsonPrimitive("currentPlaylistActivityHash"), 0);
			}
			
			@SuppressWarnings("unchecked")
			public Response<DestinyActivityDefinition> getCurrentPlaylist() {
				return (Response<DestinyActivityDefinition>) new DestinyActivityDefinition(getCurrentPlaylistActivityHash()).getAsResponse();
			}
		}

		@Override
		protected void parseData(JsonObject obj) {
		}
		
		public CharacterActivity[] getCharacters() {
			Object[] set = data.entrySet().toArray();
			CharacterActivity[] inv = new CharacterActivity[set.length];
			
			for(int i = 0; i < set.length; i++) {
				@SuppressWarnings("unchecked")
				Entry<String, JsonElement> entry = (Entry<String, JsonElement>) set[i];
				
				inv[i] = new CharacterActivity();
				inv[i].characterId = Long.valueOf(entry.getKey());
				inv[i].parse(entry.getValue().getAsJsonObject());
			}
			return inv;
		}

		public CharacterActivity getCharacter(long characterId) {
			for(CharacterActivity a : getCharacters()) {
				if(a.getCharacterId() == characterId) return a;
			}
			return null;
		}
	}
	
	public class ProfileTriumphsComponent extends Component {

		public class Triumph extends DestinyEntity {
				
			JsonObject object;
			long hash;
			String bitmask = "00000000";
			
			//Bitmask Index
			public static final byte OBJECTIVE_NOT_COMPLETED = 9 -  2;
			public static final byte OBJECTIVE_INVISIBLE = 9 - 4;
			public static final byte OBJECTIVE_UNAVAILABLE = 9 - 1;
			public static final byte OBJECTIVE_REDEEMED = 9 - 0;
			//Whatever the hell THIS is
			public static final byte OBJECTIVE_OBSCURED = 9 - 3;
			public static final byte OBJECTTIVE_NOT_PERMITTED = 9 - 5;
			public static final byte OBJECTIVE_IS_TITLE = 9 - 6;
			
			@Override
			public JsonObject getRawJson() {
				return object;
			}

			@Override
			public void parse(JsonObject object) {
				this.object = object;
				bitmask = Integer.toBinaryString(getBitmaskState());
				if(bitmask.length() < 10) {
					String mask = "";
					for(int i = 0; i < 10 - bitmask.length(); i++) mask += "0";
					bitmask = mask + bitmask;
				}
			}
			
			public boolean isRedeemed() {
				return bitmask.charAt(OBJECTIVE_NOT_COMPLETED) == '1';
			}
			
			public boolean isAvailable() {
				return bitmask.charAt(OBJECTIVE_UNAVAILABLE) == '0';
			}
			
			public boolean isCompleted() {
				return bitmask.charAt(OBJECTIVE_NOT_COMPLETED) == '0';
			}
			
			public boolean isObscured() {
				return bitmask.charAt(OBJECTIVE_OBSCURED) == '1';
			}
			
			public boolean isInvisible() {
				return bitmask.charAt(OBJECTIVE_INVISIBLE) == '1';
			}
			
			public boolean userNotPermittedToComplete() {
				return bitmask.charAt(OBJECTTIVE_NOT_PERMITTED) == '1';
			}
			
			public boolean isTitle() {
				return bitmask.charAt(OBJECTIVE_IS_TITLE) == '1';
			}
			
			public long getTriumphHash() {
				return hash;
			}
			
			public int getBitmaskState() {
				return object.getAsJsonPrimitive("state").getAsInt();
			}
			
			@OptionalValue
			public Objective[] getObjectives() {
				List<Objective> o = optionalCastArray(object.getAsJsonArray("objectives"), Objective.class);
				return o.toArray(new Objective[o.size()]);
			}
			
			public Objective getObjectiveByHash(long objectiveHash) {
				List<Objective> o = optionalCastArray(object.getAsJsonArray("objectives"), Objective.class);
				for(Objective obj : o) {
					if(obj.getObjectiveHash() == objectiveHash) return obj;
				}
				return null;
			}
			
			@OptionalValue
			public Objective[] getIntervalObjectives() {
				List<Objective> o = optionalCastArray(object.getAsJsonArray("intervalObjectives"), Objective.class);
				return o.toArray(new Objective[o.size()]);
			}
			
			public int getIntervalsRedeemedCount() {
				return object.getAsJsonPrimitive("intervalsRedeemedCount").getAsInt();
			}
		}
		
		@Override
		protected void parseData(JsonObject obj) {
		}
		
		public int getTriumphScore() {
			return data.getAsJsonPrimitive("score").getAsInt();
		}
		
		public int getLegacyTriumphScore() {
			return data.getAsJsonPrimitive("legacyScore").getAsInt();
		}
		
		public int getLifetimeScore() {
			return data.getAsJsonPrimitive("lifetimeScore").getAsInt();
		}
		
		public long getRecordCategoriesRootNodeHash() {
			return data.getAsJsonPrimitive("recordCategoriesRootNodeHash").getAsLong();
		}
		
		public long getRecordSealsRootNodeHash() {
			return data.getAsJsonPrimitive("recordSealsRootNodeHash").getAsLong();
		}
		
		public Triumph[] getTriumphs() {
			Object[] set = data.getAsJsonObject("records").entrySet().toArray();
			Triumph[] inv = new Triumph[set.length];
			
			for(int i = 0; i < set.length; i++) {
				@SuppressWarnings("unchecked")
				Entry<String, JsonElement> entry = (Entry<String, JsonElement>) set[i];
				
				inv[i] = new Triumph();
				inv[i].hash = Long.valueOf(entry.getKey());
				inv[i].parse(entry.getValue().getAsJsonObject());
			}
			return inv;
		}
		
		public Triumph getTriumph(long hash) {
			for(Triumph t : getTriumphs()) {
				if(t.hash == hash) return t;
			}
			return null;
		}
		
		//TODO Character Triumphs
	}
	
	public class ObjectiveProgress extends DestinyEntity {
		
		JsonObject object;
		
		@Override
		public JsonObject getRawJson() {
			return object;
		}

		@Override
		public void parse(JsonObject object) {
			this.object = object;
		}
		
		public long getObjectiveHash() {
			return object.getAsJsonPrimitive("objectiveHash").getAsLong();
		}
		
		public int getProgress() {
			return object.getAsJsonPrimitive("progress").getAsInt();
		}
		
		public int getCompletionValue() {
			return object.getAsJsonPrimitive("completionValue").getAsInt();
		}
		
		public boolean isCompleted() {
			return object.getAsJsonObject("complete").getAsBoolean();
		}
	}
	
	public class MetricsComponent extends Component {

		public class MetricsEntry extends DestinyEntity {
			
			JsonObject object;
			public long metricsHash;
			
			@Override
			public JsonObject getRawJson() {
				return object;
			}

			@Override
			public void parse(JsonObject object) {
				this.object = object;
			}
			
			public boolean isVisible() {
				return object.getAsJsonPrimitive("invisible").getAsBoolean() == false;
			}
			
			public ObjectiveProgress getObjectiveProgress() {
				ObjectiveProgress o = new ObjectiveProgress();
				o.parse(object.getAsJsonObject("objectiveProgress"));
				return o;
			}
			
		}
		
		@Override
		protected void parseData(JsonObject obj) {
		}
		
		public long getMetricsRootNodeHash() {
			return data.getAsJsonPrimitive("metricsRootNodeHash").getAsLong();
		}
		//TODO Load definition
		public MetricsEntry getMetric(long hash) {
			for(MetricsEntry e : getMetrics()) {
				if(e.metricsHash == hash) return e;
			}
			return null;
		}
		
		public MetricsEntry[] getMetrics() {
			Object[] set = data.getAsJsonObject("metrics").entrySet().toArray();
			MetricsEntry[] inv = new MetricsEntry[set.length];
			
			for(int i = 0; i < set.length; i++) {
				@SuppressWarnings("unchecked")
				Entry<String, JsonElement> entry = (Entry<String, JsonElement>) set[i];
				
				inv[i] = new MetricsEntry();
				inv[i].metricsHash = Long.valueOf(entry.getKey());
				inv[i].parse(entry.getValue().getAsJsonObject());
			}
			return inv;
		}
	}
	
	public class CollectibleComponent extends DestinyEntity {
		JsonObject object;
		
		public class Collectible extends DestinyEntity {
			long hash;
			JsonObject object;
			String bitmask = "00000000";
			
			/*1: Not aquired
			2: Obscured
			4: Invisible
			8: CannotAffordMaterials
			16: Inventory Space unavailable
			32: Uniqueness Violation
			64: Purchase Disabled*/
			public static final byte COLLECTIBLE_NOT_AQUIRED = 9 - 0;
			public static final byte COLLECTIBLE_OBSCURED = 9 - 1;
			public static final byte COLLECTIBLE_INVISIBLE = 9 - 2;
			public static final byte COLLECTIBLE_CANNOT_AFFORD_MATERIALS = 9 - 3;
			public static final byte COLLECTIBLE_NOT_ENOUGH_SPACE = 9 - 4;
			public static final byte COLLECTIBLE_UNIQUENESS_VIOLATION = 9 - 5;
			public static final byte COLLECTIBLE_PURCHASE_DISABLED = 9 - 6;
			
			@Override
			public JsonObject getRawJson() {
				return object;
			}
			
			@Override
			public void parse(JsonObject object) {
				this.object = object;
				bitmask = Integer.toBinaryString(getStateBitmask());
				if(bitmask.length() < 10) {
					String mask = "";
					for(int i = 0; i < 10 - bitmask.length(); i++) mask += "0";
					bitmask = mask + bitmask;
				}
			}
			
			public String getBitmask() {
				return bitmask;
			}
			
			public int getStateBitmask() {
				return object.getAsJsonPrimitive("state").getAsInt();
			}
			
			public long getHash() {
				return hash;
			}
			
			public boolean isAquired() {
				return bitmask.charAt(COLLECTIBLE_NOT_AQUIRED) == '0';
			}
			
			public boolean isObscured() {
				return bitmask.charAt(COLLECTIBLE_OBSCURED) == '1';
			}
			
			public boolean isInvisible() {
				return bitmask.charAt(COLLECTIBLE_INVISIBLE) == '1';
			}
			
			public boolean canAffordMaterials() {
				return bitmask.charAt(COLLECTIBLE_CANNOT_AFFORD_MATERIALS) == '0';
			}
			
			public boolean hasEnoughInventorySpace() {
				return bitmask.charAt(COLLECTIBLE_NOT_ENOUGH_SPACE) == '0';
			}
			
			public boolean isUniquenessViolation() {
				return bitmask.charAt(COLLECTIBLE_UNIQUENESS_VIOLATION) == '0';
			}
			
			public boolean canBePuchased() {
				return bitmask.charAt(COLLECTIBLE_PURCHASE_DISABLED) == '0';
			}
			
			//TODO connect hash to item
		}
		
		public class ProfileCollectibleComponent extends DestinyEntity {
			public JsonObject object;
			
			
			@Override
			public JsonObject getRawJson() {
				return object;
			}
			
			@Override
			public void parse(JsonObject object) {
				this.object = object;
			}
			
			public long[] getRecentCollectibleHashes() {
				return optionalLongArray(object.getAsJsonArray("recentCollectibleHashes"), new long[] {});
			}
			
			public long[] getNewnessFlaggedCollectibleHashes() {
				return optionalLongArray(object.getAsJsonArray("newnessFlaggedCollectibleHashes"), new long[] {});
			}
			
			Collectible[] cache;
			
			public Collectible[] getCollectibles() {
				if(cache != null) return cache;
				
				Object[] set = object.getAsJsonObject("collectibles").entrySet().toArray();
				Collectible[] inv = new Collectible[set.length];
				
				for(int i = 0; i < set.length; i++) {
					@SuppressWarnings("unchecked")
					Entry<String, JsonElement> entry = (Entry<String, JsonElement>) set[i];
					
					inv[i] = new Collectible();
					inv[i].hash = Long.valueOf(entry.getKey());
					inv[i].parse(entry.getValue().getAsJsonObject());
				}
				if(cache == null) cache = inv;
				
				return inv;
			}
			
			public Collectible getCollectible(long hash) {
				for(Collectible c : getCollectibles()) {
					if(c.getHash() == hash) return c;
				}
				return null;
			}
	
			@OptionalValue
			/**Only available on profile collectibles*/
			public long getCollectionCategoriesRootNodeHash() {
				return object.getAsJsonPrimitive("collectionCategoriesRootNodeHash").getAsLong();
			}
			
			@OptionalValue
			/**Only available on profile collectibles*/
			public long getCollectionBadgesRootNodeHash() {
				return object.getAsJsonPrimitive("collectionBadgesRootNodeHash").getAsLong();
			}
		}
		
		public class CharacterCollectibles extends ProfileCollectibleComponent {
			
			private long characterHash;
			
			public long getCharacterHash() {
				return characterHash;
			}
		}

		@Override
		public JsonObject getRawJson() {
			return object;
		}

		@Override
		public void parse(JsonObject object) {
			this.object = object;
		}
		
		public ProfileCollectibleComponent getProfileCollectibles() {
			ProfileCollectibleComponent c = new ProfileCollectibleComponent();
			c.parse(object.getAsJsonObject("profileCollectibles").getAsJsonObject("data"));
			return c;
		}
		
		public CharacterCollectibles[] getCharacterCollectibles() {
			Object[] set = object.getAsJsonObject("characterCollectibles").getAsJsonObject("data").entrySet().toArray();
			CharacterCollectibles[] inv = new CharacterCollectibles[set.length];
			
			for(int i = 0; i < set.length; i++) {
				@SuppressWarnings("unchecked")
				Entry<String, JsonElement> entry = (Entry<String, JsonElement>) set[i];
				
				inv[i] = new CharacterCollectibles();
				inv[i].characterHash = Long.valueOf(entry.getKey());
				inv[i].parse(entry.getValue().getAsJsonObject());
			}
			return inv;
		}
		//TODO Character collectibles
	}
	
	/**Current Character objectives inside the inventory
	 * Does not extend componend, cause this is a special occurrence*/
	public class ItemObjectives extends DestinyEntity {

		JsonObject object;
		JsonObject uninstanced;
		
		public class CharacterItemObjectives extends Component {

			public class CharacterItemObjective extends DestinyEntity {

				JsonObject object;
				long objectiveHash;
				
				@Override
				public JsonObject getRawJson() {
					return object;
				}

				@Override
				public void parse(JsonObject object) {
					this.object = object;
				}
				
				public ObjectiveProgress[] getObjectives() {
					List<ObjectiveProgress> arr = castArray(object.getAsJsonArray("objectives"), ObjectiveProgress.class);
					return arr.toArray(new ObjectiveProgress[arr.size()]);
				}
			}
			
			long characterId;
			JsonObject object;
			
			@Override
			protected void parseData(JsonObject obj) {
			}
			
			public CharacterItemObjective getObjective(long objectiveHash) {
				CharacterItemObjective i = new CharacterItemObjective();
				i.parse(data.getAsJsonObject(String.valueOf(objectiveHash)));
				i.objectiveHash = objectiveHash;
				return i;
			}
			 
		}
		
		public CharacterItemObjectives[] getCharacterItemObjectives() {
			Object[] set = uninstanced.entrySet().toArray();
			CharacterItemObjectives[] inv = new CharacterItemObjectives[set.length];
			
			for(int i = 0; i < set.length; i++) {
				@SuppressWarnings("unchecked")
				Entry<String, JsonElement> entry = (Entry<String, JsonElement>) set[i];
				
				inv[i] = new CharacterItemObjectives();
				inv[i].characterId = Long.valueOf(entry.getKey());
				inv[i].parse(entry.getValue().getAsJsonObject().getAsJsonObject("objectives"));
			}
			return inv;
		}
		
		public CharacterItemObjectives getCharacterItemObjectives(long characterId) {
			Object[] set = uninstanced.entrySet().toArray();
			
			for(int i = 0; i < set.length; i++) {
				@SuppressWarnings("unchecked")
				Entry<String, JsonElement> entry = (Entry<String, JsonElement>) set[i];
				if(characterId == Long.valueOf(entry.getKey())) {
					
					CharacterItemObjectives co = new CharacterItemObjectives();
					co.characterId = characterId;
					co.parse(entry.getValue().getAsJsonObject().getAsJsonObject("objectives"));
					return co;
				}
			}
			return null;
		}


		@Override
		public JsonObject getRawJson() {
			return object;
		}


		@Override
		/**Just pass the response here*/
		public void parse(JsonObject object) {
			this.object = object;
			uninstanced = object.getAsJsonObject("characterUninstancedItemComponents");
		}
		
		//TODO handle itemComponents (Usually private)
	}
}
