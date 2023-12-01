package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    // 테스트 생성 : ctrl + shift + t

    private final EntityManager em;


    public void save(Item item){
        if(item.getId() == null){   // id 값이 없다면 새로 생성한 객체를 의미
            em.persist(item);   // 새로 생성한 객체를 신규로 등록
        } else {    // 값이 있다면 디비에 등록된 값을 가져온 것
            em.merge(item);
            /*변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만,
            병합을 사용하면 모든 속성이 변경된다.
            병합시 값이 없으면 null 로 업데이트 할 위험도 있다. (병합은 모든 필드를 교체한다.)*/
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return  em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }



}


