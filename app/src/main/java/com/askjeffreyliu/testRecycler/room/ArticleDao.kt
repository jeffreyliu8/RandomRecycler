package com.askjeffreyliu.testRecycler.room


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.askjeffreyliu.testRecycler.model.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM Article ORDER BY date DESC,publishedAt DESC")
    fun getAll(): LiveData<List<Article>>

//    @Query("SELECT * FROM Article WHERE date == :date")
//    fun getByDate(date: String): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Article>)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(article: Article)

//    @Query("DELETE FROM Article")
//    fun deleteAll()
}