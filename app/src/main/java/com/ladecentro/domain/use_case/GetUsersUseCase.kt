package com.ladecentro.domain.use_case

import com.ladecentro.common.Resource
import com.ladecentro.data.remote.dto.toUser
import com.ladecentro.domain.model.User
import com.ladecentro.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke() : Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading())
            val user = userRepository.getUsers().map { it.toUser() }
            emit(Resource.Success(user))
        } catch (e: HttpException) {
            emit(Resource.Error("HTTP ERROR"))
        } catch (e: IOException) {
            emit(Resource.Error("IO Error"))
        }
    }
}