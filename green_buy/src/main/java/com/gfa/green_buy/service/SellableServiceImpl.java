package com.gfa.green_buy.service;

import com.gfa.green_buy.model.dto.BidDTO;
import com.gfa.green_buy.model.dto.SellableItemDTO;
import com.gfa.green_buy.model.dto.SellableItemListDTO;
import com.gfa.green_buy.model.entity.Bid;
import com.gfa.green_buy.model.entity.Money;
import com.gfa.green_buy.model.entity.SellableItem;
import com.gfa.green_buy.model.entity.User;
import com.gfa.green_buy.repository.BidRepository;
import com.gfa.green_buy.repository.MoneyRepository;
import com.gfa.green_buy.repository.SellableItemRepository;
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


    public SellableServiceImpl(SellableItemRepository sellableItemRepository, DecodeJWT decodeJWT, BidRepository bidRepository, MoneyRepository moneyRepository) {
        this.sellableItemRepository = sellableItemRepository;
        this.decodeJWT = decodeJWT;
        this.bidRepository = bidRepository;
        this.moneyRepository = moneyRepository;
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
            sellableItemListDTOS.add(new SellableItemListDTO(sellableItem.getName(), sellableItem.getPhotoUrl(),
                    lastOffer));
        }
        return sellableItemListDTOS;
    }

    @Override
    public SellableItemDTO makeBid(BidDTO bidDTO, String jwt) {
        User user = decodeJWT.decodeUser(jwt);
        SellableItem sellableItem = sellableItemRepository.getSellableItemById(bidDTO.getId());
        Money money = moneyRepository.findMoneyByUser(user);
        if (sellableItem==null) throw new IllegalArgumentException("Sellable item with given id doesn't exist!");
        if (sellableItem.isSold()) throw new IllegalArgumentException("Sellable item with given id is already sold!");
        if (money.getDollars()<bidDTO.getDollars())
            throw new IllegalArgumentException("You do not have enough money on your account!");
        if (bidRepository.findTopBySellableItemOrderByOfferDesc(sellableItem)!=null){
            if (bidDTO.getDollars()<=bidRepository.findTopBySellableItemOrderByOfferDesc(sellableItem).getOffer())
                throw new IllegalArgumentException("Your bid is too low!");
        }
 //       if (sellableItem.getPurchasePrice()<bidDTO.getDollars()){
        bidRepository.save(new Bid(bidDTO.getDollars(),user,sellableItem));
        money.setDollars(money.getDollars()-bidDTO.getDollars());
        return new SellableItemDTO(sellableItem);

//        }
    }
}
