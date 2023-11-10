//package com.ssafy.i5i.hotelAPI.domain.hotel.repository;
//
//import com.ssafy.i5i.hotelAPI.domain.hotel.entity.Accommodation;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
// input: accommodation_name -> output: accommodation
//public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
//        @Query("SELECT a FROM Accommodation a " +
//                "JOIN AttractionAccommodation aa ON a.id = aa.id " +
//                "WHERE aa.attraction.contentId = :attractionId")
//        Optional<List<Accommodation>> findByAttractionId(@Param("attractionId") int attractionId);
//    @Query(value = "SELECT *, " +
//            "(6371 * 2 * ASIN(SQRT(POW(SIN(RADIANS(Accommodation.accommodationLatitude  - :latitude) / 2), 2) + " +
//            "COS(RADIANS(:latitude)) * COS(RADIANS(Accommodation.accommodationLatitude)) * POW(SIN(RADIANS(Accommodation.accommodationLongitude - :longitude) / 2), 2))) " +
//            "AS distance " +
//            "FROM Accommodation " +  // FROM 절 추가
//            "HAVING distance <= :distance", nativeQuery = true)
//    Optional<List<Accommodation>> findByCoordinate(@Param("latitude") Double latitude, @Param("longitude") Double longitude,
//                                                  @Param("distance") Integer distance);
//}
