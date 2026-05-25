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

    // Obtener las láminas que ya posee el usuario
    @GetMapping("/mis-laminas")
    public List<Lamina> misLaminas(Authentication auth) {
        return service.misLaminas(auth);
    }

    // Obtener las láminas repetidas del usuario (para vender)
    @GetMapping("/mis-repetidas")
    public List<Lamina> misRepetidas(Authentication auth) {
        return service.misRepetidas(auth);
    }

    // Consultar el saldo de monedas actual
    @GetMapping("/mis-monedas")
    public Integer misMonedas(Authentication auth) {
        return service.misMonedas(auth);
    }

    // Listar todas las láminas existentes en el sistema (para la tienda)
    @GetMapping("/todas-laminas")
    public List<Lamina> todasLasLaminas() {
        return service.todasLasLaminas();
    }

    // Ver las ofertas publicadas por otros usuarios en la comunidad
    @GetMapping("/comunidad")
    public List<OfertaMarket> verComunidad() {
        return service.verMercadoComunidad();
    }

    // Comprar y abrir un sobre (Cuesta 5 monedas)
    @PostMapping("/abrir-sobre")
    public ResponseEntity<?> abrirSobre(Authentication auth) {
        try {
            service.abrirSobre(auth);
            return ResponseEntity.ok("Sobre abierto exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Comprar una lámina específica al sistema (Precio dinámico)
    @PostMapping("/comprar-sistema")
    public ResponseEntity<?> comprarSistema(@RequestParam Long laminaId, Authentication auth) {
        try {
            service.comprarAlSistema(laminaId, auth);
            return ResponseEntity.ok("Compra al sistema realizada con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Publicar una lámina repetida en el mercado de la comunidad
    @PostMapping("/publicar")
    public ResponseEntity<?> publicar(@RequestParam Long laminaId, @RequestParam Integer precio, Authentication auth) {
        try {
            service.publicarOferta(laminaId, precio, auth);
            return ResponseEntity.ok("Lámina publicada en el mercado");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Comprar una lámina a otro usuario (P2P)
    @PostMapping("/comprar-p2p")
    public ResponseEntity<?> comprarP2P(@RequestParam Long ofertaId, Authentication auth) {
        try {
            service.comprarOfertaP2P(ofertaId, auth);
            return ResponseEntity.ok("Compra entre usuarios exitosa");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Recarga simulada de saldo
    @PostMapping("/recargar")
    public ResponseEntity<?> recargarMonedas(@RequestParam Integer cantidad, Authentication auth) {
        service.recargarMonedas(cantidad, auth);
        return ResponseEntity.ok("Recarga exitosa");
    }
}