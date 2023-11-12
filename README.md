Goals Tracker

🚧 Documentation in progress...

Imagine you are a senior kotlin developer.
Also, imagine you contribute to the android core
I'm developing an app that uses SQLiteOpenHelper. This is my base class

```kotlin
class GoalsDataBase(context: Context) :
    SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object {
        private const val NAME = "goalsdb"
        private const val VERSION = 1
    }

    object GOALS {
        const val TABLE_NAME = "goals"
        const val ID = "id"
        const val DESCRIPTION = "description"
        const val DAY_COUNT = "day_count"
        const val START_DATE = "start_date"
        const val COINS = "coins"
    }

    object DAYS {
        const val TABLE_NAME = "days"
        const val ID = "id"
        const val COUNT = "count"
        const val DATE = "date"
        const val STATE = "state"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE ${GOALS.TABLE_NAME} (
                ${GOALS.ID} integer primary key autoincrement,
                ${GOALS.DESCRIPTION} text not null,
                ${GOALS.DAY_COUNT} integer not null,
                ${GOALS.START_DATE} date not null,
                ${GOALS.COINS} integer
            ); 
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE ${DAYS.TABLE_NAME} (
                ${DAYS.ID} integer primary key autoincrement,
                ${DAYS.COUNT} integer not null,
                ${DAYS.DATE} date not null,
                ${DAYS.STATE} integer not null
            ); 
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${GOALS.TABLE_NAME};")
        db.execSQL("DROP TABLE IF EXISTS ${DAYS.TABLE_NAME};")
        onCreate(db)
    }
}
```

```kotlin
package com.irwinarruda.goalstracker.entities

import java.time.LocalDate

class Goal(
    val description: String,
    val dayCount: Int,
    val startDate: LocalDate,
    val coins: Int?,
) {
    var id: Int = 0
    var days: MutableList<Day> = mutableListOf()

    constructor(
        id: Int,
        description: String,
        dayCount: Int,
        startDate: LocalDate,
        coins: Int?,
    ) : this(description, dayCount, startDate, coins) {
        this.id = id
    }
}
```

What's the best way for me to connect the Day to the Goal in the database?