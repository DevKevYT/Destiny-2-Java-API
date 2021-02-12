package com.sn1pe2win.definitions;

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
	
//	public TokenValue[] getTokenValues() {
//		JsonArray jtokens = getRawData().getAsJsonArray("tokenValues");
//		if(jtokens == null) return new TokenValue[0];
//		TokenValue[] tokens = new TokenValue[jtokens.size()];
//		
//		for(int i = 0; i < tokens.length; i++) {
//			tokens[i]  = new TokenValue(jtokens.get(i), jtokens.get(i).getAsJsonPrimitive().getAsInt());
//		}
//		
//		return tokens;
//		
//	}
}
