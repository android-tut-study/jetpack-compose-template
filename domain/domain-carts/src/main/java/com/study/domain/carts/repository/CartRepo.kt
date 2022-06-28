package com.study.domain.carts.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.study.compose.core.domain.model.CartDomain
import com.study.domain.carts.models.Cart
import com.study.domain.carts.models.CartChange
import com.study.domain.carts.models.CartRepoState
import kotlinx.coroutines.flow.Flow

interface CartRepo {

    fun getCarts(): Flow<List<CartDomain>>

    suspend fun addCart(cart: Cart): Long

    suspend fun removeCart(cart: Cart)

    suspend fun removeAllCarts()

    suspend fun editCart(cart: Cart)

    fun getCartRepoState(): Flow<CartRepoState>

    fun allCartsPagingSource(): PagingSource<Int, Cart>
}