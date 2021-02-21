package com.sn1pe2win.endpoints;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sn1pe2win.DestinyEntityObjects.PlayerActivity;
import com.sn1pe2win.DestinyEntityObjects.Profile;
import com.sn1pe2win.core.Gateway;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.MembershipType;

public class GetActivityHistory {

	/**@see Profile.DestinyProfileComponent#getActivityHistory(int, int)
	 * @see Profile.CharacterComponent.Character#getCharacterHistory(int, int)*/
	public static Response<PlayerActivity[]> getCharacterActivityHistory(int activityType, int count, MembershipType platform, long profileId, long... characters) {
		ArrayList<PlayerActivity> history = new ArrayList<PlayerActivity>(10);
		
		for(int i = 0; i < characters.length; i++) {
			
			int loadedPerCharacter = 0;
			int page = 0;
			boolean doPages = count > 250 || count <= 0;
			
			pager: while(true) {
				Response<JsonObject> historyPage = Gateway.sendGet("/Destiny2/" + platform.id + "/Account/" + profileId + "/Character/" + characters[i] +  "/Stats/Activities/?mode=" + activityType + "&count=" + (doPages ? 250 : count) + "&page=" + page);
				if(!historyPage.success()) return new Response<PlayerActivity[]>(new PlayerActivity[] {}, historyPage.httpStatus, historyPage.errorStatus, historyPage.errorMessage, historyPage.errorCode);
				if(historyPage.getResponseData().getAsJsonObject("Response").keySet().isEmpty()) break pager;
				
				JsonArray activities = historyPage.getResponseData().getAsJsonObject("Response").getAsJsonArray("activities");
				
				if(activities != null) { //keine aktivität für spieler gefunden
					for(int j = 0; j < activities.size(); j++) {
						PlayerActivity a = new PlayerActivity();
						a.parse(activities.get(i).getAsJsonObject());
						history.add(a);
						loadedPerCharacter++;
						if(loadedPerCharacter == count) break pager;
					}
				}
				page++;
			}
		}
		//If we selected multiple characters, sort those activities by time
		if(characters.length > 1) {
			ArrayList<PlayerActivity> sorted = new ArrayList<PlayerActivity>();
			while(!history.isEmpty()) {
				long record = 0;
				int targetIndex = -1;
				for(int i = 0; i < history.size(); i++) {
					if(history.get(i).getPeriod().getTime() >= record) {
						targetIndex = i;
						record = history.get(i).getPeriod().getTime();
					}
				}
				if(targetIndex != -1) {
					sorted.add(history.get(targetIndex));
					history.remove(targetIndex);
				}
			}
			return new Response<PlayerActivity[]>(sorted.toArray(new PlayerActivity[sorted.size()]));
		} else return new Response<PlayerActivity[]>(history.toArray(new PlayerActivity[history.size()]));
	}
}