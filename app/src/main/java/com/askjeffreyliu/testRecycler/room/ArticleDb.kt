package com.askjeffreyliu.testRecycler.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.askjeffreyliu.testRecycler.model.Article

@Database(entities = arrayOf(Article::class), version = 1, exportSchema = false)
abstract class ArticleDb : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: ArticleDb? = null

        fun getDatabase(
            context: Context
        ): ArticleDb {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDb::class.java,
                    "article_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}