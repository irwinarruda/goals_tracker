package com.irwinarruda.goalstracker.repositories

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.irwinarruda.goalstracker.entities.Goal
import java.util.*

class GoalsRepository private constructor(context: Context) {
    private val database = GoalsDataBase(context)

    companion object {
        private lateinit var instance: GoalsRepository
        fun get(context: Context): GoalsRepository {
            if (!::instance.isInitialized) {
                instance = GoalsRepository(context)
            }
            return instance
        }
    }

    @SuppressLint("Range")
    fun getAll() {
        val values = database.readableDatabase.query(
            GoalsDataBase.GOALS.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        while (values.moveToNext()) {
            Goal(
                values.getString(values.getColumnIndex(GoalsDataBase.GOALS.DESCRIPTION)),
                values.getInt(values.getColumnIndex(GoalsDataBase.GOALS.DAY_COUNT)),
                Date(),
                values.getInt(values.getColumnIndex(GoalsDataBase.GOALS.COINS))
            )
            // push the new goal to the array of
        }
    }

    fun create(goal: Goal) {
        try {
            val values = ContentValues()
            values.put(GoalsDataBase.GOALS.DESCRIPTION, goal.description)
            values.put(GoalsDataBase.GOALS.DAY_COUNT, goal.dayCount)
            values.put(GoalsDataBase.GOALS.START_DATE, goal.startDate.time)
            values.put(GoalsDataBase.GOALS.COINS, goal.coins)
            database.writableDatabase.insert(GoalsDataBase.GOALS.TABLE_NAME, null, values)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}