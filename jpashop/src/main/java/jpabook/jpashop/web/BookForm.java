package jpabook.jpashop.web;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class BookForm {

    private Long id;

    // 공통속성
    private String name;
    private int price;
    private int stockQuantity;

    // 책 관련 속성
    private String author;
    private String isbn;

}