package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;


    // 다대다는 테이블 변경이 힘들기 때문에 실무에 사용하지 않는다.
    @ManyToMany // 관계형 DB는 일대다 다대일로 중간에 풀어주는 테이블이 있어야 한다
    @JoinTable(name = "category_item",  // 중간테이블 생성
            joinColumns = @JoinColumn(name = "category_id"),// 중간테이블에 있는 카테고리아이디
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 아이템 쪽에 들어가는 조인 컬럼
    private List<Item> items = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)// 내 부모이기 떄문에 다대일
    @JoinColumn(name = "parent_id")
    private Category parent;


    @OneToMany(mappedBy = "parent") // 자식은 여러개를 가질 수 있기 때문에 일대다 // parent를 바라본다.
    private List<Category> child = new ArrayList<>();



    //==연관관계 메서드==//
    public void addChildCategory(Category child) {
        this.child.add(child);  // 컬렉션에 넣기
        child.setParent(this);  // 부모에 넣기
    }
}
