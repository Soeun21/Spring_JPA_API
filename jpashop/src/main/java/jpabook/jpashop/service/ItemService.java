package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기전용으로 실행
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional  // 쓰기도 수행
    public void saveItem(Item item){
        itemRepository.save(item);
    }


    /*
    * 변경 감지 기능 사용
    * */
    @Transactional  // 커밋이 된다
    public void updateItem(Long itemId, int price, String name, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        /*  Item findItem : 영속상태의 객체 */

        /* 무의미하게 set으로 값을 변경하는 것보다 의미있는 메서드를 만들어서 값을 변경하는 것이 좋다
        그래야 어디서 값이 변경되는것인지 정확하게 알 수 있기 때문이다.

        이렇게 사용하면 settet없이 할 수 있다.
        findItem.change(price, name, stockQuantity);
        */
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

        /*영속상태의 객체에 값을 바꿔넣으면 @Transactional을 통해 커밋을 하면서
        * 변경상태를 감지하게 되고 업데이트 쿼리를 자동으로 날리게 된다
        *
        * 가급적 merge보다는 변경감지를 사용하는 것이 좋다. 값을 넣은 속성만 변경하기 때문
        * */
        /*return findItem; merge는 준영속객체를 받아서 영속객체로 반환하게 된다
        * 병합은 모든 속성을 바꾸기 때문에 값이 고정되어있다고 값을 넣지 않으면 null로 변경된다 */
    }


    public List<Item> findItems(){
        return  itemRepository.findAll();
    }


    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }


}
