package com.template.app.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Token(
        @Expose
        @SerializedName("token_type")
        var tokenType: String? = "",
        @Expose
        @SerializedName("access_token")
        var accessToken: String? = "",
        @Expose
        @SerializedName("expires_in")
        var expires: Double? = 0.0,
        @Expose
        @SerializedName("email_verified")
        var emailVerified: Boolean = false,
        @Expose
        @SerializedName("phone_verified")
        var phoneVerified: Boolean = false
)