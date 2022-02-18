package com.example.roomr8

import android.content.Context
import android.util.Log
import androidx.room.*
import kotlin.concurrent.thread

class TestInitializer(private val context: Context) {

    fun initialize() {
        thread(start = true) {
            Log.i("room_r8", "before DB creation")
            try {
                val db = TestDatabase.newInstance(context, "test_database")
                db.dao.save(listOf(TestEntity(1, "1")))
                Log.i("room_r8", "DB test successfully finished")
            } catch (e: Throwable) {
                Log.e("room_r8", "Failed to create db", e)
            }
        }
    }


    @Database(
        entities = [TestEntity::class],
        // views = [TestEntityView::class], // Uncomment this line
        version = 1,
        exportSchema = true,
    )
    abstract class TestDatabase : RoomDatabase() {
        abstract val dao: TestDao

        companion object {
            fun newInstance(context: Context, name: String): TestDatabase {
                return Room.databaseBuilder(context, TestDatabase::class.java, name)
                    .setJournalMode(JournalMode.WRITE_AHEAD_LOGGING)
                    .build()
            }
        }
    }

    @Dao
    interface TestDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun save(entities: List<TestEntity>)

        @Query("select * from test_entity order by id")
        fun loadEntities(): List<TestEntity>
    }

    @DatabaseView("select id from test_entity")
    data class TestEntityView(
        val id: Int,
    )

    @Entity(tableName = "test_entity")
    data class TestEntity(
        @PrimaryKey val id: Int,
        val value: String,
    )
}