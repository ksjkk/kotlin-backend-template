package com.example.basic.app.infrastructure.repository

import com.example.basic.app.domain.entity.Basic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BasicRepository: JpaRepository<Basic, Long> {
}