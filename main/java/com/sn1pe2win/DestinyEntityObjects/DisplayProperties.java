package com.sn1pe2win.DestinyEntityObjects;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sn1pe2win.core.DestinyEntity;

public class DisplayProperties extends DestinyEntity {

	private String description = ""; 
	private String name = "";
	private String iconPath = "";
	private String iconUrl = "";
	private boolean hasIcon = false;
	private IconSequenceProperty[] iconSequences = new IconSequenceProperty[0];
	
	/**This object should contain the display property values*/
	@Override
	public void parse(JsonObject obj) {
		if(obj == null) return;
		JsonPrimitive jdesc = obj.getAsJsonPrimitive("description");
		if(jdesc != null) this.description = jdesc.getAsString();
		JsonPrimitive jname = obj.getAsJsonPrimitive("name");
		if(jname != null) this.name = jname.getAsString();
		boolean hi = false;
		JsonPrimitive icon = obj.getAsJsonPrimitive("hasIcon");
		if(icon != null) {
			hi = icon.getAsBoolean();
			if(hi) {
				JsonPrimitive iconpath = obj.getAsJsonPrimitive("icon");
				if(iconpath != null) this.iconPath = iconpath.getAsString();
				this.iconUrl = "https://bungie.net" + this.iconPath;
			}
		}
		JsonArray jiconSequence = obj.getAsJsonArray("iconSequences");
		if(jiconSequence != null) {
			iconSequences = new IconSequenceProperty[jiconSequence.size()];
			for(int i = 0; i < iconSequences.length; i++) {
				IconSequenceProperty ic = new IconSequenceProperty();
				ic.parse(jiconSequence.get(i).getAsJsonObject());
				iconSequences[i] = ic;
			}
		} else iconSequences = new IconSequenceProperty[0];
	}

	@Override
	public JsonObject getRawJson() {
		return null;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getIconUrl() {
		return iconUrl;
	}
	
	public String getIconPath() {
		return iconPath;
	}

	public boolean isHasIcon() {
		return hasIcon;
	}
}
