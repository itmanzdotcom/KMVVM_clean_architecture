package com.cashless.self_order.data.source.remote.api.error

import com.cashless.self_order.util.InternetManager
import com.ccc.jobchat.data.source.remote.api.error.Type
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RetrofitException : RuntimeException {

  private val errorType: String
  private lateinit var response: Response<*>
  private var errorResponse: ErrorResponse? = null
  private val TIME_OUT = "タイムアウトに達しました"
  private val SERVER_ERROR = "システムエラーが発生しました"
  private val CAN_NOT_CONNECT_TO_SERVER = "インターネット接続がオフラインのようです。"


  private constructor(type: String, cause: Throwable) : super(cause.message, cause) {
    this.errorType = type
  }

  private constructor(type: String, response: Response<*>) {
    this.errorType = type
    this.response = response
  }

  constructor(type: String, response: ErrorResponse?) {
    this.errorType = type
    this.errorResponse = response
  }

  fun getMsgError(): String? {
    return when (errorType) {
      Type.SERVER -> {
        return if (errorResponse?.code == VALIDATE_ERROR_CODE) {
          errorResponse?.messageList
        } else {
          errorResponse?.message
        }
      }
      else -> getError(errorType)
    }
  }

  fun getErrorCode() = errorResponse?.code

  fun getMsgErrorList(): String? {
    return when (errorType) {
      Type.SERVER -> errorResponse?.messageList
      else -> getError(errorType)
    }
  }

  fun getAllMsgError(): String? {
    return when (errorType) {
      Type.SERVER -> errorResponse?.allMessage
      else -> getError(errorType)
    }
  }

  private fun getError(type: String): String? {
    return when (type) {
      Type.NETWORK -> getNetworkErrorMessage(cause)
      Type.HTTP -> errorResponse?.code?.getHttpErrorMessage()
      else -> {
        if (!InternetManager.isConnected()) {
          return CAN_NOT_CONNECT_TO_SERVER
        }
        return SERVER_ERROR
      }
    }
  }

  private fun getNetworkErrorMessage(throwable: Throwable?): String {
    if (throwable is SocketTimeoutException) {
      return throwable.message.toString()
    }

    if (throwable is UnknownHostException) {
      return throwable.message.toString()
    }

    if (throwable is IOException) {
      return throwable.message.toString()
    }

    return throwable?.message.toString()
  }

  private fun Int.getHttpErrorMessage(): String {
    if (this in 300..308) {
      // Redirection
      return "It was transferred to a different URL. I'm sorry for causing you trouble"
    }
    if (this in 400..451) {
      // Client error
      return "An error occurred on the application side. Please try again later!"
    }
    if (this in 500..511) {
      // Server error
      return "A server error occurred. Please try again later!"
    }

    // Unofficial error
    return "An error occurred. Please try again later!"
  }

  companion object {

    private const val VALIDATE_ERROR_CODE = 422

    fun toNetworkError(cause: Throwable): RetrofitException {
      return RetrofitException(Type.NETWORK, cause)
    }

    fun toHttpError(response: ErrorResponse?): RetrofitException {
      return RetrofitException(Type.HTTP, response)
    }

    fun toUnexpectedError(cause: Throwable): RetrofitException {
      return RetrofitException(Type.UNEXPECTED, cause)
    }

    fun toServerError(response: ErrorResponse?): RetrofitException {
      return RetrofitException(Type.SERVER, response)
    }
  }

}