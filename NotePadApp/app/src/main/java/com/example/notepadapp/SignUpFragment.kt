package com.example.notepadapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notepadapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var fragmentSignUpBinding: FragmentSignUpBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        fragmentSignUpBinding = FragmentSignUpBinding.inflate(layoutInflater)

        fragmentSignUpBinding.run{
            buttonSignUp.run{
                setOnClickListener {
                    val password = editTextSignUpPassword.text.toString()
                    val passwordCheck = editTextSignUpPasswordCheck.text.toString()
                    if(password == passwordCheck){
                        UserDAO.insertData(context, UserData(0, Integer.parseInt(password)))
                    } else {
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("비밀번호 오류")
                        builder.setMessage("비밀번호가 일치하지 않습니다\n다시 입력해주세요")
                        builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int -> }
                        builder.show()
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        return fragmentSignUpBinding.root
    }
}