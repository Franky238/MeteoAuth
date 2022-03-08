package com.meteoauth.MeteoAuth.oAuth2;

public enum SocialProvider {

	FACEBOOK("facebook"), TWITTER("twitter"), LINKEDIN("linkedin"), GOOGLE("google"), GITHUB("github"), LOCAL("local");

	private final String providerType;

	public String getProviderType() {
		return providerType;
	}

	SocialProvider(final String providerType) {
		this.providerType = providerType;
	}

}
