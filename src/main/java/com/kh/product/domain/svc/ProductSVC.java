package com.kh.product.domain.svc;

import com.kh.product.domain.Product;

import java.util.List;

public interface ProductSVC {
        /**
         * 상품추가
         * @param product
         * @return
         */
        Product add(Product product);

        /**
         * 조회
         */
        Product findByPid(Long productId);

        /**
         * 수정
         */
        int update(Long productId, Product product);

        /**
         * 삭제
         */
        int delete(Long productId);

        /**
         * 전체 목록
         * @return 상품전체
         */
        List<Product> productAll();



}
