package com.ladecentro.data.remote.dto

data class SendOtpRequest(val action: String, val profile: Profile)

data class Profile(val phone: String)

data class VerifyOptRequest(val otp: String, val profile: Profile)

data class VerifyOtpResponse(val token: String)

data class LogoutRequest(val action: String)