package com.example.notetoself

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Switch
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notetoself.databinding.ActivityMainBinding
import com.example.notetoself.databinding.HeaderBinding

@SuppressLint("StaticFieldLeak")
class MainActivity : AppCompatActivity() , OnDeleteListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db : NoteDatabase
    private var noteList : List<NoteEntity> = listOf<NoteEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = NoteDatabase.getInstance(this)!!

        binding.btnAdd.setOnClickListener {
            val note = NoteEntity(null,binding.etNote.text.toString())
            insertNote(note)
            binding.etNote.setText("")
            closeKeyboard()
        }

        binding.noteList.layoutManager = LinearLayoutManager(this)

        getAll()
    }

    fun insertNote(note : NoteEntity){
        val insertTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.NoteDAO().insert(note)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAll()
            }

        }

        insertTask.execute()

    }

    fun getAll(){
        val getTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                noteList = db.NoteDAO().getAll()
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                setRecyclerView(noteList)
            }

        }

        getTask.execute()

    }

    fun deleteNote(note: NoteEntity){
        val deleteTask = object : AsyncTask<Unit, Unit, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                db.NoteDAO().delete(note)
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                getAll()
            }

        }

        deleteTask.execute()

    }

    fun setRecyclerView(noteList: List<NoteEntity>){
        binding.noteList.adapter = NoteAdapter(this,noteList,this)
    }

    override fun onDeleteListener(note: NoteEntity) {
        deleteNote(note)
    }

    private fun closeKeyboard() {
        val view = this.currentFocus

        if(view != null)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}