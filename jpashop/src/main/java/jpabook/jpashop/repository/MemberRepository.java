package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    // 테스트 생성 : ctrl + shift + t

//    @PersistenceContext// EntityManagerFactory의 역할을 어노테이션이 대신한다 => build에서 spring-data-jpa와 application.yml을 통해서 팩토리 설정이 완료된다
//    private EntityManager em;

    private final EntityManager em;


    public void save(Member member){
        em.persist(member); // 영속성컨텍스트에 멤버객체 넣기, 트랜잭션이 실행될 들어간다
        // persist 만 하면 인서트문이 나가지 않는다. flush를 해야 나감
    }

    public Member fineOne(Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }


    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}


