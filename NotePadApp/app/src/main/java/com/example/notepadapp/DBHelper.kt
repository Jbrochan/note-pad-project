package com.example.notepadapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "note_pad_database.db", null, 1) {
    override fun onCreate(sqliteDatabase: SQLiteDatabase?) {
        val queryCategory = """create table if not exists CategoryTable(
            |idx integer primary key autoincrement,
            |categoryTitle text not null)
        """.trimMargin()

        sqliteDatabase?.execSQL(queryCategory)

        val queryMemo = """create table if not exists MemoTable(
            |idx integer primary key autoincrement,
            |categoryIndex integer not null,
            |memoTitle text not null,
            |memoDate text not null,
            |memoContent text not null)
        """.trimMargin()

        sqliteDatabase?.execSQL(queryMemo)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}