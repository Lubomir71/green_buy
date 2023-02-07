package com.gfa.green_buy.controller;

import com.gfa.green_buy.model.dto.SellableItemDTO;
import com.gfa.green_buy.service.SellableItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SellableItemController {

    private final SellableItemService sellableItemService;

    public SellableItemController(SellableItemService sellableItemService) {
        this.sellableItemService = sellableItemService;
    }

    @PostMapping("/create")
    public ResponseEntity<SellableItemDTO> createSellableItem (@RequestBody @Valid SellableItemDTO sellableItemDTO,
                                                               HttpServletRequest request){
        String jwt = request.getHeader("Authorization").substring(7);
         return ResponseEntity.ok().body(sellableItemService.create(sellableItemDTO,jwt));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> invalidArgumentHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }
}
