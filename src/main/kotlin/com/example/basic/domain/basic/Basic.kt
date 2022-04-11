package com.example.basic.domain.basic

import com.example.basic.basic.dto.BasicDto
import com.example.basic.common.model.CommonEntity
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@DynamicUpdate
@SQLDelete(sql = "UPDATE basic SET deleted_yn = 'Y' where basic_id = ?")
@Where(clause = "deleted_yn = 'N'")
@Table(name = "basic")
data class Basic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basic_id")
    var id: Long = 0,

    @Column(length = 100, nullable = false)
    var content: String = ""

): CommonEntity(){

    fun toDto() = BasicDto(
        id = this.id,
        content = this.content
    )
}