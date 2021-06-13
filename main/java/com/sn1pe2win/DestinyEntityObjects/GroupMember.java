package com.sn1pe2win.DestinyEntityObjects;

import java.text.ParseException;

import com.google.gson.JsonObject;
import com.sn1pe2win.core.DestinyDateFormat;

public class GroupMember extends com.sn1pe2win.core.DestinyEntity {

	public static final int MEMBERTYPE_BEGINNER = 1;
	public static final int MEMBERTYPE_MEMBER = 2;
	public static final int MEMBERTYPE_ADMIN = 3;
	public static final int MEMBERTYPE_FOUNDER = 3;
	
	JsonObject object;
	
	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}

	public boolean isOnline() {
		return object.getAsJsonPrimitive("isOnline").getAsBoolean();
	}
	
	public int getMemberType() {
		return object.getAsJsonPrimitive("memberType").getAsInt();
	}
	
	/**String for whatever reason*/
	public String getGroupId() {
		return object.getAsJsonPrimitive("groupId").getAsString();
	}
	
	public java.util.Date getJoinDate() throws ParseException {
		return DestinyDateFormat.toDate(object.getAsJsonPrimitive("joinDate").getAsString());
	}
	
	//TODO bungie.net user info
	public DestinyUserInfo getDestinyUserInfo() {
		DestinyUserInfo userInfo = new DestinyUserInfo();
		userInfo.parse(object.getAsJsonObject("destinyUserInfo"));
		return userInfo;
	}
}
