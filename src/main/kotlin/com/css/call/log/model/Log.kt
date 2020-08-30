package com.css.call.log.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

@JsonIgnoreProperties(ignoreUnknown = false)
data class Log(
        @JsonProperty("id")
        val id: String = "0",
        @JsonProperty("message", required = true)
        val message: String,
        @JsonProperty("updated_ts")
        val updatedTs: ZonedDateTime = ZonedDateTime.now(),
        @JsonProperty("inserted_ts")
        val insertedTs: ZonedDateTime = ZonedDateTime.now()
) : Comparable<Log> {
        override fun compareTo(other: Log): Int {
                if (this.insertedTs > other.insertedTs) return -1
                if (this.insertedTs < other.insertedTs) return 1
                return 0
        }
}
