package com.imedia.challenge;

import com.imedia.challenge.controller.ProductController;
import com.imedia.challenge.domain.product.ProductResponse;
import com.imedia.challenge.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void testGetProductsByList_whenDataExists() throws Exception{
        List<String> skus = Arrays.asList("sku1","sku2");

        ProductResponse productResponseA = new ProductResponse("sku1","product a","this is a product a",BigDecimal.valueOf(10),10);
        ProductResponse productResponseB = new ProductResponse("sku2","product b","this is a product b",BigDecimal.valueOf(20),20);

        when(productService.findProductBySku("sku1")).thenReturn(productResponseA);
        when(productService.findProductBySku("sku2")).thenReturn(productResponseB);

        mockMvc.perform(get("/products")
                        .param("skus", "sku1,sku2")) // Passing the SKUs as request params
                .andExpect(status().isOk()) // Expecting HTTP 200 OK
                .andExpect(jsonPath("$[0].sku").value("sku1"))
                .andExpect(jsonPath("$[0].name").value("product a"))
                .andExpect(jsonPath("$[1].sku").value("sku2"))
                .andExpect(jsonPath("$[1].name").value("product b"));


        Mockito.verify(productService, times(1)).findProductBySku("sku1");
        Mockito.verify(productService, times(1)).findProductBySku("sku2");

    }

    @Test
    void testGetProductsByList_whenDataNotFound() throws Exception {

        when(productService.findProductBySku("sku1")).thenReturn(null);
        when(productService.findProductBySku("sku2")).thenReturn(null);


        mockMvc.perform(get("/products")
                        .param("skus", "sku1,sku2"))
                .andExpect(status().isNotFound());

        Mockito.verify(productService, times(1)).findProductBySku("sku1");
        Mockito.verify(productService, times(1)).findProductBySku("sku2");
    }

    @Test
    void testUpdateProduct() throws Exception {

        String sku = "sku1";
        String name = "Updated Product";
        String description = "Updated Description";
        BigDecimal price = BigDecimal.valueOf(19.99);


        mockMvc.perform(put("/products/{sku}", sku)
                        .param("name", name)
                        .param("description", description)
                        .param("price", price.toString()))
                .andExpect(status().isAccepted());


        Mockito.verify(productService, times(1)).updateProduct(sku, name, description, price);
    }

}
