package com.study.domain.carts.models

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "cart_table")
data class Cart(
    @ColumnInfo(name = "productId") val productId: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: Float,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "color") val color: Int = Color.CYAN,
    @ColumnInfo(name = "size") val size: Int = 1,
    @ColumnInfo(name = "created_at") val createdAt: Date = Date(),
    @ColumnInfo(name = "updated_at") val updatedAt: Date = createdAt
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
}