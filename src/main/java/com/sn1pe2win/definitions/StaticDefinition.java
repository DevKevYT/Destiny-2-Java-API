package com.sn1pe2win.definitions;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.sn1pe2win.DestinyEntityObjects.DisplayProperties;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Gateway;
import com.sn1pe2win.core.Response;

public abstract class StaticDefinition extends DestinyEntity {
	
	private static final ArrayList<StaticDefinition> KNOWN_DEFINITIONS = new ArrayList<StaticDefinition>();
	
	public final long identifier;
	public final ManifestTables entityDefinition;
	
	private Response<JsonObject> originalResponse;
	private JsonObject data;
	private DisplayProperties displayProperties;
	private boolean redacted;
	private boolean blacklisted;
	private int index;
	
	public StaticDefinition(ManifestTables entityDefinition, long identifier) {
		this.identifier = identifier;
		this.entityDefinition = entityDefinition;
		
		Response<JsonObject> load = getDefinition(entityDefinition, identifier);
		if(load.success() && load.containsPayload()) {
			this.originalResponse = load;
			parse(load.getResponseData());
			KNOWN_DEFINITIONS.add(this);
		} else throw new IllegalArgumentException("Unable to construct definition, when there's no data providet");
	}
	
	/**Parse the loaded data into the template
	 * You can use the {@link StaticDefinition#data} value to build abstract classes*/
	public final void parse(JsonObject data) {
		this.data = this.originalResponse.getResponseData().getAsJsonObject("Response");
		if(this.data == null) throw new IllegalArgumentException("Unable to construct definition: Wrong format");
		
		//Construct the display properties
		displayProperties = new DisplayProperties();
		displayProperties.parse(this.data.getAsJsonObject("displayProperties"));
		if(displayProperties == null) throw new IllegalArgumentException("Unable to parse display properties. Wrong data supplied");
		
		redacted = this.data.getAsJsonPrimitive("redacted").getAsBoolean();
		blacklisted = this.data.getAsJsonPrimitive("blacklisted").getAsBoolean();
		index = this.data.getAsJsonPrimitive("index").getAsInt();
	}
	
	private final Response<JsonObject> getDefinition(ManifestTables entityDefinition, long identifier) {
		for(StaticDefinition definition : KNOWN_DEFINITIONS) {
			if(definition.identifier == identifier && definition.entityDefinition == entityDefinition) return definition.originalResponse;
		}
		
		//Versuche die Definition per API zu laden
		Response<JsonObject> definition = Gateway.sendGet("/Destiny2/Manifest/" + entityDefinition.definitionName + "/" + identifier);
		if(!definition.success() || !definition.containsPayload()) {
			return new Response<JsonObject>(null, definition.httpStatus, definition.errorStatus, definition.errorMessage, definition.errorCode);
		}
		//Check for matching classes to return. Otherwise return an instance of this class
		return definition;
	}

	public final boolean isRedacted() {
		return redacted;
	}

	public final boolean isBlacklisted() {
		return blacklisted;
	}

	public final int getIndex() {
		return index;
	}
	
	/**@return The raw data to wok with in abstract classes*/
	public final JsonObject getRawJson() {
		return data;
	}
	
	/**Clears all the known, already saved definitions.
	 * Call this, if you experience a manifest update to
	 * force a reload of all definitions.*/
	public void updateDefinition() {
		KNOWN_DEFINITIONS.clear();
	}
	
	/**@return Display properties present in every definition and parsed into a more readable
	 * format*/
	public final DisplayProperties getDisplayProperties() {
		return displayProperties;
	}
	
	public final boolean success() {
		return originalResponse.success() && originalResponse.containsPayload();
	}
	
	/**Converts this object into a response object to handle errors*/
	public Response<? extends StaticDefinition> getAsResponse() {
		return new Response<StaticDefinition>(this, 
				originalResponse.httpStatus, 
				originalResponse.errorStatus, 
				originalResponse.errorMessage, 
				originalResponse.errorCode);
	}
}
