package com.gfa.green_buy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.green_buy.GreenBuyApplication;
import com.gfa.green_buy.model.dto.BidDTO;
import com.gfa.green_buy.model.dto.BidRquestDTO;
import com.gfa.green_buy.model.dto.LoginDTO;
import com.gfa.green_buy.model.dto.SellableItemDTO;
import com.gfa.green_buy.model.entity.Money;
import com.gfa.green_buy.model.entity.SellableItem;
import com.gfa.green_buy.model.entity.User;
import com.gfa.green_buy.repository.MoneyRepository;
import com.gfa.green_buy.repository.SellableItemRepository;
import com.gfa.green_buy.repository.UserRepository;
import com.gfa.green_buy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes = GreenBuyApplication.class)
class SellableItemControllerTest {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final SellableItemRepository sellableItemRepository;
    private final MoneyRepository moneyRepository;
    @Autowired
    MockMvc mvc;
    ObjectMapper objectMapper = new ObjectMapper();

    private String token;
    private User user;

    @Autowired
    SellableItemControllerTest(PasswordEncoder passwordEncoder, UserRepository userRepository, UserService userService, SellableItemRepository sellableItemRepository, MoneyRepository moneyRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userService = userService;
        this.sellableItemRepository = sellableItemRepository;
        this.moneyRepository = moneyRepository;
    }

    @BeforeEach
    public void beforeEach() throws Exception {
        user = userRepository.findUserByUsername("test");
        if (user == null) {
            user = userRepository.save(new User("test",
                    passwordEncoder.encode("12345"), "user"));
            moneyRepository.save(new Money(50,user));
        }
        token = userService.createToken(new LoginDTO("test", "12345"));

    }

    @Test
    void createSellableItemOK() throws Exception {
        SellableItemDTO sellableItemDTO = new SellableItemDTO(null, "createSellableItemOK", "Test-description",
                "https://test", 20, 30);
        mvc.perform(post("/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sellableItemDTO)))
                .andDo(print())
                .andExpect(status().isOk());
        SellableItem sellableItem = sellableItemRepository.getTopByOrderByIdDesc();
        assertEquals(sellableItem.getName(), sellableItemDTO.getName());
    }

    @Test
    void invalidArgumentHandler() throws Exception {
        SellableItemDTO sellableItemDTO = new SellableItemDTO(null, null, null,
                "httxfps://test", -5, -5);
        mvc.perform(post("/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sellableItemDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        "{\"photoUrl\":\"Given photo url is not valid url!\"," +
                                "\"name\":\"Given name is null or empty!\"," +
                                "\"description\":\"Given description is null or empty!\"," +
                                "\"startingPrice\":\"Given starting price must be a positive number\"," +
                                "\"purchasePrice\":\"Given purchase price must be a positive number\"}"));
    }

    @Test
    void showItemsOK() throws Exception {

        mvc.perform(get("/list")
                        .header("Authorization", "Bearer " + token)
                        .param("page","0"))
                .andExpect(status().isOk());
    }

    @Test
    void showItemsError() throws Exception {

        mvc.perform(get("/list")
                        .header("Authorization", "Bearer " + token)
                        .param("page","-5"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(" {\"error\":\"Page cannot be a negative number!\"}"));
    }

    @Test
    void createBidOK() throws Exception {

        SellableItemDTO sellableItemDTO = new SellableItemDTO(null, "createBidOK", "Test-description",
                "https://test", 20, 30);
        SellableItem sellableItem = sellableItemRepository.save( new SellableItem(sellableItemDTO,user));
        Long id = sellableItem.getId();
        BidRquestDTO bidRquestDTO = new BidRquestDTO(id,30);
        mvc.perform(post("/bid")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bidRquestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(sellableItemDTO.getName()));
//        assertEquals(sellableItem.isSold(),true);
    }

    @Test
    void createBidError() throws Exception {

        SellableItemDTO sellableItemDTO = new SellableItemDTO(null, "createBidOK", "Test-description",
                "https://test", 20, 30);
        SellableItem sellableItem = sellableItemRepository.save( new SellableItem(sellableItemDTO,user));
        Long id = sellableItem.getId();
        BidRquestDTO bidRquestDTO = new BidRquestDTO(id,70);
        mvc.perform(post("/bid")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bidRquestDTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"You do not have enough money on your account!\"}"));
        assertEquals(sellableItem.isSold(),false);
    }

    @Test
    void showItemOK() throws Exception{

        SellableItemDTO sellableItemDTO = new SellableItemDTO(null, "showItemOK", "Test-description",
                "https://test", 20, 30);
        SellableItem sellableItem = sellableItemRepository.save( new SellableItem(sellableItemDTO,user));
        Long id = sellableItem.getId();
        mvc.perform(get("/item/"+id)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sellableItemDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(sellableItemDTO.getName()));

    }

    @Test
    void showItemError() throws Exception{

        mvc.perform(get("/item/-5")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"error\":\"Sellable item with given id doesn't exist!\"}"));
    }
}