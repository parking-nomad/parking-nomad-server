package parkingnomad.application.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import parkingnomad.application.port.in.usecase.SocialLoginUseCase;
import parkingnomad.application.port.in.usecase.dto.TokenResponse;
import parkingnomad.application.port.out.persistence.MemberRepository;
import parkingnomad.application.port.out.persistence.RefreshTokenRepository;
import parkingnomad.domain.Member;
import parkingnomad.domain.UserInfo;
import parkingnomad.domain.oauth.clients.OAuthClients;
import parkingnomad.domain.oauth.provider.OAuthProvider;

@Component
@Transactional(readOnly = true)
public class SocialLoginService implements SocialLoginUseCase {

    private final OAuthClients oAuthClients;
    private final TokenParser tokenParser;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenAdaptor;

    public SocialLoginService(
            final OAuthClients oAuthClients,
            final TokenParser tokenParser,
            final MemberRepository memberRepository,
            final RefreshTokenRepository refreshTokenAdaptor
    ) {
        this.oAuthClients = oAuthClients;
        this.tokenParser = tokenParser;
        this.memberRepository = memberRepository;
        this.refreshTokenAdaptor = refreshTokenAdaptor;
    }

    @Override
    @Transactional
    public TokenResponse socialLogin(final String code, final OAuthProvider oAuthProvider) {
        final UserInfo userInfo = oAuthClients.getUserInfo(code, oAuthProvider.name());
        final Member member = findOrSaveMember(userInfo);
        final String accessToken = tokenParser.parseToAccessTokenFrom(member.getId());
        final String refreshToken = tokenParser.createRefreshToken();
        refreshTokenAdaptor.save(refreshToken, member.getId());
        return new TokenResponse(accessToken, refreshToken);
    }

    private Member findOrSaveMember(final UserInfo userInfo) {
        return memberRepository.findBySub(userInfo.getSubWithProvider())
                .orElseGet(() -> memberRepository.saveMember(
                        Member.createWithoutId(userInfo.getSubWithProvider(), userInfo.getNickname()))
                );
    }
}
