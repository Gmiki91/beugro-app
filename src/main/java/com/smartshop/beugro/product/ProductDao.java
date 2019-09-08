package com.smartshop.beugro.product;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource){
        jdbcTemplate=new JdbcTemplate(dataSource);
    }

    public void saveProduct(Product product){
        jdbcTemplate.update("Insert into products (name,SKU,qty,status,created_at) values(?,?,?,?,?)"
                ,product.getName(),product.getSku(),product.getQty(),product.getStatus(),product.getTimestamp());
    }
    public List<Product> listProducts(){
        return jdbcTemplate.query("Select name,SKU,qty,status,created_at from products",
                (resultSet, i) -> new Product(
                        resultSet.getString("name"),
                        resultSet.getInt("qty"),
                        resultSet.getString("SKU"),
                        resultSet.getInt("status"),
                        resultSet.getTimestamp("created_at")
                        ));
    }
}
