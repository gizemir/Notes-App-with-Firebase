package com.gizemir.notesappwithfirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.gizemir.notesappwithfirebase.databinding.FragmentEditBinding
import com.gizemir.notesappwithfirebase.databinding.FragmentNotesBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EditFragment : Fragment() {
    private var _binding: FragmentEditBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAdd.setOnClickListener { add(it) }
    }
    fun add(view: View){
        val notes = hashMapOf<String, Any>()
        notes.put("note", binding.editTextNote.text.toString())

        db.collection("noteCollection")
            .add(notes)
            .addOnSuccessListener {
                //notlar database yüklendi
                val action = EditFragmentDirections.actionEditFragmentToNotesFragment()
                Navigation.findNavController(view).navigate(action)

            }.addOnFailureListener { exception->
                Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}