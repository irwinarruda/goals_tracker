package com.irwinarruda.goalstracker.repositories

import android.content.ContentValues
import android.content.Context
import com.irwinarruda.goalstracker.entities.Goal
import com.irwinarruda.goalstracker.utils.toEpochMilli
import com.irwinarruda.goalstracker.utils.toLocalDate

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

    fun getAll(): MutableList<Goal> {
        val values = database.readableDatabase.query(
            GoalsDataBase.GOALS.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val goalsList = mutableListOf<Goal>()
        while (values.moveToNext()) {
            val id = values.getInt(values.getColumnIndex(GoalsDataBase.GOALS.ID))
            val description = values.getString(values.getColumnIndex(GoalsDataBase.GOALS.DESCRIPTION))
            val dayCount = values.getInt(values.getColumnIndex(GoalsDataBase.GOALS.DAY_COUNT))
            val startDateMillis = values.getLong(values.getColumnIndex(GoalsDataBase.GOALS.START_DATE))
            val coins = values.getInt(values.getColumnIndex(GoalsDataBase.GOALS.COINS))
            val startDate = startDateMillis.toLocalDate()
            val goal = Goal(id, description, dayCount, startDate, coins)
            goalsList.add(goal)
        }
        values.close()
        return goalsList
    }

    fun create(goal: Goal) {
        try {
            val values = ContentValues()
            values.put(GoalsDataBase.GOALS.DESCRIPTION, goal.description)
            values.put(GoalsDataBase.GOALS.DAY_COUNT, goal.dayCount)
            values.put(GoalsDataBase.GOALS.START_DATE, goal.startDate.toEpochMilli())
            values.put(GoalsDataBase.GOALS.COINS, goal.coins)
            database.writableDatabase.insert(GoalsDataBase.GOALS.TABLE_NAME, null, values)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteById(id: Int) {
        database.writableDatabase.delete(
            GoalsDataBase.GOALS.TABLE_NAME,
            "${GoalsDataBase.GOALS.ID} = ?",
            arrayOf(id.toString())
        )
    }
}