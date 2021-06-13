package com.sn1pe2win.endpoints;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sn1pe2win.DestinyEntityObjects.GroupMember;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Gateway;
import com.sn1pe2win.core.Response;

public class GetMembersOfGroup extends DestinyEntity {

	
	public final long groupId;
	private Response<JsonObject> originalResponse;
	private JsonObject object;
	
	private ArrayList<GroupMember> members;
	private int results = 0;
	
	public GetMembersOfGroup(long groupId) {
		this.groupId = groupId;
		
		update();
	}
	
	/**Reloads the data*/
	public void update() {
		members = new ArrayList<GroupMember>();
		int page = 1;
		results = 0;
		while(true) {
			originalResponse = Gateway.sendGet("/GroupV2/" + groupId + "/Members/?currentpage=" + page);
			if(originalResponse.success()) {
				JsonArray array = originalResponse.getResponseData().getAsJsonObject("Response").getAsJsonArray("results");
				for(int i = 0; i < array.size(); i++) {
					GroupMember m = new GroupMember();
					m.parse(array.get(i).getAsJsonObject());
					members.add(m);
					results ++;
				}
				if(originalResponse.getResponseData().getAsJsonObject("Response").getAsJsonPrimitive("hasMore").getAsBoolean()) {
					page++;
				} else break;
			} else break;
		}
		parse(originalResponse.getResponseData());
	}

	@Override
	public JsonObject getRawJson() {
		return object;
	}

	@Override
	public void parse(JsonObject object) {
		this.object = object;
	}
	
	public Response<JsonObject> getResponse() {
		return originalResponse;
	}
	
	public int getMemberCount() {
		return results;
	}
	
	public GroupMember[] getMembers() {
		return members.toArray(new GroupMember[members.size()]);
	}

}
