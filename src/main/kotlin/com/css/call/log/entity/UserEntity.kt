package com.css.call.log.entity

import com.css.call.log.model.Log
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document(collection = "logs")
data class UserEntity (
        @Id
        val id: String,
        val firstName: String = "",
        val lastName: String = "",
        val middleName: String? = null,
        val email: List<Pair<String, String>> = emptyList(),
        val tel: List<Pair<String, Long>> = emptyList(),
        val updatedTs: ZonedDateTime = ZonedDateTime.now(),
        val insertedTs: ZonedDateTime = ZonedDateTime.now(),
        val logs: List<Log> = emptyList()
)