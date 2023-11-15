package com.irwinarruda.goalstracker.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.irwinarruda.goalstracker.entities.Day
import com.irwinarruda.goalstracker.entities.DayState
import com.irwinarruda.goalstracker.entities.Goal
import com.irwinarruda.goalstracker.utils.toEpochMilli
import com.irwinarruda.goalstracker.utils.toLocalDate
import java.time.LocalDate

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

    private fun cursorToGoal(cursor: Cursor, includeDays: Boolean): Goal {
        val id = cursor.getInt(cursor.getColumnIndex(GoalsDataBase.GOALS.ID))
        val description = cursor.getString(cursor.getColumnIndex(GoalsDataBase.GOALS.DESCRIPTION))
        val dayCount = cursor.getInt(cursor.getColumnIndex(GoalsDataBase.GOALS.DAY_COUNT))
        val startDate = cursor.getLong(cursor.getColumnIndex(GoalsDataBase.GOALS.START_DATE)).toLocalDate()
        val coins = cursor.getInt(cursor.getColumnIndex(GoalsDataBase.GOALS.COINS))
        if (!includeDays) {
            return Goal(id, description, dayCount, startDate, coins)
        }
        return Goal(id, description, dayCount, startDate, coins, getDaysByGoalId(id))
    }

    private fun cursorToDay(cursor: Cursor): Day {
        return Day(
            cursor.getInt(cursor.getColumnIndex(GoalsDataBase.DAYS.ID)),
            cursor.getInt(cursor.getColumnIndex(GoalsDataBase.DAYS.GOAL_ID)),
            cursor.getInt(cursor.getColumnIndex(GoalsDataBase.DAYS.COUNT)),
            cursor.getLong(cursor.getColumnIndex(GoalsDataBase.DAYS.DATE)).toLocalDate(),
            DayState.entries[cursor.getInt(cursor.getColumnIndex(GoalsDataBase.DAYS.STATE))]
        )
    }

    private fun goalToContentValues(goal: Goal): ContentValues {
        return ContentValues().apply {
            put(GoalsDataBase.GOALS.DESCRIPTION, goal.description)
            put(GoalsDataBase.GOALS.DAY_COUNT, goal.dayCount)
            put(GoalsDataBase.GOALS.START_DATE, goal.startDate.toEpochMilli())
            put(GoalsDataBase.GOALS.COINS, goal.coins)
        }
    }

    private fun dayToContentValues(day: Day): ContentValues {
        return ContentValues().apply {
            put(GoalsDataBase.DAYS.GOAL_ID, day.goalId)
            put(GoalsDataBase.DAYS.COUNT, day.count)
            put(GoalsDataBase.DAYS.DATE, day.date.toEpochMilli())
            put(GoalsDataBase.DAYS.STATE, day.state.ordinal)
        }
    }

    fun updatePendingDay(id: Int) {
        val db = database.writableDatabase
        val contentValues = ContentValues().apply {
            put(GoalsDataBase.DAYS.STATE, DayState.SUCCESS.ordinal)
        }
        db.update(
            GoalsDataBase.DAYS.TABLE_NAME,
            contentValues,
            "${GoalsDataBase.DAYS.ID} = ?",
            arrayOf(id.toString())
        )
        db.close()
    }

    fun updateDaysByGoalId(id: Int) {
        val db = database.writableDatabase
        val currentDate = LocalDate.now()
        db.beginTransaction()
        val days = getDaysByGoalId(id)
        for (day in days) {
            if (day.state == DayState.DISABLED || day.state == DayState.PENDING) {
                if (day.date.isEqual(currentDate)) {
                    day.state = DayState.PENDING
                }
                if (day.date.isAfter(currentDate)) {
                    day.state = DayState.DISABLED
                }
                if (day.date.isBefore(currentDate)) {
                    day.state = DayState.ERROR
                }
                val contentValues = dayToContentValues(day)
                db.update(
                    GoalsDataBase.DAYS.TABLE_NAME,
                    contentValues,
                    "${GoalsDataBase.DAYS.ID} = ?",
                    arrayOf(day.id.toString())
                )
                continue
            }
        }
        db.setTransactionSuccessful()
        db.endTransaction()
        db.close()
    }

    fun getDaysByGoalId(id: Int): List<Day> {
        val days = database.readableDatabase.query(
            GoalsDataBase.DAYS.TABLE_NAME,
            null,
            "${GoalsDataBase.DAYS.GOAL_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        )
        val daysList = mutableListOf<Day>()
        while (days.moveToNext()) {
            daysList.add(cursorToDay(days))
        }
        return daysList
    }

    fun getAll(includeDays: Boolean): MutableList<Goal> {
        try {
            val goals = database.readableDatabase.query(
                GoalsDataBase.GOALS.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )
            val goalsList = mutableListOf<Goal>()
            while (goals.moveToNext()) {
                goalsList.add(cursorToGoal(goals, includeDays))
            }
            goals.close()
            return goalsList
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    fun create(goal: Goal) {
        try {
            val writable = database.writableDatabase
            writable.beginTransaction()
            val goalValues = goalToContentValues(goal)
            val id = writable.insert(GoalsDataBase.GOALS.TABLE_NAME, null, goalValues)
            for (i in 0 until goal.dayCount) {
                val dayValues = dayToContentValues(
                    Day(0, id.toInt(), i, goal.startDate.plusDays(i.toLong()), DayState.DISABLED)
                )
                writable.insert(GoalsDataBase.DAYS.TABLE_NAME, null, dayValues)
            }
            writable.setTransactionSuccessful()
            writable.endTransaction()
            writable.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteById(id: Int) {
        val db = database.writableDatabase
        db.delete(
            GoalsDataBase.GOALS.TABLE_NAME,
            "${GoalsDataBase.GOALS.ID} = ?",
            arrayOf(id.toString())
        )
        db.delete(
            GoalsDataBase.DAYS.TABLE_NAME,
            "${GoalsDataBase.DAYS.GOAL_ID} = ?",
            arrayOf(id.toString())
        )
        db.close()
    }
}