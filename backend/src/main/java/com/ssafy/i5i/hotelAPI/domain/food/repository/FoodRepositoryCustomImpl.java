package com.ssafy.i5i.hotelAPI.domain.food.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.i5i.hotelAPI.domain.food.entity.Food;

public class FoodRepositoryCustomImpl implements FoodRepositoryCustom{

	@Override
	public Optional<List<Food>> findByCoordinate(BigDecimal latitude, BigDecimal longitude, Long distance) {

		return Optional.empty();
	}
}
