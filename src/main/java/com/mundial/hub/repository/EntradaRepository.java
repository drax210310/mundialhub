package com.mundial.hub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mundial.hub.model.Entrada;

public interface EntradaRepository
        extends JpaRepository<Entrada, Long>{

    // Entradas de un usuario
    List<Entrada> findByUsuarioUsername(
            String username
    );

    // Buscar si el usuario ya reservó ese partido
    Optional<Entrada> findByUsuarioUsernameAndPartidoId(
            String username,
            Long partidoId
    );

    // Obtener partidos reservados por usuario
    List<Entrada> findByUsuarioUsernameAndEstado(
            String username,
            com.mundial.hub.model.EstadoEntrada estado
    );
}