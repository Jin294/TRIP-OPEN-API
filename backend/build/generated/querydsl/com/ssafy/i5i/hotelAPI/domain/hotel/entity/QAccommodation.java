package com.ssafy.i5i.hotelAPI.domain.hotel.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccommodation is a Querydsl query type for Accommodation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccommodation extends EntityPathBase<Accommodation> {

    private static final long serialVersionUID = 550517531L;

    public static final QAccommodation accommodation = new QAccommodation("accommodation");

    public final StringPath accommodationAddr = createString("accommodationAddr");

    public final NumberPath<Long> accommodationId = createNumber("accommodationId", Long.class);

    public final StringPath accommodationImg = createString("accommodationImg");

    public final NumberPath<Double> accommodationLatitude = createNumber("accommodationLatitude", Double.class);

    public final NumberPath<Double> accommodationLongitude = createNumber("accommodationLongitude", Double.class);

    public final StringPath accommodationName = createString("accommodationName");

    public final NumberPath<Long> accommodationPrice = createNumber("accommodationPrice", Long.class);

    public final NumberPath<Double> accommodationScore = createNumber("accommodationScore", Double.class);

    public final StringPath accommodationType = createString("accommodationType");

    public QAccommodation(String variable) {
        super(Accommodation.class, forVariable(variable));
    }

    public QAccommodation(Path<? extends Accommodation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccommodation(PathMetadata metadata) {
        super(Accommodation.class, metadata);
    }

}

