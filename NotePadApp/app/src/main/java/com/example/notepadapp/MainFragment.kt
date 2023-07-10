package com.example.notepadapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepadapp.databinding.FragmentMainBinding
import com.example.notepadapp.databinding.RowMainBinding

class MainFragment : Fragment() {

    private lateinit var fragmentMainBinding: FragmentMainBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var categoryList: ArrayList<CategoryData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMainBinding = FragmentMainBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentMainBinding.run{
            toolbarMain.run{
                title = "카테고리 목록"
                setTitleTextColor(Color.WHITE)
                inflateMenu(R.menu.menu_main)

                setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.itemMainAdd -> {
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle("새로운 카테고리 생성")
                            builder.setMessage("새로운 카테고리의 이름을 입력주세요")
                            val editText: EditText = EditText(context)
                            builder.setView(editText)
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                var categoryName = editText.text.toString()
                                CategoryDAO.insertData(context, CategoryData(0, categoryName))
                                categoryList = CategoryDAO.selectAllData(context)
                                recyclerViewMain.adapter?.notifyDataSetChanged()
                            }
                            builder.setNegativeButton("취소"){ dialogInterface: DialogInterface, i: Int -> }
                            builder.show()
                        }
                    }
                    false
                }
            }
            recyclerViewMain.run{
                categoryList = CategoryDAO.selectAllData(context)
                adapter = RecyclerViewAdapterClass()
                layoutManager = LinearLayoutManager(context)
            }
        }

        // Inflate the layout for this fragment
        return fragmentMainBinding.root
    }

    inner class RecyclerViewAdapterClass : RecyclerView.Adapter<RecyclerViewAdapterClass.ViewHolderClass>(){
        inner class ViewHolderClass(rowMainBinding: RowMainBinding) : RecyclerView.ViewHolder(rowMainBinding.root){
            var textviewRow: TextView

            init{
                textviewRow = rowMainBinding.textViewRowCategory
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowMainBinding = RowMainBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(rowMainBinding)

            // 항목 View 의 가로 세로 길이를 설정
            val params = RecyclerView.LayoutParams(
                // 가로 길이
                RecyclerView.LayoutParams.MATCH_PARENT,
                // 세로 길이
                RecyclerView.LayoutParams.WRAP_CONTENT
            )
            rowMainBinding.root.layoutParams = params

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return categoryList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textviewRow.text = categoryList[position].categoryTitle
        }
    }
}