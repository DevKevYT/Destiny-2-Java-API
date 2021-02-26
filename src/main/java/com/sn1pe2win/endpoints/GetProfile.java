package com.sn1pe2win.endpoints;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.sn1pe2win.DestinyEntityObjects.Profile.CharacterActivitiesComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.CharacterActivitiesComponent.CharacterActivity;
import com.sn1pe2win.DestinyEntityObjects.Profile.CharacterComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.CharacterInventoryComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.DestinyProfileComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.PlatformSilverComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileCurrenciesComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileInventories;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileProgressionComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileSetType;
import com.sn1pe2win.DestinyEntityObjects.Profile.VendorReceiptsComponent;
import com.sn1pe2win.core.DestinyEntity;
import com.sn1pe2win.core.Gateway;
import com.sn1pe2win.core.Response;
import com.sn1pe2win.definitions.MembershipType;
import com.sn1pe2win.definitions.OptionalValue;

//TODO possibillity to load additional components to this instance
public class GetProfile extends DestinyEntity {

	private Response<JsonObject> originalResponse;
	
	public ProfileSetType[] components = new ProfileSetType[0];
	
//	private DestinyProfileComponent destinyprofileComponent;
//	private VendorReceiptsComponent vendorReceipts;
//	private ProfileInventories profileInventories;
//	private ProfileCurrenciesComponent profileCurrencies;
//	private ProfileProgressionComponent profileProgression;
//	private PlatformSilverComponent platformSilver;
//	private CharacterComponent characters;
//	private CharacterInventoryComponent characterInventory;
	
	private final MembershipType platform;
	private final long destinyMembershipId;
	private final String accessToken;
	
	public GetProfile(MembershipType platform, long destinyMembershipid) {
		this(null, platform, destinyMembershipid);
	}
	
	/**Pass the access token, if you need to access protected recources.*/
	public GetProfile(@Nullable String accessToken, MembershipType platform, long destinyMembershipid) {
		
		super.REQUEST_TYPE = "Destiny2.GetProfile";
		
		this.platform = platform;
		this.accessToken = accessToken;
		this.destinyMembershipId = destinyMembershipid;
		
//		String loadTypeAsQueryString = "components=";
//		for(ProfileSetType profileSetType : loadType) loadTypeAsQueryString += profileSetType.components + ",";
//		loadTypeAsQueryString = loadTypeAsQueryString.substring(0, loadTypeAsQueryString.length()-1);
//		System.out.println(loadTypeAsQueryString);
//		
//		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + destinyMembershipid  + "/?" + loadTypeAsQueryString, accessToken);
//		if(obj.success()) parse(obj.getResponseData().getAsJsonObject("Response"));
		
//		originalResponse = obj;
	}

	public Response<JsonObject> getResponse() {
		return originalResponse;
	}
	
	@Override
	public JsonObject getRawJson() {
		return null;
	}

	@Override
	public void parse(JsonObject object) {

		
//		JsonObject profileInventories = object.getAsJsonObject("profileInventory");
//		if(profileInventories != null) {
//			newTypes.add(ProfileSetType.PROFILE_INVENTORIES);
//			this.profileInventories = new ProfileInventories();
//			this.profileInventories.parse(profileInventories);
//		}
//		
//		JsonObject profileCurrencies = object.getAsJsonObject("profileCurrencies");
//		if(profileCurrencies != null) {
//			newTypes.add(ProfileSetType.PROFILE_CURRENCIES);
//			this.profileCurrencies = new ProfileCurrenciesComponent();
//			this.profileCurrencies.parse(profileCurrencies);
//		}
//		
//		JsonObject profileProgression = object.getAsJsonObject("profileProgression");
//		if(profileProgression != null) {
//			newTypes.add(ProfileSetType.PROFILE_PROGRESSION);
//			this.profileProgression = new ProfileProgressionComponent();
//			this.profileProgression.parse(profileProgression);
//		}
//		
//		JsonObject platformSilver = object.getAsJsonObject("platformSilver");
//		if(platformSilver != null) {
//			newTypes.add(ProfileSetType.PLATFORM_SILVER);
//			this.platformSilver = new PlatformSilverComponent();
//			this.platformSilver.parse(platformSilver);
//		}
//		
//		JsonObject characters = object.getAsJsonObject("characters");
//		if(characters != null) {
//			newTypes.add(ProfileSetType.CHARACTERS);
//			this.characters = new CharacterComponent();
//			this.characters.parse(characters);
//		}
//		
//		JsonObject characterInventory = object.getAsJsonObject("characterInventories");
//		if(characterInventory != null) {
//			newTypes.add(ProfileSetType.CHARACTER_INVENTORY);
//			this.characterInventory = new CharacterInventoryComponent();
//			this.characterInventory.parse(characterInventory);
//		}
//		
//		components = newTypes.toArray(new ProfileSetType[newTypes.size()]);
	}
	
