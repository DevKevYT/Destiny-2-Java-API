package com.sn1pe2win.main;

import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileSetType;
import com.sn1pe2win.core.Gateway;
import com.sn1pe2win.definitions.MembershipType;
import com.sn1pe2win.endpoints.GetProfile;
import com.sn1pe2win.endpoints.SearchDestinyPlayer;

public class Main {

	public static void main(String[] args) {
		Gateway.X_API_KEY = "df9f17a33ec54d96ad1d2310a1cac5e1";
		GetProfile profile = SearchDestinyPlayer.queryPlayers("Sn1pe2win32", MembershipType.PSN).getResponseData()[0].loadProfile(
				"CKr8AhKGAgAgF3ChXZgp6HbCBSLtPdys28GBorSe82kdMak6jVe9bZbgAAAAlW9GW9WLwsHMLGo81uxyoMXvDlPJdmcOG/JfNFCVGp3G/UQT/Th7QshTwmujRqJPeTGV5EUtoNNgLm/QO4YkxZpRUUeOZsiNncy66Wml23wGSwRjz1BwlY+r787XxA8thx3GOPzA2DeaLoMqke2VzXW9NkepYPc73RgZ1snM+AfEqOtm4yeX1rXlpcdXsou8bzqV9J/DhnluwmjnDx5NI5ieCqWSTWBt4GIUudugUec2ndoufSZV32W/BLF2RH0Imn5YWgWcwTPEOKn+qJS5GX7s6op0DErnll+90tZSStI=", 
				ProfileSetType.PROFILE_PROGRESSION);
		System.out.println(profile.getProfileProgression().getChecklists()[0].getChecklistDefinition().getResponseData().getDisplayProperties().getName());
	}
}
//TODO talent grid definition