package com.sn1pe2win.definitions;

import com.sn1pe2win.DestinyEntityObjects.AcquisitionInfo;
import com.sn1pe2win.DestinyEntityObjects.StateInfo;

public class DestinyCollectibleDefinition extends StaticDefinition {

	public static final int COLLECTIBLES = 2;
	
	public DestinyCollectibleDefinition(long identifier) {
		super(ManifestTables.CollectibleDefinition, identifier);
	}

	public int getScope() {
		return getRawJson().getAsJsonPrimitive("scope").getAsInt();
	}
	
	public String getSourceString() {
		return getRawJson().getAsJsonPrimitive("sourceString").getAsString();
	}
	
	public long getSourceHash() {
		return getRawJson().getAsJsonPrimitive("sourceHash").getAsLong();
	}
	
	//TODO hash. You know what to do
	public long getItemHash() {
		return getRawJson().getAsJsonPrimitive("itemHash").getAsLong();
	}
	
	public AcquisitionInfo getAcquisitionInfo() {
		AcquisitionInfo i = new AcquisitionInfo();
		i.parse(getRawJson().getAsJsonObject("acquisitionInfo"));
		return i;
	}
	
	public StateInfo getStateInfo() {
		StateInfo i = new StateInfo();
		i.parse(getRawJson().getAsJsonObject("stateInfo"));
		return i;
	}
	
	/**{@link DestinyCollectibleDefinition#COLLECTIBLES}*/
	public int getPresentationNodeType() {
		return getRawJson().getAsJsonPrimitive("presentationNodeType").getAsInt();
	}
	
	public long[] getParentNodeHashes() {
		return optionalLongArray(getRawJson().getAsJsonArray("parentNodeHashes"), new long[] {});
	}
	
	//TODO Hash as usual
}
