package com.sn1pe2win.definitions;

/**As with the EnemyRaceDefinitions, we don't need no futher data processing. The only data provided by this table are the default display properties
 * You could even use the default {@link StaticDefinition#StaticDefinition(ManifestTables, long)} constructor.
 * This one is only here for a complete list of all the tables*/
public class DestinyPlaceDefinition extends StaticDefinition {

	public DestinyPlaceDefinition(long identifier) {
		super(ManifestTables.PlaceDefinition, identifier);
	}

}
