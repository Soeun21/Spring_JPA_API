package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
이론적으로 Getter, Setter 모두 제공하지 않고, 꼭 필요한 별도의 메서드를 제공하는게 가장 이상적이다.
하지만 실무에서 엔티티의 데이터는 조회할 일이 너무 많으므로, Getter의 경우 모두 열어두는 것이 편리하다.
엔티티를 변경할 때는 Setter 대신에 변경 지점이 명확하도록 변경을 위한 비즈니스 메서드를 별도로 제공해야 한다.
 */
@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")//
    private Long id;

    private String  name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // 양방향 맵핑 . 잘 사용안하지만 예시로 만듬
    private List<Order> orders = new ArrayList<>();
    // 컬렉션은 생성할 때 만들어 두고 그 후엔 변경하지 않는다.


}
