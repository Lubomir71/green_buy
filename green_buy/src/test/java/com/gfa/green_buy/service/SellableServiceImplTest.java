package com.gfa.green_buy.service;

import com.gfa.green_buy.GreenBuyApplication;
import com.gfa.green_buy.repository.SellableItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = GreenBuyApplication.class)
class SellableServiceImplTest {

    @MockBean
    private SellableItemRepository sellableItemRepository;

    @BeforeAll
    public static void beforeAll(){

    }

    @Test
    void create() {
    }

    @Test
    void listSellableItem() {
    }

    @Test
    void makeBid() {
    }

    @Test
    void showDetails() {
    }
}