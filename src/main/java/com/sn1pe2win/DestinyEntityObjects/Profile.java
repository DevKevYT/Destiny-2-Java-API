package com.sn1pe2win.DestinyEntityObjects;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyDateFormat;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyChecklistDefinition;
import com.sn1pe2win.definitions.MembershipType;

public interface Profile {
	
	public enum ProfileSetType {
		NONE(0), 
		PROFILES(100), 
		VENDOR_RECEIPTS(101),
		PROFILE_INVENTORIES(102),
		PROFILE_CURRENCIES(103),
		PROFILE_PROGRESSION(104);
		
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
		
		//TODO seasonal artifact
	}
}
