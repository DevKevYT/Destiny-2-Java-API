package com.sn1pe2win.endpoints;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.sn1pe2win.DestinyEntityObjects.Profile.DestinyProfileComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileCurrenciesComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileInventories;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileProgressionComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileSetType;
import com.sn1pe2win.DestinyEntityObjects.Profile.VendorReceiptsComponent;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Gateway;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.MembershipType;

//TODO possibillity to load additional components to this instance
public class GetProfile extends DestinyEntity {

	private Response<?> originalResponse;
	
	public ProfileSetType[] components = new ProfileSetType[0];
	
	private DestinyProfileComponent destinyprofileComponent;
	private VendorReceiptsComponent vendorReceipts;
	private ProfileInventories profileInventories;
	private ProfileCurrenciesComponent profileCurrencies;
	private ProfileProgressionComponent profileProgression;
	
	public GetProfile(MembershipType platform, long destinyMembershipid, ProfileSetType... loadType) {
		this(null, platform, destinyMembershipid, loadType);
	}
	
	/**Pass the access token, if you need to access protected recources.*/
	public GetProfile(String accessToken, MembershipType platform, long destinyMembershipid, ProfileSetType... loadType) {
		if(loadType.length == 0) loadType = new ProfileSetType[] { ProfileSetType.NONE };
		
		super.REQUEST_TYPE = "Destiny2.GetProfile";
		
		String loadTypeAsQueryString = "components=";
		for(ProfileSetType profileSetType : loadType) loadTypeAsQueryString += profileSetType.components + ",";
		loadTypeAsQueryString = loadTypeAsQueryString.substring(0, loadTypeAsQueryString.length()-1);
		System.out.println(loadTypeAsQueryString);
		
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + destinyMembershipid  + "/?" + loadTypeAsQueryString, accessToken);
		if(obj.success()) parse(obj.getResponseData().getAsJsonObject("Response"));
		
		originalResponse = obj;
	}

	public Response<?> getResponse() {
		return originalResponse;
	}
	
	@Override
	public JsonObject getRawJson() {
		return null;
	}

	@Override
	public void parse(JsonObject object) {
		ArrayList<ProfileSetType> newTypes = new ArrayList<>(1);
		
		JsonObject profile = object.getAsJsonObject("profile");
		if(profile != null) {
			newTypes.add(ProfileSetType.PROFILES);
			destinyprofileComponent = new DestinyProfileComponent();
			destinyprofileComponent.parse(profile);
		}
		
		JsonObject vendorReceipts = object.getAsJsonObject("vendorReceipts");
		if(vendorReceipts != null) {
			newTypes.add(ProfileSetType.VENDOR_RECEIPTS);
			this.vendorReceipts = new VendorReceiptsComponent();
			this.vendorReceipts.parse(vendorReceipts);
		}
		
		JsonObject profileInventories = object.getAsJsonObject("profileInventory");
		if(profileInventories != null) {
			newTypes.add(ProfileSetType.PROFILE_INVENTORIES);
			this.profileInventories = new ProfileInventories();
			this.profileInventories.parse(profileInventories);
		}
		
		JsonObject profileCurrencies = object.getAsJsonObject("profileCurrencies");
		if(profileCurrencies != null) {
			newTypes.add(ProfileSetType.PROFILE_CURRENCIES);
			this.profileCurrencies = new ProfileCurrenciesComponent();
			this.profileCurrencies.parse(profileCurrencies);
		}
		
		JsonObject profileProgression = object.getAsJsonObject("profileProgression");
		if(profileProgression != null) {
			newTypes.add(ProfileSetType.PROFILE_PROGRESSION);
			this.profileProgression = new ProfileProgressionComponent();
			this.profileProgression.parse(profileProgression);
		}
		
		components = newTypes.toArray(new ProfileSetType[newTypes.size()]);
	}
	
	/**Profiles is the most basic component, only relevant when calling GetProfile. This returns basic information about the profile, which is almost nothing: a list of characterIds, some information about the last time you logged in, and that most sobering statistic: how long you've played.
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#PROFILES}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public DestinyProfileComponent getDestinyProfileComponent() {
		return destinyprofileComponent;
	}
	
	/** Only applicable for GetProfile, this will return information about receipts for refundable vendor items.
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#VENDOR_RECEIPTS}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public VendorReceiptsComponent getVendorReceiptsComponent() {
		return vendorReceipts;
	}
	
	/**Asking for this will get you the profile-level inventories, such as your Vault buckets (yeah, the Vault is really inventory buckets located on your Profile)
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#PROFILE_INVENTORIES}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public ProfileInventories getProfileInventoryComponent() {
		return profileInventories;
	}
	
	/**This will get you a summary of items on your Profile that we consider to be "currencies", such as Glimmer. I mean, if there's Glimmer in Destiny 2. I didn't say there was Glimmer.
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#PROFILE_INVENTORIES}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public ProfileCurrenciesComponent getProfileCurrenciesComponent() {
		return profileCurrencies;
	}
	
	/**Returns progressions for the profile, such as region chests etc.
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#PROFILE_INVENTORIES}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public ProfileProgressionComponent getProfileProgression() {
		return profileProgression;
	}
	
	public boolean hasComponent(ProfileSetType component) {
		for(ProfileSetType t : components) {
			if(t == component) return true;
		}
		return false;
	}
}
