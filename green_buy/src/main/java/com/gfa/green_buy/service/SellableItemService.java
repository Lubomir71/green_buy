package com.gfa.green_buy.service;

import com.gfa.green_buy.model.dto.BidRquestDTO;
import com.gfa.green_buy.model.dto.SellableItemDTO;
import com.gfa.green_buy.model.dto.SellableItemDetailDTO;
import com.gfa.green_buy.model.dto.SellableItemListDTO;

import java.util.List;

public interface SellableItemService {
    SellableItemDTO create (SellableItemDTO sellableItemDTO, String jwt);
    List<SellableItemListDTO> listSellableItem (Integer page);
    SellableItemDTO makeBid (BidRquestDTO bidRquestDTO, String jwt);
    SellableItemDTO showDetails (Long id);
}
