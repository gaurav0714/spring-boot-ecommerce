package in.nic.sb_ecom.service;

import in.nic.sb_ecom.payload.CartDTO;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCart(String emailId, Long cartId);

    CartDTO updateProductQuantityInCart(Long productId, int delete);

    String deleteProductFromCart(Long cartId, Long productId);
}
