package parkingnomad.adaptor.out.persistence.refreshtoken;

import org.springframework.data.repository.CrudRepository;

public interface RedisRefreshTokenRepository extends CrudRepository<RedisRefreshTokenEntity, String> {
}
