package com.example.pwpbsqlite

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ModifyActivity : AppCompatActivity() {
    private var modTitle: EditText? = null
    private var modDesc: EditText? = null
    private var id: Long = 0
    private var isItemDeleted = false

    private var dbManager: DatabaseManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
        val toolbar: Toolbar = findViewById<View>(R.id.modify_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        dbManager = DatabaseManager(this)
        dbManager!!.open()
        modTitle = findViewById<View>(R.id.title_modify_input) as EditText
        modDesc = findViewById<View>(R.id.desc_modify_input) as EditText
        val intentData = intent.extras
        val myID = intentData!!.getString("Id")
        val myTitle = intentData.getString("Title")
        val myDescription = intentData.getString("Desc")
        modTitle!!.setText(myTitle)
        modDesc!!.setText(myDescription)
        id = myID!!.toLong()
        val fabDelete =
            findViewById<View>(R.id.fabDelete) as FloatingActionButton
        val fabUpdate =
            findViewById<View>(R.id.fabUpdate) as FloatingActionButton
        fabDelete.setOnClickListener {
            dbManager!!.delete(myID.toInt())
            setItemDeleted(true)
            returnHome()
        }
        fabUpdate.setOnClickListener {
            val newTitle = modTitle!!.text.toString()
            val newDesc = modDesc!!.text.toString()
            dbManager!!.update(myID.toInt(), newTitle, newDesc)
            returnHome()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.getItemId()
        if(id ==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item)
    }

    private fun returnHome() {
        val intent = Intent(
            applicationContext,
            MainActivity::class.java
        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        if (isItemDeleted) {
            intent.putExtra("ItemDeleted", true)
        }
        startActivity(intent)
    }

    fun setItemDeleted(itemDeleted: Boolean) {
        isItemDeleted = itemDeleted
    }
}