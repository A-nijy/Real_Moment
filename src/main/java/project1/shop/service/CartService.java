package project1.shop.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project1.shop.domain.entity.Cart;
import project1.shop.domain.entity.Item;
import project1.shop.domain.entity.Member;
import project1.shop.domain.repository.CartRepository;
import project1.shop.domain.repository.ItemRepository;
import project1.shop.domain.repository.MemberRepository;
import project1.shop.dto.innerDto.CartDto;
import project1.shop.dto.innerDto.ItemDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;


    // 장바구니 목록 조회
    @Transactional
    public List<CartDto.CartResponse> showCart(Long id) {

        List<Cart> carts = cartRepository.findByMember_MemberId(id);

        List<CartDto.CartResponse> cartsDto = carts.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return cartsDto;

    }

    // Cart엔티티 -> CartDto 변환 과정 (CartDto 내부에 ItemDto가 있는데 Cart엔티티의 Item을 ItemDto로 변환)
    public CartDto.CartResponse mapToDto(Cart cart) {

        ItemDto.SimpleItemResponse simpleItemDto = new ItemDto.SimpleItemResponse(cart.getItem());

        CartDto.CartResponse cartResponse = new CartDto.CartResponse(cart.getCartId(), simpleItemDto, cart.getStock(), cart.getPrice(), cart.isCheck());

        return cartResponse;
    }


    // 장바구니 추가
    @Transactional
    public void saveCart(Long memberId, CartDto.CartSaveRequest request) {

        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        Item item = itemRepository.findById(request.getItemId()).orElseThrow(IllegalArgumentException::new);

        Cart cart = new Cart(member, item, request.getStock());

        cartRepository.save(cart);
    }


    // 장바구니 삭제
    @Transactional
    public void deleteCart(Long id, Long cartId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(IllegalArgumentException::new);

        cartRepository.delete(cart);
    }


    // 장바구니 수량 수정
    @Transactional
    public void updateStockCart(Long id, CartDto.CartUpdateStockRequest request) {

        Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(IllegalArgumentException::new);

        cart.updateStockCart(request.getStock());
    }


    // 장바구니 구매 체크
    @Transactional
    public void updateCheckCart(Long id, CartDto.CartUpdateCheckRequest request) {

        Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(IllegalArgumentException::new);

        cart.updateCheckCart(request.isCheck());
    }
}
