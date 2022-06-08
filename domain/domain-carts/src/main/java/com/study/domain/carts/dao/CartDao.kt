package com.study.domain.carts.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.study.domain.carts.models.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_table ORDER BY title ASC")
    fun allCarts(): Flow<List<Cart>>

    @Insert(onConflict = IGNORE)
    suspend fun addToCarts(cart: Cart): Long

    @Delete
    suspend fun deleteCarts(vararg carts: Cart): Int

    @Update
    suspend fun editCarts(vararg carts: Cart): Int
}