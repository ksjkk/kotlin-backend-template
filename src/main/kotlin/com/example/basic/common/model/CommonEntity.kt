package com.example.basic.common.model

import com.example.basic.common.enum.YN
import org.hibernate.annotations.DynamicUpdate
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
@DynamicUpdate
abstract class CommonEntity(

    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,

    @CreatedBy
    @Column(updatable = false)
    var createdBy: String? = null,

    @LastModifiedBy
    var updatedBy: String? = null,

    @Enumerated(EnumType.STRING)
    var deletedYn: YN = YN.N

): Serializable