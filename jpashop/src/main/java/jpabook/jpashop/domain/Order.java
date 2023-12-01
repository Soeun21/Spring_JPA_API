package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본생성자를 외부에서 불러낼 수 없게 막는다
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //주문 회원


    // 오더아이템스에 오더를 저장하면 오더에도 자동저장된다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
   /*
    -- cascade 적용x
    persist(item1) persist(item2) persist(item3)
    persist(order)

    --cascade 적용시
    persist(order)
*/
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; //배송정보


    private LocalDateTime orderDate; //주문시간


    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]


    //==연관 관계 메서드==//
    // 연관 관계 편의 메서드는 컨트롤하는 쪽이 가지고 있는다
    public void  setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    // == 생성 메서드 ==

    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);     // 상태를 기본으로 Order상태로 해둔다
        order.setOrderDate(LocalDateTime.now());    // 현재시간 입력
        return  order;
    }

    // == 비즈니스 로직==

    // 주문취소
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){    //  배송완료상태일 경우 예외터트리기
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능 합니다");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }


    // == 조회 로직 ==

    // 전체 주문 가격 조회
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
