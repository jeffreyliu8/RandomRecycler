package com.askjeffreyliu.testRecycler.room


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.askjeffreyliu.testRecycler.model.RoomArticle

@Dao
interface ArticleDao {
    @Query("SELECT * FROM RoomArticle")
    fun getAll(): LiveData<List<RoomArticle>>

    @Query("SELECT * FROM RoomArticle WHERE date == :date")
    fun getByDate(date: String): List<RoomArticle>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(latLngList: List<RoomArticle>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: RoomArticle)

    @Query("DELETE FROM RoomArticle")
    fun deleteAll()
}