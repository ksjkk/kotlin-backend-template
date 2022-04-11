package com.example.basic.basic.infrastructure

import com.example.basic.domain.basic.Basic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BasicRepository: JpaRepository<Basic, Long>