package com.sn1pe2win.definitions;

/**We don't need no futher data processing. The only data provided by this table are the default display properties
 * You could even use the default {@link StaticDefinition#StaticDefinition(ManifestTables, long)} constructor.
 * This one is only here for completing all the tables*/
public class DestinyEnemyRaceDefinition extends StaticDefinition {

	public DestinyEnemyRaceDefinition(long hash) {
		super(ManifestTables.EnemyRaceDefinition, hash);
	}

}
