package com.css.call.log.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = false)
data class Log (
        @JsonProperty("message", required = true)
        val message: String,
        @JsonProperty("incoming", required = true)
        val incoming: Boolean,
        @JsonProperty("staff", required = true)
        val staff: String,
        @JsonProperty("vm")
        val vm: String = "",
        @JsonProperty("status")
        val status: Status = Status.Open,
        @JsonProperty("follow_up")
        val followUp: FollowUp = FollowUp.Complete,
        @JsonProperty("updated_ts")
        val updatedTs: ZonedDateTime = ZonedDateTime.now(),
        @JsonProperty("inserted_ts")
        val insertedTs: ZonedDateTime = ZonedDateTime.now()
)

enum class Status {
        Open, Closed
}

enum class FollowUp {
        Complete, Client
}