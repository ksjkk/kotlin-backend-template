package com.example.basic.app.common.audit

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(
    basePackages = ["com.example.basic"]
)
@EntityScan(basePackages = ["com.example.basic"])
class JpaAuditConfig {

    @Bean
    fun auditorAware() = Auditor()

    @Bean
    @ConfigurationProperties("spring.jpa.properties.hibernate")
    fun hibernateProperties(): Map<String, Any> = HashMap()

    @Bean
    fun entityManagerFactory(dataSource: DataSource, hibernateProperties: Map<String, Any>): LocalContainerEntityManagerFactoryBean {
        return LocalContainerEntityManagerFactoryBean().let {
            it.dataSource = dataSource
            it.setPackagesToScan("com.example.basic")
            it.jpaVendorAdapter = HibernateJpaVendorAdapter()
            it.setJpaPropertyMap(hibernateProperties.entries.associate { property ->
                "hibernate.${property.key}" to property.value
            })
            it
        }
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager? {
        return JpaTransactionManager().let {
            it.entityManagerFactory = entityManagerFactory
            it
        }
    }
}