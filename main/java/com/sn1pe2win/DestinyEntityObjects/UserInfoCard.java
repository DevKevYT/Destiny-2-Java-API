package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.definitions.MembershipType;
import com.sn1pe2win.endpoints.GetProfile;

/**Contains profile data from a searched profile
 * Type: Destiny2.SearchDestinyPlayer*/
public class UserInfoCard extends DestinyEntity {

	private String iconPath = "";
	private MembershipType membershipType;
	private long membershipId = 0;
	private String displayName = "";
	
	JsonObject object;
	
	public UserInfoCard() {
		REQUEST_TYPE = "Destiny2.SearchDestinyPlayer";
	}

	@Override
	public JsonObject getRawJson() {
		return object;
	}

	/**Response object*/
	@Override
	public void parse(JsonObject object) {
		iconPath = object.getAsJsonPrimitive("iconPath").getAsString();
		membershipType = MembershipType.of(object.getAsJsonPrimitive("membershipType").getAsShort());
		membershipId = object.getAsJsonPrimitive("membershipId").getAsLong();
		displayName = object.getAsJsonPrimitive("displayName").getAsString();
		this.object = object;
	}


	public String getIconPath() {
		return iconPath;
	}

	public MembershipType getMembershipType() {
		return membershipType;
	}

	public long getMembershipId() {
		return membershipId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public GetProfile loadProfile() {
		GetProfile getProfile = new GetProfile(membershipType, membershipId);
		return getProfile;
	}
	
	public GetProfile loadProfile(String accessToken) {
		GetProfile getProfile = new GetProfile(accessToken, membershipType, membershipId);
		return getProfile;
	}
}
