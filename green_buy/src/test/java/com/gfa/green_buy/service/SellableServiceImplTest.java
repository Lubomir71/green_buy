package com.gfa.green_buy.service;

import com.gfa.green_buy.GreenBuyApplication;
import com.gfa.green_buy.model.dto.*;
import com.gfa.green_buy.model.entity.Bid;
import com.gfa.green_buy.model.entity.Money;
import com.gfa.green_buy.model.entity.SellableItem;
import com.gfa.green_buy.model.entity.User;
import com.gfa.green_buy.repository.BidRepository;
import com.gfa.green_buy.repository.MoneyRepository;
import com.gfa.green_buy.repository.SellableItemRepository;
import com.gfa.green_buy.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest(classes = GreenBuyApplication.class)
class SellableServiceImplTest {

    SellableItemService sellableItemService;
    @Mock
    DecodeJWT decodeJWT;
    @Mock
    SellableItemRepository sellableItemRepository;
    @Mock
    BidRepository bidRepository;
    @Mock
    MoneyRepository moneyRepository;
    @Mock
    UserRepository userRepository;

    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        sellableItemService = new SellableServiceImpl(sellableItemRepository,decodeJWT,bidRepository,moneyRepository
                ,userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createOK() {
        SellableItemDTO sellableItemDTO = new SellableItemDTO(null, "createOK", "Test-description",
                "https://test", 20, 30);
        User user = new User("test", "12345", "user");
        String jwt = "jwt";
        when(decodeJWT.decodeUser(any())).thenReturn(user);
        when(sellableItemRepository.save(any())).thenReturn(new SellableItem(sellableItemDTO,user));
        SellableItemDTO sellableItemDTOResult = sellableItemService.create(sellableItemDTO,jwt);
        verify(sellableItemRepository,times(1)).save(any());
        assertEquals(sellableItemDTOResult.getName(),"createOK");
    }

    @Test
    void listSellableItemOK() {
        User user = new User("test", "12345", "user");
        SellableItemDTO sellableItemDTO = new SellableItemDTO(null, "listSellableError",
                "Test-description","https://test", 20, 30);
        SellableItem sellableItem = new SellableItem(sellableItemDTO,user);
        Bid bid = new Bid(50,user,sellableItem);
        List<SellableItem> items = new ArrayList<>();
        items.add(sellableItem);
        when(sellableItemRepository.findAllBySoldIsFalse(any())).thenReturn(items);
        when(bidRepository.findTopBySellableItemOrderByOfferDesc(any())).thenReturn(bid);

        List<SellableItemListDTO> list = sellableItemService.listSellableItem(0);

        verify(sellableItemRepository,times(1)).findAllBySoldIsFalse(any());
        verify(bidRepository,times(2)).findTopBySellableItemOrderByOfferDesc(any());
        assertEquals(1,list.size());
    }

    @Test
    void listSellableError() {

        Throwable ex = assertThrows(IllegalArgumentException.class,()->sellableItemService.listSellableItem(-5));
        assertEquals("Page cannot be a negative number!",ex.getMessage());
    }

    @Test
    void makeBidOK(){
        SellableItemDTO sellableItemDTO = new SellableItemDTO(null, "makeBidOK", "Test-description",
                "https://test", 20, 30);
        User user = new User("test", "12345", "user");
        Money money = new Money(30,user);
        SellableItem sellableItem = new SellableItem(sellableItemDTO,user);
        String jwt = "jwt";
        BidRquestDTO bidRquestDTO = new BidRquestDTO(1l,30);
        Bid bid =new Bid(15,user,sellableItem);

        when(decodeJWT.decodeUser(any())).thenReturn(user);
        when(sellableItemRepository.getSellableItemById(any())).thenReturn(sellableItem);
        when(moneyRepository.findMoneyByUser(any())).thenReturn(money);
        when(bidRepository.findTopBySellableItemOrderByOfferDesc(any())).thenReturn(bid);

        SellableItemDTO sellableItemDTOResponse = sellableItemService.makeBid(bidRquestDTO,jwt);
        assertEquals(0,money.getDollars());
        assertTrue(sellableItem.isSold());
        assertEquals(SellableItemDetailBuyerDTO.class,sellableItemDTOResponse.getClass());
    }


    @Test
    void makeBidError() {
        SellableItemDTO sellableItemDTO = new SellableItemDTO(null, "makeBidError", "Test-description",
                "https://test", 20, 30);
        User user = new User("test", "12345", "user");
        Money money = new Money(10,user);
        SellableItem sellableItem = new SellableItem(sellableItemDTO,user);
        sellableItem.setSold(true);
        String jwt = "jwt";
        BidRquestDTO bidRquestDTO = new BidRquestDTO(1l,20);
        Bid bid =new Bid(15,user,sellableItem);

        when(decodeJWT.decodeUser(any())).thenReturn(user);
        when(sellableItemRepository.getSellableItemById(1l)).thenReturn(sellableItem);
        when(sellableItemRepository.getSellableItemById(2l)).thenReturn(null);
        when(moneyRepository.findMoneyByUser(any())).thenReturn(money);
        when(bidRepository.findTopBySellableItemOrderByOfferDesc(any())).thenReturn(bid);

        bidRquestDTO.setId(2l);
        Throwable ex = assertThrows(IllegalArgumentException.class,()->sellableItemService.makeBid(bidRquestDTO,jwt));
        assertEquals("Sellable item with given id doesn't exist!",ex.getMessage());

        bidRquestDTO.setId(1l);
        ex = assertThrows(IllegalArgumentException.class,()->sellableItemService.makeBid(bidRquestDTO,jwt));
        assertEquals("Sellable item with given id is already sold!",ex.getMessage());

        sellableItem.setSold(false);
        ex = assertThrows(IllegalArgumentException.class,()->sellableItemService.makeBid(bidRquestDTO,jwt));
        assertEquals("You do not have enough money on your account!",ex.getMessage());

        bidRquestDTO.setDollars(10);
        ex = assertThrows(IllegalArgumentException.class,()->sellableItemService.makeBid(bidRquestDTO,jwt));
        assertEquals("Your bid is too low!",ex.getMessage());
    }

    @Test
    void showDetails() {
        User user = new User("test", "12345", "user");
        SellableItemDTO sellableItemDTO = new SellableItemDTO(null, "listSellableError",
                "Test-description","https://test", 20, 30);
        SellableItem sellableItem = new SellableItem(sellableItemDTO,user);
        sellableItem.setSold(true);
        Bid bid = new Bid(30,user,sellableItem);
        List<Bid> bids = new ArrayList<>();
        bids.add(bid);

        when(sellableItemRepository.getSellableItemById(1l)).thenReturn(sellableItem);
        when(bidRepository.findAllBySellableItemOrderByIdDesc(any())).thenReturn(bids);
        when(bidRepository.findTopBySellableItemOrderByOfferDesc(any())).thenReturn(bid);

        SellableItemDTO sellableItemDTOResponse = sellableItemService.showDetails(1l);

        verify(sellableItemRepository,times(1)).getSellableItemById(1l);
        verify(bidRepository,times(1)).findAllBySellableItemOrderByIdDesc(any());
        verify(bidRepository,times(1)).findTopBySellableItemOrderByOfferDesc(any());

        assertEquals(SellableItemDetailBuyerDTO.class,sellableItemDTOResponse.getClass());
    }

    @Test
    void showDetailsError() {

        when(sellableItemRepository.getSellableItemById(any())).thenReturn(null);

        Throwable ex = assertThrows(IllegalArgumentException.class,()->sellableItemService.showDetails(1l));
        assertEquals("Sellable item with given id doesn't exist!",ex.getMessage());
    }
}