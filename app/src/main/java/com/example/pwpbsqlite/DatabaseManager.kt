package com.example.pwpbsqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase


class DatabaseManager(private val context: Context) {
    private var dbHelper: DatabaseHelper? = null
    private var database: SQLiteDatabase? = null

    @Throws(SQLException::class)
    fun open(): DatabaseManager {
        dbHelper = DatabaseHelper(context)
        database = dbHelper!!.writableDatabase
        return this
    }

    fun close() {
        dbHelper!!.close()
    }

    fun insert(title: String?, description: String?) {
        val contentValues = ContentValues()
        contentValues.put(DatabaseHelper.TITLE, title)
        contentValues.put(DatabaseHelper.DESC, description)
        database!!.insert(DatabaseHelper.TABLE_NAME, null, contentValues)
    }

    fun update(id: Int, title: String?, description: String?) {
        val contentValues = ContentValues()
        contentValues.put(DatabaseHelper.TITLE, title)
        contentValues.put(DatabaseHelper.DESC, description)
        database!!.update(
            DatabaseHelper.TABLE_NAME,
            contentValues,
            DatabaseHelper._ID + " = " + id,
            null
        )
    }

    fun delete(id: Int) {
        database!!.delete(
            DatabaseHelper.TABLE_NAME,
            DatabaseHelper._ID + " ='" + id + "'",
            null
        )
    }

    fun fetch(): Cursor? {
        val columns = arrayOf(
            DatabaseHelper._ID,
            DatabaseHelper.TITLE,
            DatabaseHelper.DESC
        )
        val cursor = database!!.query(
            DatabaseHelper.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        return cursor
    }

}