package com.example.basic.domain.auth

import com.example.basic.common.model.CommonEntity
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@DynamicUpdate
@SQLDelete(sql = "UPDATE client_api_key SET deleted_yn = 'Y' where basic_id = ?")
@Where(clause = "deleted_yn = 'N'")
@Table(name = "client_api_key")
class ApiKey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_api_key_id")
    var id: Long? = null,

    @Column(length = 100)
    var clientId: String? = null,

    @Column(length = 300)
    var apiKey: String? = null

): CommonEntity()