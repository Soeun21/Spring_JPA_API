package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)// 읽기전용 이라고 알려줌  //transaction.annotation.Transactional;를 사용하는것이 더 좋다
//@AllArgsConstructor // 2. 롬복으로 모든필드가 들어가는 생성자를 만들어준다
@RequiredArgsConstructor        //3. final붙은 것을 생성자 매개변수에 넣어서  생성자 만들어준다
public class MemberService {

//    @Autowired
    private final MemberRepository memberRepository;  //final을 넣는것 추천

   /* 1. 생성자 직접 생성
   @Autowired      // 생성자 Injection으로 만들어 사용하는것이 주로 권장된다
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
     생성자가 하나만 있는 경우에는  @Autowired가 없어도 알아서 생성자 인젝션으로 맞춰준다
*/


    // 회원가입
    @Transactional//(readOnly = false) 기본적으로 false이기 떄문에 전체클래스에 true를 걸어주고 쓰기에만
    public Long join(Member member){//@Transactional를 붙여주어도 된다
        validateDuplicateMember(member);// 중복검사
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //Exception  중복회원이 있을 경우 예외처리
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }


    // 회원전체조회

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

/*    @Transactional(readOnly = true) 낱개로 읽기전용을 하고싶을 땐 각각의 메서드에 붙여준다*/
    public Member findOne(Long  meberid){
        return memberRepository.fineOne(meberid);
    }

}
