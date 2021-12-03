package com.example;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.example.controller.ProductController;
import com.example.model.Product;
import com.example.service.ProductService;
import com.sun.org.apache.xerces.internal.util.Status;




@WebMvcTest(controllers=ProductController.class)
@ActiveProfiles("test")
public class ControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService productService;
	
	private List<Product> listProducts;
	
	@BeforeEach
	void setUp() {
		this.listProducts=new ArrayList<Product>();
		this.listProducts.add(new Product(1L, "Product1", "Description1",1111111));
		this.listProducts.add(new Product(2L, "Product2", "Description2",22222222));
		this.listProducts.add(new Product(3L, "Product3", "Description2",33333333));
		
	}
	
	@Test
	void shoudFetchAllProducts() throws Exception{
		when(productService.getAllProduct()).thenReturn(listProducts);
		this.mockMvc.perform(get("/products"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()",is(listProducts.size())));
	}	
	@Test
	void shouldFetchOneProductById() throws Exception {
		final Long productId=1L;
		final Product product=new Product(1L, "Product1", "Description1",1111111);
		
		when(productService.getProductById(productId)).thenReturn(product);
		this.mockMvc.perform(get("/products/{id}", productId))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name",is(product.getName())))
		.andExpect(jsonPath("$.description", is(product.getDescription())))
		.andExpect(jsonPath("$.price", is(product.getPrice())));	
				
	}
	@Test
	void schoudRetrive404IdProductNotFoundById() throws Exception{
		final Long productId=1L;
		final Long productId2=2L;
		Product product=productService.getProductById(productId);
	    
		 when(productService.getProductById(productId)).thenReturn(product);
		this.mockMvc.perform(get("/products/{id}", productId))
		.andExpectAll(status().isNotFound());
        
	}
	
	@Test
	void shoudCreateNewUser() throws Exception {
		when(productService.createProduct(new Product(null, "BBB", "QQQ", 7777777))).thenReturn(new Product(null, "BBB", "QQQ", 7777777));
		  

		new Product(null, "BBB", "QQQ", 7777777)
		.equals(extracted());
		
		
		
	}

	private ResultActions extracted() throws Exception {
		return this.mockMvc.perform(post("/products"));
	}
	@Test
	void shoudDeleteProduct() throws Exception{
		 Long productId=1L;
	    Product product=new Product(1L, "Product1", "Description1",1111111);
	     when(productService.getProductById(productId)).thenReturn(product);
	    doNothing().when(productService).deleteProcut(product.getId());

		this.mockMvc.perform(delete("/products/{id}", product.getId()))
		.andReturn();
		
	}
}
