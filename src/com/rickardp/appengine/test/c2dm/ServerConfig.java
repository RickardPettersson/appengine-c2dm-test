package com.rickardp.appengine.test.c2dm;

public class ServerConfig {
	// To get AuthToken you running /GetAuthFromGoogle and take the auth string
	// and put here
	public static String AuthToken = "YourFirstAuthToken";

	// To get your registration id you running the android application for this
	// project and see in the LogCat console in Eclipse What registration id you
	// got and put it here
	public static String DeviceRegistrationID = "YourDeviceRegistrationID";

	// Your Google Account, this is the "Role account", to your knowledge there
	// has been a bug that if the role account is the same as the device syncing
	// with its not getting the messages, so try to create a new google account
	// for c2dm is my tip
	public static String GoogleAccount = "YourGoogleAccount";

	// Your Google Account Password for the "Role account"
	public static String GoogleAccountPassword = "YourGoogelAccountPassword";

}
