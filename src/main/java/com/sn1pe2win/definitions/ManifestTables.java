package com.sn1pe2win.definitions;

public enum ManifestTables {
	
	/**Destiny enemy races. Somehow obsolete and is does not seem to be updated in a VERY long time...*/
	EnemyRaceDefinition("DestinyEnemyRaceDefinition"),
	
	/**All locations from destiny 2*/
	PlaceDefinition("DestinyPlaceDefinition"),
	
	/**All activities*/
	ActivityDefinition("DestinyActivityDefinition"),
	
	/**Activity modifier*/
	ActivityModifierDefinition("DestinyActivityModifierDefinition"),
	
	/**The type of the activity (Story?)*/
	ActivityModeDefinition("DestinyActivityModeDefinition"),
	
	/**Activity types such as Gambit, Raid, Ironbanner etc...*/
	ActivityTypeDefinition("DestinyActivityTypeDefinition"),
	
	/**Warlock masterrace!*/
	ClassDefinition("DestinyClassDefinition"),
	
	/**Male - Female - Alien?*/
	GenderDefinition("DestinyGenderDefinition"),
	
	InventoryBucketDefinition("DestinyInventoryBucketDefinition"),
	
	RaceDefinition("DestinyRaceDefinition"),
	
	UnlockDefinition("DestinyUnlockDefinition"),
	
	MaterialRequirementSetDefinition("DestinyMaterialRequirementSetDefinition"),
	
	SandboxPerkDefinition("DestinySandboxPerkDefinition"),
	
	StatGroupDefinition("DestinyStatGroupDefinition"),
	
	FactionDefinition("DestinyFactionDefinition"),
	
	ChecklistDefinition("DestinyChecklistDefinition"),
	
	VendorGroupDefinition("DestinyVendorGroupDefinition"),
	
	RewardSourceDefinition("DestinyRewardSourceDefinition"),
	
	ItemCategoryDefinition("DestinyItemCategoryDefinition"),
	
	DamageTypeDefinition("DestinyDamageTypeDefinition"),
	
	MedalTierDefinition("DestinyMedalTierDefinition"),
	
	AchievementDefinition("DestinyAchievementDefinition"),
	
	BondDefinition("DestinyBondDefinition"),
	
	CollectibleDefinition("DestinyCollectibleDefinition"),
	
	DestinationDefinition("DestinyDestinationDefinition"),
	
	EquipmentSlotDefinition("DestinyEquipmentSlotDefinition"),
	
	StatDefinition("DestinyStatDefinition"),
	
	InventoryItemDefinition("DestinyInventoryItemDefinition"),
	
	MilestoneDefinition("DestinyMilestoneDefinition"),
	
	ItemTierTypeDefinition("DestinyItemTierTypeDefinition"),
	
	LocationDefinition("DestinyLocationDefinition"),
	
	LoreDefinition("DestinyLoreDefinition");
	
	public final String definitionName;
	
	ManifestTables(String definitionName) {
		this.definitionName = definitionName;
	}
}
