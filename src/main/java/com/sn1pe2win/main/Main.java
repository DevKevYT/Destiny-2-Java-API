package com.sn1pe2win.main;

import com.sn1pe2win.core.Gateway;
import com.sn1pe2win.endpoints.GetPostGameCarnageReport;

public class Main {

	public static void main(String[] args) {
		Gateway.X_API_KEY = "df9f17a33ec54d96ad1d2310a1cac5e1";
//		GetProfile profile = SearchDestinyPlayer.queryPlayers("Sn1pe2win32", MembershipType.PSN).getResponseData()[0].loadProfile(
//				/*"CPv8AhKGAgAgwG/I0XdNW8LTd1UHaEkp1Q32/6DqPEZfQPatLVliZLXgAAAAe2PErwaeFFF4BMJG3gUM35jFAYRJmlwtqF03+sNsMxjGxGDHjTsBygnL5iRCNkjmaHAc7/P40rkhvn62GAyq6lR3H6U1IHLQA7rNc+laceydo1RJWULuBu7fzWcOifLvxkJw4qsZlRM7dDGc/1QynfSjPXUGirXzcgnQRWFl/Pw2coqRqfC6T/e79MQ46Q9/8Y0n1JQKU3IDc+lU8fSSvIDvsuc3PvsuOx1Y7fY43XPa2iNDMIzSoKJPyG3mqudAlnfgbFze3CML2DhO3IaRsSZ9JbniOwvJuebpgh29tc4=",*/ 
//				ProfileSetType.CHARACTERS);
//		System.out.println(profile.getCharacterComponent().getCharacters()[1].getLevelProgression().getCurrentProgress());
//		DestinyFactionDefinition faction = new DestinyFactionDefinition(1838583129L);
//		System.out.println(faction.getVendors().get(0).getDestinationHash());
		GetPostGameCarnageReport g = new GetPostGameCarnageReport("8008632488");
		System.out.println(g.getPlayers()[0].getStats().getDeaths());
	}
}
//TODO talent grid definition