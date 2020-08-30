package com.css.call.log.entity

import com.css.call.log.model.Log
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document(collection = "logs")
data class UserEntity (
        @Id
        val id: String,
        @JsonProperty("first_name")
        val firstName: String,
        @JsonProperty("last_name")
        val lastName: String,
        @JsonProperty("middle_name")
        val middleName: String? = null,
        @JsonProperty("alias")
        val alias: String? = null,
        @JsonProperty("email")
        val email: Map<String, String> = emptyMap(),
        @JsonProperty("tel")
        val tel: Map<String, Long> = emptyMap(),
        @JsonProperty("updated_ts")
        val updatedTs: ZonedDateTime = ZonedDateTime.now(),
        @JsonProperty("inserted_ts")
        val insertedTs: ZonedDateTime = ZonedDateTime.now(),
        @JsonProperty("logs")
        val logs: Set<Log> = emptySet()
)