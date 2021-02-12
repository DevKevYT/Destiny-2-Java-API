package com.sn1pe2win.DestinyEntityObjects;

import java.text.ParseException;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyDateFormat;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.DestinyInventoryBucketDefinition;
import com.sn1pe2win.definitions.MembershipType;

public interface Profile {
	
	public enum ProfileSetType {
		NONE(0), 
		PROFILES(100), 
		VENDOR_RECEIPTS(101),
		PROFILE_INVENTORIES(102);
		
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
		
		public class DestinyItemComponent extends DestinyEntity {

			JsonObject obj = new JsonObject();
			
			@Override
			public JsonObject getRawJson() {
				return obj;
			}

			@Override
			public void parse(JsonObject object) {
				this.obj = object;
			}
			
			public long getItemHash() {
				return obj.getAsJsonPrimitive("itemHash").getAsLong();
			}
			
			public int getQuantity() {
				return obj.getAsJsonPrimitive("quantity").getAsInt();
			}
			//TODO item enums
			public int getBindStatus() {
				return obj.getAsJsonPrimitive("bindStatus").getAsInt();
			}
			
			public int getLocation() {
				return obj.getAsJsonPrimitive("location").getAsInt();
			}
			
			public int getState() {
				return obj.getAsJsonPrimitive("state").getAsInt();
			}
			
			public long getBucketHash() {
				return obj.getAsJsonPrimitive("bucketHash").getAsLong();
			}
			
			@SuppressWarnings("unchecked")
			public Response<DestinyInventoryBucketDefinition> getBucket() {
				return (Response<DestinyInventoryBucketDefinition>) new DestinyInventoryBucketDefinition(obj.getAsJsonPrimitive("bucketHash").getAsLong()).getAsResponse();
			}
			
			public int getTransferStatus() {
				return obj.getAsJsonPrimitive("transferStatus").getAsInt();
			}
			
			public boolean isLockable() {
				return obj.getAsJsonPrimitive("lockable").getAsBoolean();
			}
			
			public int getDismantlePermission() {
				return obj.getAsJsonPrimitive("dismantlePermission").getAsInt();
			}
			
			public boolean isWrapper() {
				return obj.getAsJsonPrimitive("isWrapper").getAsBoolean();
			}
		}
		
		@Override
		protected void parseData(JsonObject obj) {
		}
		
		public DestinyItemComponent[] getItems() {
			JsonArray arr = data.getAsJsonArray("items");
			DestinyItemComponent[] items = new DestinyItemComponent[arr.size()];
			for(int i = 0; i < arr.size(); i++) {
				items[i] = new DestinyItemComponent();
				items[i].parse(arr.get(i).getAsJsonObject());
			}
			return items;
		}
	}
}
