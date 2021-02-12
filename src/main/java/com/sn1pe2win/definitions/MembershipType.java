package com.sn1pe2win.definitions;

public enum MembershipType {
	
	ALL((short) -1, "All", ""), 
	PSN((short) 2, "PSN", "ps"), 
	XBOX((short) 1, "XBox", "xb"), 
	PC((short) 3, "PC", "pc"),
	STADIA((short) 5, "Stadia", "stadia"),
	BUNGIE_NEXT((short) 254, "unknown", "unknown"),
	NONE((short) 0, "none", "none");
	
	public final short id;
	public final String readable;
	public final String officalName;
	
	MembershipType(short id, String readable, String officalName) {
		this.id = id;
		this.readable = readable;
		this.officalName = officalName;
	}
	
	public static MembershipType of(short id) {
		for(MembershipType membership : MembershipType.values()) {
			if(membership.id == id) return membership;
		}
		return NONE;
	}
}
