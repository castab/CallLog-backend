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
        val email: List<Pair<String, String>> = emptyList(),
        @JsonProperty("tel")
        val tel: List<Pair<String, Long>> = emptyList(),
        @JsonProperty("logs")
        val logs: List<Log> = emptyList()
)