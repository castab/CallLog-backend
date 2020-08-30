package com.css.call.log.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class User (
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