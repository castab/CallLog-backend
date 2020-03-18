package com.css.call.log.service

import com.css.call.log.entity.UserEntity
import com.css.call.log.model.User
import com.css.call.log.repo.MongoRepo
import com.css.call.log.util.id
import com.css.call.log.util.toUserEntity
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ApiService(private val mongoRepo: MongoRepo) {

    companion object {
        private val log = KotlinLogging.logger{}
    }

    @GetMapping("/users")
    fun getAllUsers(): List<UserEntity> {
        return mongoRepo.findAll()
    }

    @PostMapping("/user")
    fun createUser(@RequestBody user: User) {
        when (mongoRepo.findByIdOrNull(user.id())) {
            null -> mongoRepo.insert(user.toUserEntity())
            else -> throw BadRequestException("User already exists.")
        }
    }

    @GetMapping("/user/id/{id}")
    fun findById(@PathVariable id: String): UserEntity {
        return when (val result = mongoRepo.findByIdOrNull(id.toUpperCase())) {
            null -> throw ResourceNotFoundException("User not found.")
            else -> result
        }
    }

    @PatchMapping("/user/id/{id}")
    fun updateUser(@RequestBody user: User, @PathVariable id: String) {
        val result = findById(id)
        when {
            (result.id == user.id()) -> mongoRepo.save(user.toUserEntity())
            else -> {
                mongoRepo.save(user.toUserEntity())
                mongoRepo.delete(result)
            }
        }
    }

    @DeleteMapping("/user/id/{id}")
    fun deleteById(@PathVariable id: String): ResponseEntity<String> {
        mongoRepo.delete(findById(id))
        return ResponseEntity(HttpStatus.ACCEPTED)
    }
}