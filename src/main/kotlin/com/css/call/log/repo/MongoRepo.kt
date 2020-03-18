package com.css.call.log.repo

import com.css.call.log.entity.UserEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service

@Service
interface MongoRepo : MongoRepository<UserEntity, String>