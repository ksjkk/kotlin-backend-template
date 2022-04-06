package com.example.basic.app.domain.entity

import com.example.basic.app.common.audit.Auditor
import com.example.basic.app.common.enum.YN
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
open class CommonEntity(

    @CreatedDate
    @Column(updatable = false)
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,

    @CreatedBy
    @Column(updatable = false)
    var createdBy: String = "",

    @LastModifiedBy
    var updatedBy: String = "",

    @Enumerated(EnumType.STRING)
    var deletedYn: YN = YN.N

): Serializable {

    @PrePersist
    fun prePersist(){
        this.createdBy = Auditor().current
        this.createdAt = LocalDateTime.now()
        this.updatedBy = Auditor().current
        this.updatedAt = LocalDateTime.now()
    }

    @PreUpdate
    fun preUpdate(){
        this.updatedBy = Auditor().current
        this.updatedAt = LocalDateTime.now()
    }
}