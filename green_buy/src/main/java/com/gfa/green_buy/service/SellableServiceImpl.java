package com.gfa.green_buy.service;

import com.gfa.green_buy.model.dto.*;
import com.gfa.green_buy.model.entity.Bid;
import com.gfa.green_buy.model.entity.Money;
import com.gfa.green_buy.model.entity.SellableItem;
import com.gfa.green_buy.model.entity.User;
import com.gfa.green_buy.repository.BidRepository;
import com.gfa.green_buy.repository.MoneyRepository;
import com.gfa.green_buy.repository.SellableItemRepository;
import com.gfa.green_buy.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellableServiceImpl implements SellableItemService {

    private final SellableItemRepository sellableItemRepository;
    private final DecodeJWT decodeJWT;
    private final BidRepository bidRepository;
    private final MoneyRepository moneyRepository;
    private final UserRepository userRepository;


    public SellableServiceImpl(SellableItemRepository sellableItemRepository, DecodeJWT decodeJWT,
                               BidRepository bidRepository, MoneyRepository moneyRepository,
                               UserRepository userRepository) {
        this.sellableItemRepository = sellableItemRepository;
        this.decodeJWT = decodeJWT;
        this.bidRepository = bidRepository;
        this.moneyRepository = moneyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SellableItemDTO create(SellableItemDTO sellableItemDTO, String jwt) {

        SellableItem sellableItem = new SellableItem(sellableItemDTO, decodeJWT.decodeUser(jwt));
        sellableItemRepository.save(sellableItem);
        return new SellableItemDTO(sellableItem);
    }

    @Override
    public List<SellableItemListDTO> listSellableItem(Integer page) {
        if (page<0) throw new IllegalArgumentException("Page cannot be a negative number!");
        Pageable pageable = PageRequest.of(page, 20);
        List<SellableItemListDTO> sellableItemListDTOS = new ArrayList<>();
        for (SellableItem sellableItem : sellableItemRepository.findAllBySoldIsFalse(pageable)) {
            Integer lastOffer = 0;
            if (bidRepository.findTopBySellableItemOrderByOfferDesc(sellableItem) != null)
                lastOffer = bidRepository.findTopBySellableItemOrderByOfferDesc(sellableItem).getOffer();
            sellableItemListDTOS.add(new SellableItemListDTO(sellableItem.getId(), sellableItem.getName(),
                    sellableItem.getPhotoUrl(),lastOffer));
        }
        return sellableItemListDTOS;
    }

    @Override
    public SellableItemDTO makeBid(BidRquestDTO bidRquestDTO, String jwt) {
        User user = decodeJWT.decodeUser(jwt);
        SellableItem sellableItem = sellableItemRepository.getSellableItemById(bidRquestDTO.getId());
        Money money = moneyRepository.findMoneyByUser(user);
        if (sellableItem==null) throw new IllegalArgumentException("Sellable item with given id doesn't exist!");
        if (sellableItem.isSold()) throw new IllegalArgumentException("Sellable item with given id is already sold!");
        if (money.getDollars()< bidRquestDTO.getDollars())
            throw new IllegalArgumentException("You do not have enough money on your account!");
        if (bidRepository.findTopBySellableItemOrderByOfferDesc(sellableItem)!=null){
            if (bidRquestDTO.getDollars()<=bidRepository.findTopBySellableItemOrderByOfferDesc(sellableItem).getOffer())
                throw new IllegalArgumentException("Your bid is too low!");
        }
        bidRepository.save(new Bid(bidRquestDTO.getDollars(),user,sellableItem));
        List<BidDTO> bidDTOS = new ArrayList<>();
        List<Bid> bids = bidRepository.findAllBySellableItemOrderByIdDesc(sellableItem);
        for (Bid bid:bids){
            bidDTOS.add(new BidDTO(bid.getUser().getUsername(),bid.getOffer()));
        }
        if (bidRquestDTO.getDollars()>=sellableItem.getPurchasePrice()){
            sellableItem.setSold(true);
            sellableItemRepository.save(sellableItem);
            money.setDollars(money.getDollars()- bidRquestDTO.getDollars());
            moneyRepository.save(money);
            return new SellableItemDetailBuyerDTO(sellableItem,bidDTOS,user.getUsername());
        }else {
            return new SellableItemDetailDTO(sellableItem,bidDTOS);
        }
    }

    @Override
    public SellableItemDTO showDetails(Long id) {
        SellableItem sellableItem=sellableItemRepository.getSellableItemById(id);
        List<BidDTO> bidDTOS = new ArrayList<>();
        List<Bid> bids = bidRepository.findAllBySellableItemOrderByIdDesc(sellableItem);
        for (Bid bid:bids){
            bidDTOS.add(new BidDTO(bid.getUser().getUsername(),bid.getOffer()));
        }
        return new SellableItemDetailDTO(sellableItem,bidDTOS);
    }
}
