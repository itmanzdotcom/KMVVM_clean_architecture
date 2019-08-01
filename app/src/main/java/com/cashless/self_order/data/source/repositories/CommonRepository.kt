package com.cashless.self_order.data.source.repositories

import com.cashless.self_order.data.source.remote.service.AppApi

interface CommonRepository {


}

class CommonRepositoryImPl
constructor(private val apiService: AppApi) : CommonRepository {


}
