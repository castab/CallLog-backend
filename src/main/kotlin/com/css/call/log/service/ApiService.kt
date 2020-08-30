package com.css.call.log.service

import com.css.call.log.entity.UserEntity
import com.css.call.log.model.Log
import com.css.call.log.model.LogDisplay
import com.css.call.log.model.User
import com.css.call.log.repo.MongoRepo
import com.css.call.log.util.id
import com.css.call.log.util.toUserEntity
import mu.KotlinLogging
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.DeleteMapping
import java.time.ZonedDateTime
import java.util.*

@RestController
class ApiService(private val mongoRepo: MongoRepo) {

    companion object {
        private val log = KotlinLogging.logger{}
    }

    @GetMapping("/users")
    fun getAllUsers(): List<UserEntity> {
        return mongoRepo
                .fetchAllUsersSansLogs()
                .sortedBy { it.id }
    }

    @GetMapping("/logs")
    fun getAllLogs(): List<LogDisplay> {
        return mongoRepo
                .fetchAllUserLogs()
                .map{
                    it.logs
                      .map {log -> LogDisplay("${it.lastName}, ${it.firstName} ${it.middleName?:""}".trim(),it.id,log)}
                }
                .flatten()
                .sortedByDescending { it.log.updatedTs }
    }

    @PostMapping("/user")
    fun createUser(@RequestBody user: User): ResponseEntity<String> {
        if (user.firstName.isBlank() || user.lastName.isBlank()) throw BadRequestException("First and last names are required.")
        return try {
            mongoRepo.insert(user.copy(logs = emptySet()).toUserEntity())
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: DuplicateKeyException) {
            throw BadRequestException("User already exists.")
        }
    }

    @GetMapping("/user/{id}")
    fun findById(@PathVariable id: String): UserEntity {
        return when (val result = mongoRepo.findByIdOrNull(id.toUpperCase())) {
            null -> throw ResourceNotFoundException("User not found.")
            else -> result
        }
    }

    @PatchMapping("/user/{id}")
    fun updateUser(@RequestBody(required = true) user: User, @PathVariable id: String): ResponseEntity<String> {
        val result = findById(id)
        return when {
            (result.id == user.id()) -> {
                mongoRepo.save(user.copy(insertedTs = result.insertedTs).toUserEntity())
                ResponseEntity(HttpStatus.CREATED)
            }
            else -> {
                createUser(user.copy(insertedTs = result.insertedTs))
                mongoRepo.delete(result)
                ResponseEntity(HttpStatus.ACCEPTED)
            }
        }
    }

    @GetMapping("/user/{id}/logs")
    fun getUserLogs(@PathVariable id: String): SortedSet<Log> {
        return findById(id).logs.toSortedSet()
    }

    @PostMapping("/user/{id}/log")
    fun createUserLog(@PathVariable id: String, @RequestBody(required = true) log: Log): ResponseEntity<String> {
        return when {
            id.isEmpty() -> throw BadRequestException("User ID is required.")
            log.message.isEmpty() -> throw BadRequestException("Message field cannot be empty")
            else -> {
                val now = ZonedDateTime.now()
                val newLog = log.copy(
                        id = UUID.randomUUID().toString(),
                        updatedTs = now,
                        insertedTs = now)
                val currentUser = findById(id)
                if (currentUser.logs.contains(newLog)) throw BadRequestException("Log already exists.")
                val updatedLogs = if (currentUser.logs.count() >= 100) {
                    currentUser.logs.toSortedSet().let{ it - it.last() + newLog }
                } else {
                    currentUser.logs + newLog
                }.filter{it.id!="0"}.toSet()
                mongoRepo.save(currentUser.copy(logs = updatedLogs))
                ResponseEntity(HttpStatus.CREATED)
            }
        }
    }

    @DeleteMapping("/user/{id}/log")
    fun deleteUserLog(@PathVariable id: String, @RequestBody(required = true) logToDelete: Log): ResponseEntity<String> {
        val user = findById(id)
        return if (user.logs.contains(logToDelete)) {
            mongoRepo.save(user.copy(logs = user.logs - logToDelete))
            ResponseEntity(HttpStatus.ACCEPTED)
        } else {
            throw ResourceNotFoundException("The specified log does not exist.")
        }
    }

    @DeleteMapping("/user/{id}")
    fun deleteById(@PathVariable id: String): ResponseEntity<String> {
        mongoRepo.delete(findById(id))
        return ResponseEntity(HttpStatus.ACCEPTED)
    }
}