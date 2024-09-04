package com.imedia.challenge.db.repository;

import com.imedia.challenge.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,String> {

        @Query("SELECT p FROM ProductEntity p WHERE p.sku = ?1")
        Optional<ProductEntity> findProductBySku(String sku);

}
