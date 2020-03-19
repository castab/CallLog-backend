package com.css.call.log.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class User (
        @JsonProperty("first_name")
        val firstName: String = "",
        @JsonProperty("last_name")
        val lastName: String = "",
        @JsonProperty("middle_name")
        val middleName: String? = null,
        @JsonProperty("email")
        val email: Map<String, String> = emptyMap(),
        @JsonProperty("tel")
        val tel: Map<String, Long> = emptyMap(),
        @JsonProperty("logs")
        val logs: List<Log> = emptyList()
)