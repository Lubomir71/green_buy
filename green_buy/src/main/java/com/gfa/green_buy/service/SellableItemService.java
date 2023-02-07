package com.gfa.green_buy.service;

import com.gfa.green_buy.model.dto.SellableItemDTO;

public interface SellableItemService {
    SellableItemDTO create (SellableItemDTO sellableItemDTO, String jwt);
}
