package com.example.notepadapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "note_pad_database.db", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val sql = """create table UserTable(
            |idx integer primary key autoincrement,
            |userPassword integer not null)
        """.trimMargin()

        p0?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}