package com.sagar.android.roomdatabase

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    private val tag: String = "log_Tag"

    private lateinit var roomDatabase: WordRoomDatabase
    private lateinit var dao: WordDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roomDatabase = Room.databaseBuilder(
            applicationContext,
            WordRoomDatabase::class.java,
            "word_database.db"
        ).build()

        dao = roomDatabase.wordDao()

        Thread(
            Runnable {
                dao.insert(Word("The Word"))
            }
        ).start()

        Handler().postDelayed(
            {
                dao.getAllWord().observe(
                    this,
                    Observer<List<Word>> { t ->
                        t?.let {
                            Log.v(tag, "here")
                            for (word in t) {
                                Log.v(tag, word.mWord)
                            }
                        }
                    }
                )
            },
            3000
        )
    }
}
