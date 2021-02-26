package com.sn1pe2win.definitions;

import java.util.List;

import com.sn1pe2win.DestinyEntityObjects.MilestoneActivity;

public class DestinyMilestoneDefinition extends StaticDefinition {

	public static final int MILESTONE_TYPE_WEEKLY = 3;
	public static final int MILESTONE_TYPE_TUTORIAL = 1;
	
	public DestinyMilestoneDefinition(long identifier) {
		super(ManifestTables.MilestoneDefinition, identifier);
	}
	
	@OptionalValue
	public String getFriendlyName() {
		return optionalString(getRawJson().getAsJsonPrimitive("friendlyName"), "unknown");
	}
	
	@OptionalValue
	public String getImage() {
		return optionalString(getRawJson().getAsJsonPrimitive("image"), "");
	}

	public int getMilestoneType() {
		return getRawJson().getAsJsonPrimitive("milestoneType").getAsInt();
	}
	
	public boolean isRecruitable() {
		return getRawJson().getAsJsonPrimitive("recruitable").getAsBoolean();
	}
	
	public boolean isShowInExplorer() {
		return getRawJson().getAsJsonPrimitive("showInExplorer").getAsBoolean();
	}
	
	public boolean isShowInMilestones() {
		return getRawJson().getAsJsonPrimitive("showInMilestones").getAsBoolean();
	}
	
	public boolean hasExplorePrioritizesActivityImage() {
		return getRawJson().getAsJsonPrimitive("explorePrioritizesActivityImage").getAsBoolean();
	}
	
	public boolean hasPredictableDates() {
		return getRawJson().getAsJsonPrimitive("hasPredictableDates").getAsBoolean();
	}
	
	public boolean isInGameMilestones() {
		return getRawJson().getAsJsonPrimitive("isInGameMilestone").getAsBoolean();
	}
	
	public int getDefaultOrder() {
		return getRawJson().getAsJsonPrimitive("defaultOrder").getAsInt();
	}
	
	@OptionalValue
	public MilestoneActivity[] getActivities() {
		List<MilestoneActivity> a = castArray(getRawJson().getAsJsonArray("activities"), MilestoneActivity.class);
		return a.toArray(new MilestoneActivity[a.size()]);
	}
	
	//TODO finish quests
}
