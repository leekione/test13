package com.kh.product.domain.dao;

import com.kh.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO{

    private final JdbcTemplate jt;
    /**
     * 상품추가
     *
     * @param product
     * @return
     */
    @Override
    public int add(Product product) {
        int result = 0;
        StringBuffer sql = new StringBuffer();
        sql.append("insert into product (pid,pname,count,price) ");
        sql.append("values(?,?,?,?) ");

        result = jt.update(sql.toString(),product.getPid(),product.getPname(),product.getCount(),product.getPrice());

        return result;

    }

    /**
     * 상품번호생성
     */
    @Override
    public Long makePid() {
        String sql = "select product_pid_seq.nextval from dual ";
        Long productId = jt.queryForObject(sql,Long.class);
        return productId;
    }

    /**
     * 조회
     *
     */
    @Override
    public Product findByPid(Long productId) {
        StringBuffer sql = new StringBuffer();

        sql.append("select pid,pname,count,price ");
        sql.append("  from product ");
        sql.append(" where pid = ? ");

        Product findedProduct = null;
        try {
            findedProduct = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Product.class),productId);
        } catch (DataAccessException e) {
            log.info("찾고자하는 상품이 없습니다={}",productId);
        }
        return findedProduct;
    }

    /**
     * 수정
     *
     */
    @Override
    public int update(Long productId, Product product) {
        int result = 0;
        StringBuffer sql = new StringBuffer();

        sql.append("update product ");
        sql.append("   set pname = ?, ");
        sql.append("       count = ?, ");
        sql.append("       price = ? ");
        sql.append(" where pid = ? ");

      result = jt.update(sql.toString(),product.getPname(),product.getCount(),product.getPrice(),productId);
      return result;

    }

    /**
     * 삭제
     */
    @Override
    public int delete(Long productId) {
        int result = 0;
        String sql = "delete from product where pid = ? ";

        result = jt.update(sql,productId);
        return result;


    }

    /**
     * 전체 목록
     *
     * @return 상품전체
     */
    @Override
    public List<Product> productAll() {
        StringBuffer sql = new StringBuffer();
        sql.append("select pid,pname,count,price");
        sql.append(" from product ");
        return jt.query(sql.toString(),new BeanPropertyRowMapper<>(Product.class));
    }


}
