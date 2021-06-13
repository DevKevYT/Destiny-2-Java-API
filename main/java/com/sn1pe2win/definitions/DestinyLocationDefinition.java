package com.sn1pe2win.definitions;

import com.sn1pe2win.DestinyEntityObjects.LocationRelease;
import java.util.List;

public class DestinyLocationDefinition extends StaticDefinition {

	public DestinyLocationDefinition(long identifier) {
		super(ManifestTables.LocationDefinition, identifier);
	}
	
	public LocationRelease[] getLocations() {
		List<LocationRelease> l = castArray(getRawJson().getAsJsonArray("locationReleases"), LocationRelease.class);
		return l.toArray(new LocationRelease[l.size()]);
	}
}
