package parkingnomad.adaptor.out.persistence.member;

import org.springframework.stereotype.Component;
import parkingnomad.domain.Member;

@Component
public class JpaMemberMapper {
    public JpaMemberEntity toJpaMemberEntity(final Member member) {
        return new JpaMemberEntity(
                member.getId(),
                member.getSub(),
                member.getName(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }

    public Member toMemberDomainEntity(final JpaMemberEntity jpaMemberEntity) {
        return Member.createWithId(
                jpaMemberEntity.getId(),
                jpaMemberEntity.getSub(),
                jpaMemberEntity.getName(),
                jpaMemberEntity.getCreatedAt(),
                jpaMemberEntity.getUpdatedAt()
        );
    }
}
