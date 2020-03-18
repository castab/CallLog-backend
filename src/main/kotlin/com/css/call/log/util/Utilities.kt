package com.css.call.log.util

import com.css.call.log.entity.UserEntity
import com.css.call.log.model.User

fun User.toUserEntity(): UserEntity = UserEntity(
        id = id(),
        firstName = firstName,
        middleName = middleName,
        lastName = lastName,
        tel = tel,
        email = email,
        logs = logs
)

fun User.id(): String = when {
    (middleName.isNullOrEmpty()) -> "${lastName.toUpperCase()}_${firstName.toUpperCase()}"
    else -> "${lastName.toUpperCase()}_${firstName.toUpperCase()}_${middleName.toUpperCase()}"
}

fun List<User>.toUserEntities(): List<UserEntity> = map{it.toUserEntity()}