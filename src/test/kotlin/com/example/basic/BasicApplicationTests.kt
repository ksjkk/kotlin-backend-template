package com.example.basic

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*
import kotlin.NoSuchElementException

//@SpringBootTest
class BasicApplicationTests {

	@Test
	fun contextLoads() {
	}

	@Test
	fun simpleTest() {
		val optional: Optional<String> = Optional.empty()
		val elseGet = optional.orElseGet { "" }
		println("elseGet : $elseGet")
		Assertions.assertThrows(NoSuchElementException::class.java) { optional.get() }
	}

	@Test
	fun simpleTest2() {
		val optional = Optional.of("aa")
		val geto = optional.orElseGet { "" }
		assertThat(geto).isEqualTo("aa")
	}

	@Test
	fun simpleTest3() {
		val loc = LocalDateTime.now().plusSeconds(36000)
		val date = Date.from(loc.atZone(ZoneId.systemDefault()).toInstant())
		println("loc : $loc")
		println("date : $date")
		println(Date(Date().time + 36000 * 1000))
	}

	@Test
	fun stringTest() {
		val nullString: String? = null
		val emptyString = ""
		val blankString = " "

		assertThat(nullString.orEmpty().isEmpty()).isTrue
		assertThat("a".orEmpty().isNotEmpty()).isTrue
		assertThat(emptyString.isEmpty()).isTrue
		assertThat(emptyString.isBlank()).isTrue
		assertThat(blankString.isEmpty()).isFalse
		assertThat(blankString.isBlank()).isTrue
	}

	@Test
	fun substrTest() {
		val str = "123456789"
		println(str.substring(0, 5))
	}

	@Test
	fun dto(){
		val listDto1 = ListDto(listOf("string1","string2"), "string")
		val listDto2 = ListDto(null, "string")
		val dto1 = Dto("string")
		val dto2 = Dto(null)

		println("*******")
		println(listDto1)
		println(listDto2)
		println(dto1)
		println(dto2)
	}

}

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ListDto(
	var list: List<String>? = null,
	var string: String? = null
)

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Dto(
	var string: String? = null
)