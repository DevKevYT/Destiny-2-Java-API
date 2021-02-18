package com.sn1pe2win.definitions;

import com.google.gson.JsonObject;
import com.sn1pe2win.DestinyEntityObjects.Color;
import com.sn1pe2win.DestinyEntityObjects.Inventory;
import com.sn1pe2win.DestinyEntityObjects.ItemAction;
import com.sn1pe2win.core.Response;

public class DestinyInventoryItemDefinition extends StaticDefinition {

	public DestinyInventoryItemDefinition(long identifier) {
		super(ManifestTables.InventoryItemDefinition, identifier);
	}
	
	@OptionalValue
	public long getCollectibleHash() {
		return primitiveLong("collectibleHash");
	}
	
	@OptionalValue
	@SuppressWarnings("unchecked")
	public Response<DestinyCollectibleDefinition> getCollectible() {
		if(getCollectibleHash() != 0) {
			return (Response<DestinyCollectibleDefinition>) new DestinyInventoryItemDefinition(getCollectibleHash()).getAsResponse();
		} else return new Response<DestinyCollectibleDefinition>(null, 404, "Not Found", "This value is not present in the current definition item", 0);
	}
	
	public Color getBackgroundColor() {
		Color c = new Color();
		c.parse(getRawJson().getAsJsonObject("backgroundColor"));
		return c;
	}
	
	@OptionalValue
	public String getScreenshotURL() {
		return primitiveString("screenshot");
	}
	
	public String itemTypeDisplayName() {
		return primitiveString("itemTypeDisplayName");
	}
	
	public String getFlavorText() {
		return primitiveString("flavorText");
	}
	
	public String getUiItemDisplayStyle() {
		return primitiveString("uiItemDisplayStyle");
	}
	
	public String getItemTypeAndTierDisplayName() {
		return primitiveString("itemTypeAndTierDisplayName");
	}
	
	public String getDisplaySource() {
		return primitiveString("displaySource");
	}
	
	@OptionalValue
	public ItemAction getAction() {
		JsonObject obj = getRawJson().getAsJsonObject("action");
		if(obj != null) {
			ItemAction a = new ItemAction();
			a.parse(obj);
			return a;
		} else return new ItemAction();
	}
	
	public Inventory getInventory() {
		JsonObject obj = getRawJson().getAsJsonObject("inventory");
		if(obj != null) {
			Inventory a = new Inventory();
			a.parse(obj);
			return a;
		} else return new Inventory();
	}
	//TODO break made here...
}
