package com.example.notepadapp

import android.content.Context

class CategoryDAO {
    companion object{
        // Create Data
        fun insertData(context: Context, categoryData: CategoryData){
            // autoincrement가 있는 컬럼은 제외하고 나머지를 지정
            val query = "insert into CategoryTable (categoryTitle) values(?)"
            // ?에 들어갈 배열
            val arg1 = arrayOf(categoryData.categoryTitle)
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            dbHelper.writableDatabase.execSQL(query, arg1)
            // Db 닫기
            dbHelper.close()
        }

        // Read Condition
        fun selectData(context: Context, index: Int) : CategoryData{
            // 쿼리문
            val query = "select * from CategoryTable where idx = ?"
            // ?에 들어갈 배열
            val arg1 = arrayOf("$index")
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            val cursor = dbHelper.writableDatabase.rawQuery(query, arg1)
            cursor.moveToNext()

            // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("categoryTitle")

            // 데이터를 가져온다.
            val idx = cursor.getInt(idx1)
            val categoryTitle = cursor.getString(idx2)

            val categoryData = CategoryData(idx, categoryTitle)

            // Db 닫기
            dbHelper.close()

            return categoryData
        }

        // Read All
        fun selectAllData(context: Context) : ArrayList<CategoryData>{
            // 쿼리문
            val query = "select * from CategoryTable"
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            val cursor = dbHelper.writableDatabase.rawQuery(query,null)

            val categoryList = ArrayList<CategoryData>()
            while(cursor.moveToNext()){
                // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("categoryTitle")

                // 데이터를 가져온다.
                val idx = cursor.getInt(idx1)
                val categoryTitle = cursor.getString(idx2)

                val categoryData = CategoryData(idx, categoryTitle)
                categoryList.add(categoryData)
            }
            categoryList.reverse()

            // Db 닫기
            dbHelper.close()

            return categoryList
        }

        // Update
        fun updateData(context: Context, categoryData: CategoryData){
            // 쿼리문
            val query = "update CategoryTable set categoryTitle = ? where idx = ?"
            // ?에 들어갈 배열
            val arg1 = arrayOf(categoryData.categoryTitle, categoryData.idx)
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
            val query = "delete from CategoryTable where idx = ?"
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

data class CategoryData(var idx: Int, var categoryTitle: String)