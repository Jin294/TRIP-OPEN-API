package com.ssafy.i5i.hotelAPI.domain.food.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FoodResponseDto {
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Title {
		private Integer attractionId;
		private String attracionName;
		private Double attractionLongitude;
		private Double attractionLatitude;
		private Long foodId;
		private String foodName;
		private String foodType;
		private Double foodLongitude;
		private Double foodLatitude;
		private Integer foodJjim;
		private Integer foodScore;
		private Double foodStar;
		private Integer foodStarUser;

		public FoodResponseDto.TitleD convertToFDto (){
			return new FoodResponseDto.TitleD(this.attractionId, this.attracionName,
				this.attractionLongitude,this.attractionLatitude,
				this.foodId,this.foodName,this.foodType,
				this.foodLongitude, this.foodLatitude, this.foodJjim, this.foodScore, this.foodStar,
				this.foodStarUser, null);
		}
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TitleD {
		private Integer attractionId;
		private String attracionName;
		private Double attractionLongitude;
		private Double attractionLatitude;
		private Long foodId;
		private String foodName;
		private String foodType;
		private Double foodLongitude;
		private Double foodLatitude;
		private Integer foodJjim;
		private Integer foodScore;
		private Double foodStar;
		private Integer foodStarUser;
		private Double Distance;
	}


	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Coordi {
		private Long id;
		private String foodName;
		private String foodType;
		private Double foodLongitude;
		private Double foodLatitude;
		private Integer foodJjim;
		private Integer foodScore;
		private Double foodStar;
		private Integer foodStarUser;
		private Double Distance;
	}
}
