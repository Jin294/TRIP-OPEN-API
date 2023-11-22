package com.ssafy.i5i.hotelAPI.domain.food.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFood is a Querydsl query type for Food
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFood extends EntityPathBase<Food> {

    private static final long serialVersionUID = -866241453L;

    public static final QFood food = new QFood("food");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> restaurantLatitude = createNumber("restaurantLatitude", Double.class);

    public final NumberPath<Integer> restaurantLike = createNumber("restaurantLike", Integer.class);

    public final NumberPath<Double> restaurantLongitude = createNumber("restaurantLongitude", Double.class);

    public final StringPath restaurantName = createString("restaurantName");

    public final NumberPath<Integer> restaurantScore = createNumber("restaurantScore", Integer.class);

    public final NumberPath<Double> restaurantStar = createNumber("restaurantStar", Double.class);

    public final NumberPath<Integer> restaurantStarUser = createNumber("restaurantStarUser", Integer.class);

    public final StringPath restaurantType = createString("restaurantType");

    public QFood(String variable) {
        super(Food.class, forVariable(variable));
    }

    public QFood(Path<? extends Food> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFood(PathMetadata metadata) {
        super(Food.class, metadata);
    }

}

