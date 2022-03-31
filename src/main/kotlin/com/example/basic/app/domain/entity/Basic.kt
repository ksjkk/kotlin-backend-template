package com.example.basic.app.domain.entity

import com.example.basic.app.domain.dto.BasicDto
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
    var id: Long? = null,

    @Column(length = 100)
    var content: String? = null

): CommonEntity(){

    fun toDto() = BasicDto(
                        id = this.id,
                        content = this.content
                    )
}