package com.example.aj.emergencynotifier

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.design.widget.TabLayout
import android.widget.Toast
import org.jetbrains.anko.db.createTable

/**
 * Created by AJ on 16/03/2018.
 */
val DATABASE_NAME = "MyDB"
val TABLE_NAME = "User"
val COL_ID = "id"
val COL_NUMBER = "number"

class DataBaseHandler(context: Context) : SQLiteOpenHelper(context , DATABASE_NAME,null,1){
    val context = context
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =  "CREATE TABLE " + TABLE_NAME +" (" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_NUMBER + " VARCHAR(100))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun insertData(user: User) : Boolean{
        var success : Boolean
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NUMBER,user.number)
        var result = db.insert(TABLE_NAME,null,cv)
        if(result == (-1).toLong())
        {
            success = false
        }
        else
        {
            success = true

        }
        return success
    }
    fun readData() : MutableList<User>{
        var list : MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * from " + TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do{
                var user  = User()
                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.number = result.getString(result.getColumnIndex(COL_NUMBER))
                list.add(user)

            }while (result.moveToNext())
        }
        return list
    }


}