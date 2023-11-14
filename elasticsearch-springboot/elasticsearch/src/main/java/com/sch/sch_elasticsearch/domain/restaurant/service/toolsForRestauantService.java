package com.sch.sch_elasticsearch.domain.restaurant.service;

import org.springframework.stereotype.Service;

@Service
public class toolsForRestauantService {

    public static final Double EARTH_RADIUS = 6371.0;

    /**
     * 두 좌표를 입력받아, KM 차이를 반환한다.
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d =EARTH_RADIUS* c;
        return d;
    }

    /**
     * 초기 위경도를 입력받고, km 범위를 입력받아 최소 최대 위경도값을 반환한다.
     * @param lat 초기 위도
     * @param lon 초기 경도
     * @param distance KM 단위의 거리
     * @return 위도와 경도의 범위를 포함하는 배열
     */
    public static double[][] calculateLatLonRange(double lat, double lon, double distance) {
        double latChange = distance / EARTH_RADIUS; // 위도 변화 계산
        double lonChange = Math.asin(Math.sin(latChange) / Math.cos(Math.toRadians(lat))); // 경도 변화 계산

        double minLat = lat - Math.toDegrees(latChange);
        double maxLat = lat + Math.toDegrees(latChange);
        double minLon = lon - Math.toDegrees(lonChange);
        double maxLon = lon + Math.toDegrees(lonChange);

        return new double[][]{{minLat, minLon}, {maxLat, maxLon}};
    }
}
