package com.example.notepadapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.notepadapp.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    lateinit var userPassword: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // 비밀 번호 설정 여부에 따라 회원가입과 로그인으로 분기
        if(UserDAO.selectAllData(this@MainActivity).size == 0){
            replaceFragment(FragmentName.FRAGMENT_SIGN_UP, false, true)
        } else{
            replaceFragment(FragmentName.FRAGMENT_SIGN_IN, false, true)
        }
    }

    fun replaceFragment(name: FragmentName, addToBackStack: Boolean, animate: Boolean){
        // Fragment 교체 상태로 설정
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        // 새로운 Fragment를 담을 변수
        var newFragment: Fragment? = null

        // Fragment 이름으로 분기
        when(name){
            FragmentName.FRAGMENT_SIGN_UP -> {
                newFragment = SignUpFragment()
            }
            FragmentName.FRAGMENT_SIGN_IN -> {
                newFragment = SignInFragment()
            }
            FragmentName.FRAGMENT_MAIN -> {
                newFragment = MainFragment()
            }
        }

        if(newFragment != null){
            // Fragment 교체
            fragmentTransaction.replace(R.id.fragmentContainerViewMain, newFragment)

            if (animate == true) {
                // 애니메이션을 설정한다.
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            }

            if (addToBackStack == true) {
                // Fragment를 Backstack에 넣어 이전으로 돌아가는 기능이 동작할 수 있도록 한다.
                fragmentTransaction.addToBackStack(name.str)
            }

            // 교체 명령이 동작하도록 한다.
            fragmentTransaction.commit()
        }
    }

    // Fragment를 BackStack에서 제거
    fun removeFragment(name:FragmentName){
        supportFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

enum class FragmentName(val str: String){
    FRAGMENT_SIGN_UP("SignUpFragment"),
    FRAGMENT_SIGN_IN("SignInFragment"),
    FRAGMENT_MAIN("MainFragment")
}