	/**Profiles is the most basic component, only relevant when calling GetProfile. This returns basic information about the profile, which is almost nothing: a list of characterIds, some information about the last time you logged in, and that most sobering statistic: how long you've played.
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#PROFILES}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public Response<DestinyProfileComponent> getDestinyProfileComponent() {
		DestinyProfileComponent c = new DestinyProfileComponent();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.PROFILES.components, accessToken);
		if(obj.success()) c.parse(obj.getResponseData().getAsJsonObject("Response").getAsJsonObject("profile"));
		return new Response<DestinyProfileComponent>(c, obj.httpStatus, obj.errorStatus, obj.errorMessage, obj.errorCode);
	}
	
	/** Only applicable for GetProfile, this will return information about receipts for refundable vendor items.
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#VENDOR_RECEIPTS}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public Response<VendorReceiptsComponent> getVendorReceiptsComponent() {
		VendorReceiptsComponent v = new VendorReceiptsComponent();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.VENDOR_RECEIPTS.components, accessToken);
		if(obj.success()) v.parse(obj.getResponseData().getAsJsonObject("Response").getAsJsonObject("vendorReceipts"));
		return new Response<VendorReceiptsComponent>(v, obj.httpStatus, obj.errorStatus, obj.errorMessage, obj.errorCode);
	}
	
	/**Asking for this will get you the profile-level inventories, such as your Vault buckets (yeah, the Vault is really inventory buckets located on your Profile)
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#PROFILE_INVENTORIES}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public Response<ProfileInventories> getProfileInventoryComponent() {
		ProfileInventories i = new ProfileInventories();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.PROFILE_INVENTORIES.components, accessToken);
		if(obj.success()) i.parse(obj.getResponseData().getAsJsonObject("Response").getAsJsonObject("profileInventory"));
		return new Response<ProfileInventories>(i, obj.httpStatus, obj.errorStatus, obj.errorMessage, obj.errorCode);
	}
	
	/**This will get you a summary of items on your Profile that we consider to be "currencies", such as Glimmer. I mean, if there's Glimmer in Destiny 2. I didn't say there was Glimmer.
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#PROFILE_INVENTORIES}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public Response<ProfileCurrenciesComponent> getProfileCurrenciesComponent() {
		ProfileCurrenciesComponent c = new ProfileCurrenciesComponent();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.PROFILE_CURRENCIES.components, accessToken);
		if(obj.success()) c.parse(obj.getResponseData().getAsJsonObject("Response").getAsJsonObject("profileCurrencies"));
		return new Response<ProfileCurrenciesComponent>(c, obj.httpStatus, obj.errorStatus, obj.errorMessage, obj.errorCode);
	}
	
	/**Returns progressions for the profile, such as region chests etc.
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#PROFILE_INVENTORIES}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public Response<ProfileProgressionComponent> getProfileProgression() {
		ProfileProgressionComponent p = new ProfileProgressionComponent();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.PROFILE_PROGRESSION.components, accessToken);
		if(obj.success()) p.parse(obj.getResponseData().getAsJsonObject("Response").getAsJsonObject("profileProgression"));
		return new Response<ProfileProgressionComponent>(p, obj.httpStatus, obj.errorStatus, obj.errorMessage, obj.errorCode);
	}
	
	/**Returns the silver on all platforms. Needs oauth
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#PLATFORM_SILVER}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public Response<PlatformSilverComponent> getPlatformSilver() {
		PlatformSilverComponent s = new PlatformSilverComponent();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.PLATFORM_SILVER.components, accessToken);
		if(obj.success()) s.parse(obj.getResponseData().getAsJsonObject("Response").getAsJsonObject("platformSilver"));
		return new Response<PlatformSilverComponent>(s, obj.httpStatus, obj.errorStatus, obj.errorMessage, obj.errorCode);
	}
	
	/**Returns a summary of all characters
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#CHARACTERS}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public Response<CharacterComponent> getCharacterComponent() {
		CharacterComponent c = new CharacterComponent();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.CHARACTERS.components, accessToken);
		if(obj.success()) c.parse(obj.getResponseData().getAsJsonObject("Response").getAsJsonObject("characters"));
		return new Response<CharacterComponent>(c, obj.httpStatus, obj.errorStatus, obj.errorMessage, obj.errorCode);
	}
	
	/**Returns all the items the character currently has. (Except vault)
	 * @Nullable Not null, if the {@link GetProfile#components} contains {@link ProfileSetType#CHARACTERS}
	 * <br>Check with {@link GetProfile#hasComponent(ProfileSetType)}*/
	public CharacterInventoryComponent getCharacterInventories() {
		CharacterInventoryComponent c = new CharacterInventoryComponent();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.CHARACTER_INVENTORY.components, accessToken);
		if(obj.success()) c.parse(obj.getResponseData().getAsJsonObject("Response").getAsJsonObject("characterInventories"));
		return c;
	}
	
	public CharacterActivitiesComponent getCharacterActivities() {
		CharacterActivitiesComponent c = new CharacterActivitiesComponent();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.CHARACTER_ACTIVITIES.components, accessToken);
		if(obj.success()) c.parse(obj.getResponseData().getAsJsonObject("Response").getAsJsonObject("characterActivities"));
		return c;
	}
	
	public boolean hasComponent(ProfileSetType component) {
		for(ProfileSetType t : components) {
			if(t == component) return true;
		}
		return false;
	}
	
	public MembershipType getPlatform() {
		return platform;
	}
	
	@OptionalValue
	public String getAccessToken() {
		return accessToken == null ? "" : accessToken;
	}

	public long getDestinyMembershipId() {
		return destinyMembershipId;
	}
}
