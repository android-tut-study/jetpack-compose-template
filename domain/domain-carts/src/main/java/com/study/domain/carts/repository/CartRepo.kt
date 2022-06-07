package com.example.domain.carts.repository

import com.example.domain.carts.models.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepo {

    fun getCarts(): Flow<List<Cart>>

    suspend fun addCart(cart: Cart)

    suspend fun removeCart(cart: Cart)

    suspend fun removeAllCarts()

    suspend fun editCart(cart: Cart)
}