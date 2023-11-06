package com.ssafy.i5i.hotelAPI.domain.food.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.i5i.hotelAPI.domain.food.entity.Food;


public interface FoodRepository extends JpaRepository<Food, Long>,FoodRepositoryCustom {

}
