package com.css.call.log.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
data class Log (
        @JsonProperty("message")
        val message: String = "",
        @JsonProperty("updated_ts")
        val updatedTs: ZonedDateTime = ZonedDateTime.now(),
        @JsonProperty("inserted_ts")
        val insertedTs: ZonedDateTime = ZonedDateTime.now()
)