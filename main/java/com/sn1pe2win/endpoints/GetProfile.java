package com.sn1pe2win.endpoints;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.sn1pe2win.DestinyEntityObjects.Profile.CharacterActivitiesComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.CharacterComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.CharacterInventoryComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.CollectibleComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.DestinyProfileComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.ItemObjectives;
import com.sn1pe2win.DestinyEntityObjects.Profile.MetricsComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.PlatformSilverComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileCurrenciesComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileInventories;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileProgressionComponent;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileSetType;
import com.sn1pe2win.DestinyEntityObjects.Profile.ProfileTriumphsComponent;
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
	
	/**Retuns a summary of all the triumphs this profile got alongside the definitions*/
	public Response<ProfileTriumphsComponent> getProfileTriumphs() {
		ProfileTriumphsComponent c = new ProfileTriumphsComponent();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.PROFILE_RECORDS.components, accessToken);
		if(obj.success()) c.parse(obj.getResponseData().getAsJsonObject("Response").getAsJsonObject("profileRecords"));
		return new Response<ProfileTriumphsComponent>(c, obj.httpStatus, obj.errorStatus, obj.errorMessage, obj.errorCode);
	}
	
	public Response<CollectibleComponent> getCollectibles() {
		CollectibleComponent c = new CollectibleComponent();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.COLLECTIBLES.components, accessToken);
		if(obj.success()) c.parse(obj.getResponseData().getAsJsonObject("Response"));
		return new Response<CollectibleComponent>(c, obj.httpStatus, obj.errorStatus, obj.errorMessage, obj.errorCode);
	}
	
	public Response<MetricsComponent> getMetrics() {
		MetricsComponent c = new MetricsComponent();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.PROFILE_METRICS.components, accessToken);
		if(obj.success()) c.parse(obj.getResponseData().getAsJsonObject("Response").getAsJsonObject("metrics"));
		return new Response<MetricsComponent>(c, obj.httpStatus, obj.errorStatus, obj.errorMessage, obj.errorCode);
	}
	
	public Response<ItemObjectives> getItemObjectives() {
		ItemObjectives c = new ItemObjectives();
		Response<JsonObject> obj = Gateway.sendGet("/Destiny2/" + platform.id +  "/Profile/"  + getDestinyMembershipId()  + "/?components=" + ProfileSetType.ITEM_OBJECTIVES.components, accessToken);
		if(obj.success()) c.parse(obj.getResponseData().getAsJsonObject("Response"));
		return new Response<ItemObjectives>(c, obj.httpStatus, obj.errorStatus, obj.errorMessage, obj.errorCode);
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
