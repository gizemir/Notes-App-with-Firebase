package com.gizemir.notesappwithfirebase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.gizemir.notesappwithfirebase.databinding.FragmentNotesBinding
import com.gizemir.notesappwithfirebase.databinding.FragmentSessionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class NotesFragment : Fragment(), PopupMenu.OnMenuItemClickListener {
    private var _binding: FragmentNotesBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private lateinit var popup: PopupMenu
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    val noteList: ArrayList<Note> = arrayListOf()
    private  lateinit var adapterNote: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = Firebase.firestore

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener { floatingButtonClick(it) }
        popup = PopupMenu(requireContext(), binding.floatingActionButton)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.my_popup_menu, popup.menu)
        popup.setOnMenuItemClickListener(this)


        firestoreGetData()
        adapterNote = NoteAdapter(noteList)
        binding.noteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.noteRecyclerView.adapter = adapterNote

    }
    fun firestoreGetData(){
        db.collection("noteCollection")
            .get()
            .addOnSuccessListener {result ->
                if(result != null){
                for(document in result){

                    Log.d("Firestore", "Fetched ${result.size()} noteCollection")
                    //val yazilanlar = document.get("note") as String

                    //val gorunen = Note(yazilanlar)
                    val gorunen = document.toObject(Note::class.java)
                    noteList.add(gorunen)
                }
                adapterNote.notifyDataSetChanged()
            } else {
            Log.d("Firestore", "No documents found")
        }
            }.addOnFailureListener { exception->
                Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }
    fun floatingButtonClick(view: View){
        popup.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.logOutItem){
            val action = NotesFragmentDirections.actionNotesFragmentToSessionFragment()
            Navigation.findNavController(requireView()).navigate(action)
            auth.signOut()
        }else if(item?.itemId == R.id.newNoteItem){
            val action = NotesFragmentDirections.actionNotesFragmentToEditFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
        return true
    }
}