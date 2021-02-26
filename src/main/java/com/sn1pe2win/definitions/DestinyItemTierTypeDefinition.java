package com.sn1pe2win.definitions;

import com.sn1pe2win.DestinyEntityObjects.InfusionProcess;

public class DestinyItemTierTypeDefinition extends StaticDefinition {
	
	public DestinyItemTierTypeDefinition(long identifier) {
		super(ManifestTables.ItemTierTypeDefinition, identifier);
	}

	public InfusionProcess getInfusionProcess() {
		return cast(getRawJson().getAsJsonObject("infusionProcess"), InfusionProcess.class);
	}
}
