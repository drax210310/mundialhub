package com.mundial.hub.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.mundial.hub.model.Lamina;
import com.mundial.hub.model.OfertaMarket;
import com.mundial.hub.service.AlbumService;

@RestController
@RequestMapping("/api/album")
@CrossOrigin(origins = "http://localhost:8081")
public class AlbumController {

    @Autowired
    private AlbumService service;

    @GetMapping("/mis-laminas")
    public List<Lamina> misLaminas(Authentication auth) {
        return service.misLaminas(auth);
    }

    @GetMapping("/mis-repetidas")
    public List<Lamina> misRepetidas(Authentication auth) {
        return service.misRepetidas(auth);
    }

    @GetMapping("/mis-monedas")
    public Integer misMonedas(Authentication auth) {
        return service.misMonedas(auth);
    }

    @GetMapping("/todas-laminas")
    public List<Lamina> todasLasLaminas() {
        return service.todasLasLaminas();
    }

    @GetMapping("/comunidad")
    public List<OfertaMarket> verComunidad() {
        return service.verMercadoComunidad();
    }

    @PostMapping("/abrir-sobre")
    public ResponseEntity<?> abrirSobre(Authentication auth) {
        try {
            service.abrirSobre(auth);
            return ResponseEntity.ok("Sobre abierto exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/comprar-sistema")
    public ResponseEntity<?> comprarSistema(@RequestParam Long laminaId, Authentication auth) {
        try {
            service.comprarAlSistema(laminaId, auth);
            return ResponseEntity.ok("Compra al sistema realizada con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/publicar")
    public ResponseEntity<?> publicar(@RequestParam Long laminaId, @RequestParam Integer precio, Authentication auth) {
        try {
            service.publicarOferta(laminaId, precio, auth);
            return ResponseEntity.ok("Lámina publicada en el mercado");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/comprar-p2p")
    public ResponseEntity<?> comprarP2P(@RequestParam Long ofertaId, Authentication auth) {
        try {
            service.comprarOfertaP2P(ofertaId, auth);
            return ResponseEntity.ok("Compra entre usuarios exitosa");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/recargar")
    public ResponseEntity<?> recargarMonedas(@RequestParam Integer cantidad, Authentication auth) {
        service.recargarMonedas(cantidad, auth);
        return ResponseEntity.ok("Recarga exitosa");
    }
}