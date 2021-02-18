package com.sn1pe2win.definitions;

import java.util.ArrayList;

import com.sn1pe2win.DestinyEntityObjects.FactionVendorInformation;
import com.sn1pe2win.DestinyEntityObjects.HashPair;

/**@uncomplete*/
public class DestinyFactionDefinition extends StaticDefinition {
	
	public final long progressionHash;
	public final long rewardItemHash;
	public final long rewardVendorHash;
	
	public DestinyFactionDefinition(long identifier) {
		super(ManifestTables.FactionDefinition, identifier);
		
		progressionHash = getRawJson().getAsJsonPrimitive("progressionHash").getAsLong();
		rewardItemHash = getRawJson().getAsJsonPrimitive("rewardItemHash").getAsLong();
		rewardVendorHash = getRawJson().getAsJsonPrimitive("rewardVendorHash").getAsLong();
		//TODO Progressions
	}
	
	public HashPair[] getTokenValues() {
		return optionalHashPair(getRawJson().getAsJsonObject("tokenValues"));
	}
	
	public ArrayList<FactionVendorInformation> getVendors() {
		return (ArrayList<FactionVendorInformation>) optionalCastArray(getRawJson().getAsJsonArray("vendors"), FactionVendorInformation.class);
	}
	//TODO definitions for hashes
	
}
