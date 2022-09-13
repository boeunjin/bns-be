package com.bns.bookhubservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRentalEntity is a Querydsl query type for RentalEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRentalEntity extends EntityPathBase<RentalEntity> {

    private static final long serialVersionUID = -1648900223L;

    public static final QRentalEntity rentalEntity = new QRentalEntity("rentalEntity");

    public final StringPath bookId = createString("bookId");

    public final StringPath channelId = createString("channelId");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isReturn = createBoolean("isReturn");

    public final StringPath memberId = createString("memberId");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public QRentalEntity(String variable) {
        super(RentalEntity.class, forVariable(variable));
    }

    public QRentalEntity(Path<? extends RentalEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRentalEntity(PathMetadata metadata) {
        super(RentalEntity.class, metadata);
    }

}

