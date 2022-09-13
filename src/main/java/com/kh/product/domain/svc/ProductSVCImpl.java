package com.kh.product.domain.svc;

import com.kh.product.domain.Product;
import com.kh.product.domain.dao.ProductDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSVCImpl implements ProductSVC{

    private final ProductDAO productDAO;

    /**
     * 상품추가
     *
     * @param product
     * @return
     */
    @Override
    public Product add(Product product) {
        Long generateProductId = productDAO.makePid();
        product.setPid(generateProductId);
        productDAO.add(product);
        return productDAO.findByPid(generateProductId);
    }

    /**
     * 조회
     *
     */
    @Override
    public Product findByPid(Long productId) {
        return productDAO.findByPid(productId);
    }

    /**
     * 수정
     *
     */
    @Override
    public int update(Long productId, Product product) {
        int cnt = productDAO.update(productId,product);
        log.info("수정갯수={}",cnt);
        return cnt;
    }

    /**
     * 삭제
     *
     */
    @Override
    public int delete(Long productId) {
        int cnt = productDAO.delete(productId);
        log.info("삭제갯수={}",cnt);
        return cnt;
    }

    /**
     * 전체 목록
     *
     * @return 상품전체
     */
    @Override
    public List<Product> productAll() {
        return productDAO.productAll();
    }


}
