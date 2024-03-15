package parkingnomad.application.port.out.oauth;

import parkingnomad.domain.oauth.clients.OAuthInfo;

public interface KakaoOAuthInformationLoaderPort {
    OAuthInfo getOAuthInfo(final String code, final String grantType, final String clientId, final String clientSecret,
                           final String redirectUri, final String tokenRequestUri);
}
