package com.sn1pe2win.endpoints;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sn1pe2win.DestinyEntityObjects.UserInfoCard;
import com.sn1pe2win.core.Gateway;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.MembershipType;

public class SearchDestinyPlayer {

	/**@return A list of unloaded destiny members. If you want to use any of them as a linked account,<br>
	 * you would need to call {@link DestinyProfile#link(Member, Node)}
	 * @param gamertag - The name of the player to search
	 * @param platform - Use {@link MembershipType#ALL} to search all platforms*/
	public static Response<UserInfoCard[]> queryPlayers(String gamertag, MembershipType platform) {
		Response<JsonObject> response = Gateway.sendGet("/Destiny2/SearchDestinyPlayer/" + platform.id + "/" + gamertag + "/");
		if(!response.success()) return new Response<UserInfoCard[]>(new UserInfoCard[] {}, response.httpStatus, response.errorStatus, response.errorMessage, response.errorCode);
		
		ArrayList<UserInfoCard> results = new ArrayList<UserInfoCard>();
		JsonArray arr = response.getResponseData().getAsJsonArray("Response");
		for(int i = 0; i < arr.size(); i++) {
			UserInfoCard member = new UserInfoCard();
			member.parse(arr.get(i).getAsJsonObject());
			results.add(member);
		}
		return new Response<UserInfoCard[]>(results.toArray(new UserInfoCard[results.size()]));
	}
}
