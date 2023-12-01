package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)    // junit실행할 떄 스프링과 엮어서 실행하기 위한 것
@SpringBootTest //  스프링부트를 띄운상태로 테스트를 돌리기 위해 필요
@Transactional  // 이게 있어야 롤백이 된다 Rolled back transaction for test: 테스트가 끝나면 롤백될수있게 사용
// 롤백설정을 해두면 insert쿼리가 나갈 필요가 없기 때문에 isnert문이 보이지 않는다
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;


   @Test
//   @Rollback(value = false)// 롤백을 하지 않는다는 의미, 이렇게하면 insert문이 보인다
   // 익숙해지기 전까지는 롤백을 false로 해두고 테스트가 잘 실행되었는지 db로 확인해보는 것이 좋다
   public void 회원가입() throws Exception{
       // given
       Member member = new Member();
       member.setName("kim");

       // when
       Long saveId = memberService.join(member);

       // then
       em.flush();  // insert 쿼리를 강제로 보낸다
       assertEquals(member, memberRepository.fineOne(saveId));

   }    // 종료되면 자동으로 롤백이 된다



   @Test(expected = IllegalStateException.class)    // ()를 작성할 경우 아래 주석의 trycatch를 안해도 됨
   public void 중복_회원_예외() throws Exception{
       // given
       Member member1= new Member();
       member1.setName("kim");

       Member member2= new Member();
       member2.setName("kim");
       
       // when
       memberService.join(member1);
       memberService.join(member2); // 예외가 발생해야 한다!!
       /*try {
           memberService.join(member2); // 예외가 발생해야 한다!!
       }catch (IllegalStateException e){
           return;
       }*/

       // then
       fail("예외가 발생해야 한다");
   }
   
   
   
   
}