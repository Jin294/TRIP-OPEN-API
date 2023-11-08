package com.ssafy.i5i.hotelAPI.domain.food.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.hotel.entity.Attraction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "food")
@AllArgsConstructor
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "food_id")
	private Long id;

	//음식점 이름
	@Column(name = "food_name")
	private String foodName;

	//음식점 카테고리
	@Column(name = "food_type")
	private String foodType;

	//음식점 경도
	@Column(name = "food_longitude")
	private Double foodLongitude;

	//음식점 위도
	@Column(name = "food_latitude")
	private Double foodLatitude;

	//음식점 찜
	@Column(name = "food_jjim")
	private Integer foodJjim;

	//음식점 점수
	@Column(name = "food_score")
	private Integer foodScore;

	//음식점 별점
	@Column(name = "food_star")
	private Double foodStar;

	//음식점 별점을 준 사람의 수
	@Column(name = "food_staruser")
	private Integer foodStarUser;

	public FoodResponseDto convertToDto (){
		return new FoodResponseDto(this.id, this.foodName, this.foodType, this.foodLongitude, this.foodLatitude, this.foodJjim, this.foodScore, this.foodStar, this.foodStarUser, null);
	}

}
