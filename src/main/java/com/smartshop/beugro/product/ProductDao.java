package com.smartshop.beugro.product;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao {
    private JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveProduct(Product product) {
        jdbcTemplate.update("Insert into products (name,SKU,qty,status,created_at) values(?,?,?,?,?)"
                , product.getName(),
                product.getSku(),
                product.getQty(),
                product.getStatus(),
                product.getTimestamp());
    }

    public List<Product> listProducts() {
        return jdbcTemplate.query("Select id,name,SKU,qty,status,created_at from products",
                (resultSet, i) -> new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("SKU"),
                        resultSet.getInt("qty"),
                        resultSet.getInt("status"),
                        resultSet.getTimestamp("created_at")
                ));
    }
    public void updateProduct(Product product){
        jdbcTemplate.update("Update products Set name=?,SKU=?,qty=?, status=? Where id=?"
                , product.getName(),
                product.getSku(),
                product.getQty(),
                product.getStatus(),
                product.getId());
    }
    public Product getProductById(long id){
       return jdbcTemplate.queryForObject("Select name,sku,qty,status from products where id=?",
                ((resultSet, i) -> new Product(
                        resultSet.getString("name"),
                        resultSet.getString("SKU"),
                        resultSet.getInt("qty"),
                        resultSet.getInt("status")
                        )),id);
    }
}
