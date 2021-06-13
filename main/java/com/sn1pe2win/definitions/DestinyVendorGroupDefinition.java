package com.sn1pe2win.definitions;

import com.sn1pe2win.DestinyEntityObjects.DisplayProperties;

public class DestinyVendorGroupDefinition extends StaticDefinition {

	public DestinyVendorGroupDefinition(long identifier) {
		super(ManifestTables.VendorGroupDefinition, identifier);
	}

	public DisplayProperties getDisplayProperties() {
		return null;
	}
	
	public int getOrder() {
		return getRawJson().getAsJsonPrimitive("order").getAsInt();
	}
	
	public String getCategoryName() {
		return getRawJson().getAsJsonPrimitive("categoryName").getAsString();
	}
}
