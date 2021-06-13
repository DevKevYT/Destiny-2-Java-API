# Destiny-2-Java-API Wrapper

Simple, easy to use Destiny 2 Java Wrapper for small projects
This wrapper does not download the manifest, instead it only takes the Destiny 2 definitions if it really needs them
This brings a lot of advantages, but also some disatvantages.
Be careful, as already mentioned, this wrapper is just ideal for small projects or if it just suits your needs:

## Advantages
- Does not require a full Manifest File, thus saving a lot of RAM
- Ideal for small projects where you don't even want the full manifest data
- Easy to implement
- Manifest Data Always on the newest version

## Disadvantages
- Unlike with the fully downloaded manifest, only English in the Definitions is supported
- Longer loading times / more internet traffic

# Projects using this Wrapper
- https://github.com/DevKevYT/DiscordDestinyBot

# Implementation + Required Dependencies
- Download the latest .jar from the releases
- Add the .jar to your Java Build Path
- Download or add the gson library to your Project
  - Using Gradle:
    Add this line to your build.gradle file
    `implementation 'com.google.code.gson:gson:2.8.6'`
  - Otherwise download the latest release at at https://github.com/google/gson 
    and add the .jar to your Java build Path
- If you don't own one already, register your Destiny 2 Application at https://www.bungie.net/en/Application
  and copy your X_API_KEY (Named API-Key)
- Add the API key to the Gateway class.
  Example:
  ```java
  import com.sn1pe2win.core.Gateway;
  
  public class Main {
    public static void main(String[] args) {
	    Gateway.X_API_KEY = "YOUR API KEY HERE";
        //Your code goes here
	  }
  }
  ```
- Done!

# Examples
The following examples should give you an idea of how to use this Wrapper

1. Tracking a triumph from a user
```java
public class Main {
	
	public static void main(String[] args) {
		Gateway.X_API_KEY = "";
		
		//First, load a destiny Profile using his membershipType and membershipId
		GetProfile profile = new GetProfile(MembershipType.PSN, 4611686018459652987L);

		//Get the profile triumphs
		Response<ProfileTriumphsComponent> triumphs = profile.getProfileTriumphs();
		
		//Check, if the data was revieved without any error codes
		if(triumphs.success()) {
			
			//The function .getResponseData() will get you the data you'll need, if the response was successfull
			ProfileTriumphsComponent profileTriumphs = triumphs.getResponseData();
			
			//Get the triumph with the hash 1359055399. This would be the triumph: "Put a flag on it" (Place Raid Banners in Last Wish)
			Triumph triumph = profileTriumphs.getTriumph(1359055399L);
			
			//Get the objective of the triumph (Some triumphs could have multiple objectives) to track the progress
			Objective placedBanners = triumph.getObjectives()[0];
			
			System.out.println(profile.getDestinyProfileComponent().getResponseData().getUserInfo().getDisplayName()
					+ " has placed " + placedBanners.getProgress() + " banners in last wish");
			
			
		} else System.out.println("Something went wrong: " + triumphs.errorMessage);
		
		//this is the same as above, just as a handy "one-liner" without any error handling (Quick and dirty way)
		System.out.println(profile.getDestinyProfileComponent().getResponseData().getUserInfo().getDisplayName()
				+ " has placed "  + profile.getProfileTriumphs().getResponseData().getTriumph(1359055399L).getObjectives()[0].getProgress() + " banners in last wish");
	}
}
```



