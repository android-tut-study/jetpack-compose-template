package com.study.compose.usecase.carts

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.study.compose.core.domain.Mapper
import com.study.compose.core.domain.model.CartDomain
import com.study.domain.carts.models.Cart
import com.study.domain.carts.repository.CartRepo
import kotlinx.coroutines.flow.map

class LazyGetCartsUseCase(
    private val cartRepo: CartRepo,
    private val mapper: Mapper<Cart, CartDomain>
) {

    operator fun invoke(params: Param) = Pager(
        PagingConfig(
            pageSize = params.size,
            enablePlaceholders = params.enablePlaceHolder,
        )
    ) {
        cartRepo.allCartsPagingSource()
    }.flow
        .map { pagingData: PagingData<Cart> -> pagingData.map(mapper) }

    data class Param(val size: Int = 10, val enablePlaceHolder: Boolean = true)
}

