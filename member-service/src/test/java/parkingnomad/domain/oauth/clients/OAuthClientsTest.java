package parkingnomad.domain.oauth.clients;

import parkingnomad.exception.oauth.InvalidOAuthProviderException;
import parkingnomad.support.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class OAuthClientsTest extends BaseTest {
    @Autowired
    OAuthClients oAuthClients;

    @Test
    @DisplayName("provider와 일치하는 client가 없는 경우 예외가 발생한다.")
    void getUserInfoFail() {
        //given
        final String invalidProviderName = "invalidProviderName";

        //when & then
        assertThatThrownBy(() -> oAuthClients.getUserInfo("code", invalidProviderName))
                .isInstanceOf(InvalidOAuthProviderException.class)
                .hasMessageContaining("provider와 일치하는 client가 존재하지 않습니다");
    }
}
