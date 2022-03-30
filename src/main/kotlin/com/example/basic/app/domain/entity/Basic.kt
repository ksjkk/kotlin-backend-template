package com.example.basic.app.domain.entity

import com.example.basic.app.domain.dto.BasicDto
import javax.persistence.*

@Entity
@Table
class Basic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basic_id")
    var id: Long = 0,

    @Column(length = 100)
    var content: String?
){
    fun toDto(): BasicDto {
        return BasicDto(
            id = this.id,
            content = this.content
        )
    }
}