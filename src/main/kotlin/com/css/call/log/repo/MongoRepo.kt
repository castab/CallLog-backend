package com.css.call.log.repo

import com.css.call.log.entity.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Service

@Service
interface MongoRepo : MongoRepository<UserEntity, String> {
    @Query(value = "{}", fields = "{logs: 0}")
    fun fetchAllUsersSansLogs() : List<UserEntity>
    @Query(value = "{}", fields = "{logs: 1, firstName: 1, lastName: 1, middleName: 1}")
    fun fetchAllUserLogs() : List<UserEntity>
}