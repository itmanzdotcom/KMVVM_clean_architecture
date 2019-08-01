package com.template.app.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @field:SerializedName("id")
    var id: Int = UNKNOWN_ID,
    @Expose
    @field:SerializedName("name")
    var name: String? = "",
    @Expose
    @SerializedName("full_name")
    var fullName: String? = "",
    @Expose
    @SerializedName("description")
    var description: String? = "",
    @Expose
    @SerializedName("owner")
    var owner: Owner? = null,

    var isFavorite: Boolean = false
) {

  data class Owner(
      @Expose
      @field:SerializedName("login")
      var login: String? = "",
      @Expose
      @field:SerializedName("avatar_url")
      var avatarUrl: String? = ""
  )

  companion object {
    const val UNKNOWN_ID = -1
  }
}
