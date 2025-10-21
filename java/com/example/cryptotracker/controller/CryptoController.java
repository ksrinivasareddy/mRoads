package com.example.cryptotracker.controller;

import com.example.cryptotracker.dto.CryptoPrice;
import com.example.cryptotracker.service.CryptoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/{coin}")
    public CryptoPrice getCrypto(@PathVariable String coin) {
        return cryptoService.getCryptoPrice(coin);
    }
}
