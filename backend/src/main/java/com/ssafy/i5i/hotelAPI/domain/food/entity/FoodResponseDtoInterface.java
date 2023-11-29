package com.ssafy.i5i.hotelAPI.domain.food.entity;

public interface FoodResponseDtoInterface {
	Long getId();
	String getRestaurantName();
	String getRestaurantType();
	Double getRestaurantLongitude();
	Double getRestaurantLatitude();
	Integer getRestaurantLike();
	Integer getRestaurantScore();
	Double getRestaurantStar();
	Integer getRestaurantStarUser();
	Double getDistance();
}
