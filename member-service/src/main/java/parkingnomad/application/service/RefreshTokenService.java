package parkingnomad.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parkingnomad.application.port.in.usecase.RefreshTokensUseCase;
import parkingnomad.application.port.in.usecase.dto.TokenResponse;
import parkingnomad.application.port.out.persistence.MemberRepository;
import parkingnomad.application.port.out.persistence.RefreshTokenRepository;
import parkingnomad.exception.InvalidRefreshTokenException;
import parkingnomad.exception.member.NonExistentMemberException;
import parkingnomad.jwt.TokenParser;

import static org.apache.logging.log4j.util.Strings.isBlank;
import static parkingnomad.exception.AuthExceptionCode.INVALID_REFRESH_TOKEN;
import static parkingnomad.exception.member.MemberExceptionCode.NON_EXISTENT_MEMBER;

@Service
@Transactional(readOnly = true)
public class RefreshTokenService implements RefreshTokensUseCase {

    private final TokenParser tokenParser;
    private final RefreshTokenRepository refreshTokenAdaptor;
    private final MemberRepository memberRepository;

    public RefreshTokenService(
            final TokenParser tokenParser,
            final RefreshTokenRepository refreshTokenAdaptor,
            final MemberRepository memberRepository
    ) {
        this.tokenParser = tokenParser;
        this.refreshTokenAdaptor = refreshTokenAdaptor;
        this.memberRepository = memberRepository;
    }

    @Override
    public TokenResponse refreshTokens(final String refreshToken) {
        if (isBlank(refreshToken)) {
            throw new InvalidRefreshTokenException(INVALID_REFRESH_TOKEN.getCode());
        }

        final Long memberId = refreshTokenAdaptor.findMemberIdByRefreshToken(refreshToken)
                .orElseThrow(() -> new InvalidRefreshTokenException(INVALID_REFRESH_TOKEN.getCode()));

        memberRepository.findById(memberId)
                .orElseThrow(() -> new NonExistentMemberException(NON_EXISTENT_MEMBER.getCode(), memberId));

        final String accessToken = tokenParser.parseToAccessTokenFrom(memberId);
        final String newRefreshToken = tokenParser.createRefreshToken();
        refreshTokenAdaptor.save(newRefreshToken, memberId);
        refreshTokenAdaptor.deleteByRefreshToken(refreshToken);

        return new TokenResponse(accessToken, newRefreshToken);
    }
}
