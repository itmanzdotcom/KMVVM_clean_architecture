package com.template.app.data.source.repositories

import com.template.app.data.source.remote.service.AppApi

interface CommonRepository {


}

class CommonRepositoryImPl
constructor(private val apiService: AppApi) : CommonRepository {


}
