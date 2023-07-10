package com.example.notepadapp

import android.content.Context

class UserDAO {
    companion object {
        // Create
        fun insertData(context: Context, data: UserData){
            // autoincrement가 있는 컬럼은 제외하고 나머지를 지정
            val query = "insert into UserTable (userPassword) values(?)"
            // ?에 들어갈 배열
            val arg1 = arrayOf(data.userPassword)
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            dbHelper.writableDatabase.execSQL(query, arg1)
            // Db 닫기
            dbHelper.close()
        }

        // Read Condition
        fun selectData(context: Context, index: Int) : UserData{
            // 쿼리문
            val query = "select * from UserTable where idx = ?"
            // ?에 들어갈 배열
            val arg1 = arrayOf("$index")
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            val cursor = dbHelper.writableDatabase.rawQuery(query, arg1)
            cursor.moveToNext()

            // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
            val idx1 = cursor.getColumnIndex("idx")
            val idx2 = cursor.getColumnIndex("userPassword")

            // 데이터를 가져온다.
            val idx = cursor.getInt(idx1)
            val userPassword = cursor.getInt(idx2)

            val userData = UserData(idx, userPassword)

            // Db 닫기
            dbHelper.close()

            return userData
        }

        // Read All
        fun selectAllData(context: Context) : ArrayList<UserData>{
            // 쿼리문
            val query = "select * from UserTable"
            // Db 오픈
            val dbHelper = DBHelper(context)

            // Query 실행
            val cursor = dbHelper.writableDatabase.rawQuery(query,null)

            val userList = ArrayList<UserData>()
            while(cursor.moveToNext()){
                // 컬럼의 이름을 지정하여 컬럼의 순서값을 가져온다.
                val idx1 = cursor.getColumnIndex("idx")
                val idx2 = cursor.getColumnIndex("userPassword")

                // 데이터를 가져온다.
                val idx = cursor.getInt(idx1)
                val userPassword = cursor.getInt(idx2)

                val userData = UserData(idx, userPassword)
                userList.add(userData)
            }

            // Db 닫기
            dbHelper.close()

            return userList
        }

        // Update
        fun updateData(context: Context, userData: UserData){
            // 쿼리문
            val query = "update UserTable set UserPassword = ? where idx = ?"
            // ?에 들어갈 배열
            val arg1 = arrayOf(userData.userPassword, userData.idx)
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
            val query = "delete from UserTable where idx = ?"
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

data class UserData(var idx: Int, var userPassword: Int)