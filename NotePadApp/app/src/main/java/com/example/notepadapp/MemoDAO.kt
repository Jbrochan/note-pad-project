package com.example.notepadapp

import android.content.Context

class MemoDAO {
    companion object {
        // Create Table
        fun createTable(context: Context, categoryName: String){
            // autoincrement가 있는 컬럼은 제외하고 나머지를 지정
            val query  = """create table $categoryName(
            |idx integer primary key autoincrement,
            |memoTitle text not null,
            |memoDate text not null,
            |memoContent text not null
            |)""".trimMargin()

            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            dbHelper.writableDatabase.execSQL(query)
            // Db 닫기
            dbHelper.close()
        }


        // Create Data
        fun insertData(context: Context, categoryName: String, data: MemoData){
            // autoincrement가 있는 컬럼은 제외하고 나머지를 지정
            val query = "insert into $categoryName (memoTitle, memoDate, memoContent) values(?, ?, ?)"
            // ?에 들어갈 배열
            val arg1 = arrayOf(data.memoTitle, data.memoDate, data.memoContent)
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            dbHelper.writableDatabase.execSQL(query, arg1)
            // Db 닫기
            dbHelper.close()
        }

        // Read Condition
        fun selectData(context: Context, categoryName: String, index: Int) : MemoData{
            // 쿼리문
            val query = "select * from $categoryName where idx = ?"
            // ?에 들어갈 배열
            val arg1 = arrayOf("$index")
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            val cursor = dbHelper.writableDatabase.rawQuery(query, arg1)
            cursor.moveToNext()

            // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("memoTitle")
            val idx3 = cursor.getColumnIndex("memoDate")
            val idx4 = cursor.getColumnIndex("memoContent")

            // 데이터를 가져온다.
            val idx = cursor.getInt(idx1)
            val memoTitle = cursor.getString(idx2)
            val memoDate = cursor.getString(idx3)
            val memoContent = cursor.getString(idx4)

            val memoData = MemoData(idx, memoTitle, memoDate, memoContent)

            // Db 닫기
            dbHelper.close()

            return memoData
        }

        // Read All
        fun selectAllData(context: Context, categoryName: String) : ArrayList<MemoData>{
            // 쿼리문
            val query = "select * from $categoryName"
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            val cursor = dbHelper.writableDatabase.rawQuery(query,null)

            val memoList = ArrayList<MemoData>()
            while(cursor.moveToNext()){
                // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("memoTitle")
                val idx3 = cursor.getColumnIndex("memoDate")
                val idx4 = cursor.getColumnIndex("memoContent")

                // 데이터를 가져온다.
                val idx = cursor.getInt(idx1)
                val memoTitle = cursor.getString(idx2)
                val memoDate = cursor.getString(idx3)
                val memoContent = cursor.getString(idx4)

                val memoData = MemoData(idx, memoTitle, memoDate, memoContent)
                memoList.add(memoData)
            }

            // Db 닫기
            dbHelper.close()

            return memoList
        }

        // Update
        fun updateData(context: Context, memoData: MemoData){
            // 쿼리문
            val query = "update MemoTable set memoTitle = ?, memoDate = ?, memoContent = ? where idx = ?"
            // ?에 들어갈 배열
            val arg1 = arrayOf(memoData.memoTitle, memoData.memoDate, memoData.memoContent, memoData.idx)
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            dbHelper.writableDatabase.execSQL(query, arg1)

            // Db 닫기
            dbHelper.close()
        }

        // Delete
        fun deleteData(context: Context, index: Int){
            // 쿼리문
            val query = "delete from MemoTable where idx = ?"
            // ?에 들어갈 배열
            val arg1 = arrayOf(index)
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            dbHelper.writableDatabase.execSQL(query, arg1)

            // Db 닫기
            dbHelper.close()
        }

    }
}

data class MemoData(var idx: Int,
                    var memoTitle: String,
                    var memoDate: String,
                    var memoContent: String)