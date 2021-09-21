package com.sn1pe2win.main;

import com.sn1pe2win.DestinyEntityObjects.Profile.ItemObjectives.CharacterItemObjectives;
import com.sn1pe2win.core.Gateway;
import com.sn1pe2win.definitions.MembershipType;
import com.sn1pe2win.endpoints.GetProfile;

public class Main {
	
	public static void main(String[] args) {
		Gateway.X_API_KEY = "df9f17a33ec54d96ad1d2310a1cac5e1";
		GetProfile profile = new GetProfile(MembershipType.PSN, 4611686018459652987L);
		CharacterItemObjectives obj = profile.getItemObjectives().getResponseData().getCharacterItemObjectives(2305843009394498992L);
		
		System.out.println(profile.getDestinyProfileComponent().getResponseData().getUserInfo().getDisplayName() 
				+ " hat auf seinem Pass " + obj.getObjective(1600065451L).getObjectives()[0].getProgress() + " Siege!");
	}
}