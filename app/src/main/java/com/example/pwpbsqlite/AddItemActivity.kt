package com.example.pwpbsqlite

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar


class AddItemActivity : AppCompatActivity() {
    private var inputTitle: EditText? = null
    private var inputDesc: EditText? = null
    private var dbManager: DatabaseManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        val toolbar: Toolbar = findViewById<View>(R.id.add_item_toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        inputTitle = findViewById<View>(R.id.title_input) as EditText
        inputDesc = findViewById<View>(R.id.desc_input) as EditText
        dbManager = DatabaseManager(this)
        dbManager!!.open()
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

    fun onClickDone(view: View?) {
        val myInputTitle = inputTitle!!.text.toString()
        val myInputDesc = inputDesc!!.text.toString()
        if (myInputTitle.isEmpty() || myInputDesc.isEmpty()) {
            Snackbar.make(view!!, "Please fill in both form!", Snackbar.LENGTH_SHORT).show()
        } else {
            dbManager!!.insert(myInputTitle, myInputDesc)
            val intent = Intent(
                this,
                MainActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}