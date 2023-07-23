package com.example.notepadapp

import android.content.Context

class MemoDAO {
    companion object {
        // Create Data
        fun insertData(context: Context, data: MemoData){
            // autoincrement가 있는 컬럼은 제외하고 나머지를 지정
            val query = "insert into MemoTable (categoryIndex, memoTitle, memoDate, memoContent) values(?, ?, ?, ?)"
            // ?에 들어갈 배열
            val arg1 = arrayOf(data.categoryIndex, data.memoTitle, data.memoDate, data.memoContent)
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            dbHelper.writableDatabase.execSQL(query, arg1)
            // Db 닫기
            dbHelper.close()
        }

        // Read Condition
        fun selectData(context: Context, index: Int) : MemoData{
            // 쿼리문
            val query = "select * from MemoTable where idx = ?"
            // ?에 들어갈 배열
            val arg1 = arrayOf("$index")
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            val cursor = dbHelper.writableDatabase.rawQuery(query, arg1)
            cursor.moveToNext()

            // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("categoryIndex")
            val idx3 = cursor.getColumnIndex("memoTitle")
            val idx4 = cursor.getColumnIndex("memoDate")
            val idx5 = cursor.getColumnIndex("memoContent")

            // 데이터를 가져온다.
            val idx = cursor.getInt(idx1)
            val categoryIndex = cursor.getInt(idx2)
            val memoTitle = cursor.getString(idx3)
            val memoDate = cursor.getString(idx4)
            val memoContent = cursor.getString(idx5)

            val memoData = MemoData(idx, categoryIndex, memoTitle, memoDate, memoContent)

            // Db 닫기
            dbHelper.close()

            return memoData
        }

        // Read Category Condition
        fun selectCategoryData(context: Context, categoryIndex: Int) : ArrayList<MemoData>{
            // 쿼리문
            val query = "select * from MemoTable where categoryIndex = $categoryIndex"
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            val cursor = dbHelper.writableDatabase.rawQuery(query, null)
            val memoList = ArrayList<MemoData>()
            while(cursor.moveToNext()){
                // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("categoryIndex")
                val idx3 = cursor.getColumnIndex("memoTitle")
                val idx4 = cursor.getColumnIndex("memoDate")
                val idx5 = cursor.getColumnIndex("memoContent")

                // 데이터를 가져온다.
                val idx = cursor.getInt(idx1)
                val categoryIndex = cursor.getInt(idx2)
                val memoTitle = cursor.getString(idx3)
                val memoDate = cursor.getString(idx4)
                val memoContent = cursor.getString(idx5)

                val memoData = MemoData(idx, categoryIndex, memoTitle, memoDate, memoContent)
                memoList.add(memoData)
            }

            // Db 닫기
            dbHelper.close()

            return memoList
        }

        // Read All
        fun selectAllData(context: Context) : ArrayList<MemoData>{
            // 쿼리문
            val query = "select * from MemoTable"
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            val cursor = dbHelper.writableDatabase.rawQuery(query,null)

            val memoList = ArrayList<MemoData>()
            while(cursor.moveToNext()){
                // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("categoryIndex")
                val idx3 = cursor.getColumnIndex("memoTitle")
                val idx4 = cursor.getColumnIndex("memoDate")
                val idx5 = cursor.getColumnIndex("memoContent")

                // 데이터를 가져온다.
                val idx = cursor.getInt(idx1)
                val categoryIndex = cursor.getInt(idx2)
                val memoTitle = cursor.getString(idx3)
                val memoDate = cursor.getString(idx4)
                val memoContent = cursor.getString(idx5)

                val memoData = MemoData(idx, categoryIndex, memoTitle, memoDate, memoContent)
                memoList.add(memoData)
            }

            // Db 닫기
            dbHelper.close()

            return memoList
        }

        // Update
        fun updateData(context: Context, memoData: MemoData){
            // 쿼리문
            val query = "update MemoTable set categoryIndex = ?, memoTitle = ?, memoDate = ?, memoContent = ? where idx = ?"
            // ?에 들어갈 배열
            val arg1 = arrayOf(memoData.categoryIndex, memoData.memoTitle, memoData.memoDate, memoData.memoContent, memoData.idx)
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

        // Delete category data
        fun deleteCategoryData(context: Context, categoryIndex: Int){
            // 쿼리문
            val query = "delete from MemoTable where categoryIndex = ?"
            // ? 에 들어갈 배열
            val arg1 = arrayOf(categoryIndex)
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
                    var categoryIndex: Int,
                    var memoTitle: String,
                    var memoDate: String,
                    var memoContent: String)