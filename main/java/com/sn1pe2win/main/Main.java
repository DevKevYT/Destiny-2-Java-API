package com.sn1pe2win.main;

import com.sn1pe2win.core.Gateway;
import com.sn1pe2win.definitions.MembershipType;
import com.sn1pe2win.endpoints.GetProfile;

public class Main {
	
	public static void main(String[] args) {
		Gateway.X_API_KEY = "df9f17a33ec54d96ad1d2310a1cac5e1";
		GetProfile profile = new GetProfile(MembershipType.PSN, 4611686018459652987L);
		//Jäger Kills als Warlock
		System.out.println(profile.getProfileTriumphs().getResponseData().getTriumph(1359055399L).getObjectives()[0].getProgress() + " lol");
		//Titanen Kills als Jäger
		
		//		GetPostGameCarnageReport r = new GetPostGameCarnageReport(profile.getDestinyProfileComponent().getResponseData().getActivityHistory(0, 1).getResponseData()[0].getActivityDetails().getInstanceId());
//		for(ExtendedPGCRWeapons w : r.getPlayers()[0].getExtended().getUniqueWeaponKills()) {
//			JsonArray array = w.getItem().getResponseData().getRawJson().getAsJsonArray("itemCategoryHashes");
//			boolean weapon = false;
//			for(JsonElement e : array) {
//				System.out.println(e.getAsInt());
//			}
//		}
		

//		System.out.println(p.getPlayers()[0].getExtended().getUniqueWeaponKills(1508896098L).getItem().getResponseData().getDisplayProperties().getName());
//		PlayerActivity[] activities = profile.getDestinyProfileComponent().getResponseData().getActivityHistory(0, 2).getResponseData();
//		for(PlayerActivity a : activities) {
//			System.out.println(a.getActivityDetails().getActivity().getResponseData().getDisplayProperties().getName() + "\t\t\t" + a.getPeriod().toString());
//		}
		//System.out.println(profile.getProfileTriumphs().getResponseData().getTriumph(2406724529L).getIntervalObjectives()[0].getProgress());
		//System.out.println(profile.getCollectibles().getResponseData().getCharacterCollectibles()[0].getCollectible(28595).);
//		PlayerActivity[] activity = profile.getDestinyProfileComponent().getResponseData().getActivityHistory(0, 5).getResponseData();
//		for(PlayerActivity a : activity) {
//			
//			GetPostGameCarnageReport pgcr = new GetPostGameCarnageReport(a.getActivityDetails().getInstanceId());
//			DestinyClassDefinition clazz = null;
//			for(PGCRPlayerEntry e : pgcr.getPlayers()) {
//				if(e.getPlayerInfo().getDestinyUserInfo().getMembershipId() == 4611686018459652987L) {
//					clazz = new DestinyClassDefinition(e.getPlayerInfo().getDestinyClassHash());
//					break;
//				}
//			}
//			
//			System.out.println(a.getPeriod().toString() + ": " + a.getActivityDetails().getActivity().getResponseData().getDisplayProperties().getName() + 
//					"\n\tPlayed as: "+ clazz.getMaleClassName() + "\n\tInstanceId: " + a.getActivityDetails().getInstanceId() + "\n");
//		}
//		int totalKills = 0;
//		int totalDeaths = 0;
//		float rankingMultiplicator = 1;
//		
//		for(int i = 0; i < 10; i++) {
//			if(i >= activity.length) break;
//			
//			PlayerActivity a = activity[i];
//			if(a.getActivityStats().getStatValue("playerCount") >= 4) {
//				//totalKills += a.getActivityStats().getKills();
//				//totalDeaths += a.getActivityStats().getDeaths();
//				
//				GetPostGameCarnageReport pgcr = new GetPostGameCarnageReport(a.getActivityDetails().getInstanceId());
//				//Bester Spieler (Meiste Kills)
//				PGCRPlayerEntry best = null;
//				PGCRPlayerEntry mostDeaths = null;
//				for(PGCRPlayerEntry e : pgcr.getPlayers()) {
//					if(best == null) best = e;
//					if(mostDeaths == null) mostDeaths = e;
//					if(e.getStats().getKills() > best.getStats().getKills()) {
//						best = e;
//					}
//					if(e.getStats().getDeaths() > mostDeaths.getStats().getDeaths()) {
//						mostDeaths = e;
//					}
//				}
//				//"Normalize" the kills and deaths
//				float length = (float) a.getActivityStats().getKills() / (float) best.getStats().getKills();
//				totalKills += a.getActivityStats().getKills() * length;
//				System.out.println(length);
//				float deathsNormaizer =  1 - ((float) a.getActivityStats().getDeaths() / (float) mostDeaths.getStats().getDeaths());
//				System.out.println("Tode " + deathsNormaizer);
//				totalDeaths += a.getActivityStats().getDeaths() * deathsNormaizer;
//				
//				//System.out.println("Spieler mit den meisten Kills: " + best.getPlayerInfo().getDestinyUserInfo().getDisplayName() + " " + a.getPeriod().toGMTString());
			}
		}
//		
//		float kd = (float) totalKills / (float) totalDeaths;
//		System.out.println("KD: " + kd);
//		System.out.println("Der Spieler erzielte ein Ranking von: " + kd * rankingMultiplicator +  " seit den letzten 10 Spielen");
//TODO talent grid definition