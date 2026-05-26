package com.mundial.hub.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mundial.hub.model.OfertaMarket;

public interface OfertaMarketRepository extends JpaRepository<OfertaMarket, Long> {
	List<OfertaMarket> findByActivaTrue();
}