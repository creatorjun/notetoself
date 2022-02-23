package com.example.notetoself

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter (val context: Context, var list : List<NoteEntity>, var onDeleteListener: OnDeleteListener) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        val note = list[position]
        holder.note.text = note.note

        holder.delete.setOnClickListener { onDeleteListener.onDeleteListener(note) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val note = itemView.findViewById<TextView>(R.id.tv_note)
        val root = itemView.findViewById<ConstraintLayout>(R.id.root)
        val update = itemView.findViewById<ImageButton>(R.id.btn_update)
        val delete = itemView.findViewById<ImageButton>(R.id.btn_delete)
    }

}