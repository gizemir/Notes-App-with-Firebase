package com.gizemir.notesappwithfirebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.gizemir.notesappwithfirebase.databinding.FragmentSessionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SessionFragment : Fragment() {
    private var _binding: FragmentSessionBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSessionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSGnUp.setOnClickListener { signUp(it) }
        binding.buttonLogIn.setOnClickListener { logIn(it) }

        val currentUser = auth.currentUser
        if(currentUser != null){
            val action = SessionFragmentDirections.actionSessionFragmentToNotesFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }
    fun signUp(view: View){
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty()){
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task ->
                if(task.isSuccessful){
                    //kullanıcı oluşturuldu, diğer sayfaya git
                    val action = SessionFragmentDirections.actionSessionFragmentToNotesFragment()
                    Navigation.findNavController(view).navigate(action)
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun logIn(view: View){
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                val action = SessionFragmentDirections.actionSessionFragmentToNotesFragment()
                Navigation.findNavController(view).navigate(action)
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}