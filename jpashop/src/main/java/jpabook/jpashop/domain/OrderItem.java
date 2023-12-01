package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본생성자를 외부에서 불러낼 수 없게 막는다
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item; //주문 상품


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //주문


    private int orderPrice; //주문 가격

    private int count; //주문 수량

//    protected OrderItem(){} // 기본생성자를 사용할 수 없게 막는다


    // == 생성 메서드 ==
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);    // 재고에서 수량 빼기
        return  orderItem;
    }


    // == 비즈니스 로직==

    // 주문취소
    public void cancel() {// 주문 취소될 경우 재고 수량을 다시 돌려 주어야 한다
        getItem().addStock(count);
    }

    //==조회 로직==//
    // 전체 주문 가격 조회
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
