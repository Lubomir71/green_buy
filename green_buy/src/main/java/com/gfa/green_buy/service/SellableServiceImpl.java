package com.gfa.green_buy.service;

import com.gfa.green_buy.model.dto.SellableItemDTO;
import com.gfa.green_buy.model.entity.SellableItem;
import com.gfa.green_buy.repository.SellableItemRepository;
import org.springframework.stereotype.Service;

@Service
public class SellableServiceImpl implements SellableItemService {

    private final SellableItemRepository sellableItemRepository;
    private final DecodeJWT decodeJWT;

    public SellableServiceImpl(SellableItemRepository sellableItemRepository, DecodeJWT decodeJWT) {
        this.sellableItemRepository = sellableItemRepository;
        this.decodeJWT = decodeJWT;
    }

    @Override
    public SellableItemDTO create(SellableItemDTO sellableItemDTO,String jwt) {

        SellableItem sellableItem = new SellableItem(sellableItemDTO,decodeJWT.decodeUser(jwt));
        sellableItemRepository.save(sellableItem);
        return new SellableItemDTO(sellableItem);
    }
}
