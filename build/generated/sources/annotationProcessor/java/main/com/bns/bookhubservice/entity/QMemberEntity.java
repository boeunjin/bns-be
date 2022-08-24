package com.bns.bookhubservice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberEntity is a Querydsl query type for MemberEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMemberEntity extends EntityPathBase<MemberEntity> {

    private static final long serialVersionUID = -217029513L;

    public static final QMemberEntity memberEntity = new QMemberEntity("memberEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath member_id = createString("member_id");

    public final StringPath memberEmail = createString("memberEmail");

    public final StringPath memberLocation = createString("memberLocation");

    public final StringPath memberName = createString("memberName");

    public final StringPath memberSlackId = createString("memberSlackId");

    public final StringPath memberTeamName = createString("memberTeamName");

    public QMemberEntity(String variable) {
        super(MemberEntity.class, forVariable(variable));
    }

    public QMemberEntity(Path<? extends MemberEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberEntity(PathMetadata metadata) {
        super(MemberEntity.class, metadata);
    }

}

