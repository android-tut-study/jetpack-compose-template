package com.study.domain.carts.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
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

    @Query("SELECT * FROM cart_table ORDER BY created_at DESC")
    fun lazyAllCarts(): PagingSource<Int, Cart>

}
