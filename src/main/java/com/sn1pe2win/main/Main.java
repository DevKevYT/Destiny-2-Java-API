package com.sn1pe2win.main;

import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileSetType;
import com.sn1pe2win.core.Gateway;
import com.sn1pe2win.definitions.MembershipType;
import com.sn1pe2win.endpoints.GetProfile;
import com.sn1pe2win.endpoints.SearchDestinyPlayer;

public class Main {

	public static void main(String[] args) {
		Gateway.X_API_KEY = "df9f17a33ec54d96ad1d2310a1cac5e1";
		GetProfile profile = SearchDestinyPlayer.queryPlayers("Sn1pe2win32", MembershipType.PSN).getResponseData()[0].loadProfile(ProfileSetType.PROFILE_INVENTORIES);
		System.out.println(profile.getProfileInventoryComponent().getItems()[0].getBucket().getResponseData().getItemCount());
	}
}
//TODO talent grid definition