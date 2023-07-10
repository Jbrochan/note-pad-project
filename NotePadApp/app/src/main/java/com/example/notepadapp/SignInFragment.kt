package com.example.notepadapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notepadapp.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private lateinit var fragmentSignInBinding: FragmentSignInBinding
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSignInBinding = FragmentSignInBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentSignInBinding.run{
            buttonSignIn.run{
                setOnClickListener {
                    val userPassword = UserDAO.selectData(context, 1).userPassword.toString()
                    val userPasswordInput = editTextSignInPassword.text.toString()
                    
                    // 비밀번호가 일치할 경우 메인 화면으로 이동
                    if(userPassword == userPasswordInput){
                        mainActivity.replaceFragment(FragmentName.FRAGMENT_MAIN, false, true)
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        return fragmentSignInBinding.root
    }
}