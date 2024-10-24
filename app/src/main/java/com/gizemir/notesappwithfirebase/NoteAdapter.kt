package com.gizemir.notesappwithfirebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gizemir.notesappwithfirebase.databinding.RecyclerRowBinding

class NoteAdapter(private val noteList: ArrayList<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewholder>(){
    class NoteViewholder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewholder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent, false )
        return NoteViewholder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViewholder, position: Int) {
        holder.binding.textViewRecyclerView.text = noteList[position].note
    }
}