package com.example.pwpbsqlite

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cursoradapter.widget.SimpleCursorAdapter
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private var dbManager: DatabaseManager? = null
    private var listView: ListView? = null
    private var adapter: SimpleCursorAdapter? = null
    private val dbHelper: DatabaseHelper? = null

    val from = arrayOf(
        DatabaseHelper._ID,
        DatabaseHelper.TITLE,
        DatabaseHelper.DESC
    )
    val to = intArrayOf(R.id.id_text, R.id.title_text, R.id.desc_text)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById<View>(R.id.main_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        dbManager = DatabaseManager(this)
        dbManager!!.open()
        val cursor: Cursor? = dbManager!!.fetch()
        listView = findViewById<View>(R.id.main_listview) as ListView
        adapter = SimpleCursorAdapter(this, R.layout.adapter, cursor, from, to, 0)
        listView!!.adapter = adapter

        try {
            val intent = intent
            val ItemDeleted = intent.extras!!.getBoolean("ItemDeleted")
            val modifyActivity = ModifyActivity()
            if (ItemDeleted) {
                Snackbar.make(listView!!, "ItemDeleted!", Snackbar.LENGTH_LONG).show()
                modifyActivity.setItemDeleted(false)
            }
        } catch (e: Exception) {
            if (adapter!!.isEmpty()) {
                Snackbar.make(listView!!, "Click on fab to add list", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(listView!!, "Hold on item to modify", Snackbar.LENGTH_LONG).show()
            }
        }
        listView!!.setOnItemLongClickListener { parent, view, position, id ->
            val itemID = view.findViewById(R.id.id_text) as TextView
            val itemTitle = view.findViewById(R.id.title_text) as TextView
            val itemDesc = view.findViewById(R.id.desc_text) as TextView
            val myId = itemID.text.toString()
            val myTitle = itemTitle.text.toString()
            val myDesc = itemDesc.text.toString()
            val intent = Intent(applicationContext, ModifyActivity::class.java)
            intent.putExtra("Id", myId)
            intent.putExtra("Title", myTitle)
            intent.putExtra("Desc", myDesc)
            startActivity(intent)
            false
        }
    }

    fun onClickAddItem(view: View?) {
        val intent = Intent(applicationContext, AddItemActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.getItemId()
        if(id ==android.R.id.home){
            onBackPressed();
        }
        return if (id == R.id.action_clear_all) {
            true
        } else super.onOptionsItemSelected(item)
    }
}