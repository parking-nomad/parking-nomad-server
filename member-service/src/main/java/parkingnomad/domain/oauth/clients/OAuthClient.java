package parkingnomad.domain.oauth.clients;

import parkingnomad.domain.UserInfo;

public interface OAuthClient {
    boolean isFitWithProvider(String oAuthProviderName);

    OAuthInfo getOauthInfo(String code);

    UserInfo getValidatedUserInfo(OAuthInfo oauthInfo);
}
