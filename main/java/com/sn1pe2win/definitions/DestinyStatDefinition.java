package com.sn1pe2win.definitions;

public class DestinyStatDefinition extends StaticDefinition {

	public static final int STAT_CATEGORY_GAMEPLAY = 0;
	public static final int STAT_CATEGORY_WEAPON = 1;
	public static final int STAT_CATEGORY_PRIMARY = 3;
	public static final int STAT_CATEGORY_DEFENSE = 2;
	
	public static final int STAT_AGGREGATION_ITEM = 2;
	public static final int STAT_AGGREGATION_CHARACTER = 1;
	public static final int STAT_AGGREGATION_CHARACTER_AVERAGE = 0;
	
	public DestinyStatDefinition(long identifier) {
		super(ManifestTables.StatDefinition, identifier);
	}

	public boolean interpolate() {
		return primitiveBoolean("interpolate");
	}
	
	/**@see {@link DestinyStatDefinition#STAT_CATEGORY_GAMEPLAY}<br>...*/
	public int getStatCategory() {
		return primitiveInteger("statCategory");
	}
	
	public boolean hasComputedBlock() {
		return primitiveBoolean("hasComputedBlock");
	}
	
	/**@see {@link DestinyStatDefinition#STAT_AGGREGATION_CHARACTER_AVERAGE}<br>...*/
	public int getAggregationType() {
		return primitiveInteger("aggregationType");
	}
}
