package com.example.notepadapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
                    // 카테고리 추가 기능
                    when(it.itemId){
                        R.id.itemMainAdd -> {
                            val builder = AlertDialog.Builder(context)
                            builder.setTitle("새로운 카테고리 생성")
                            builder.setMessage("새로운 카테고리의 이름을 입력주세요")
                            val editText: EditText = EditText(context)
                            builder.setView(editText)
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                var categoryName = editText.text.toString()
                                // 카테고리 이름 중복 체크
                                if(CategoryDAO.selectNameData(mainActivity, categoryName) == null){
                                    CategoryDAO.selectNameData(mainActivity, categoryName)
                                        ?.let { it1 -> Log.d("user", it1.categoryTitle) }
                                    CategoryDAO.insertData(context, CategoryData(0, categoryName))
                                    categoryList = CategoryDAO.selectAllData(context)
                                    recyclerViewMain.adapter?.notifyDataSetChanged()
                                } else {
                                    val builder = AlertDialog.Builder(context)
                                    builder.setTitle("이미 존재하는 카테고리 입니다")
                                    builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int -> }
                                    builder.show()
                                }
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

                rowMainBinding.root.setOnCreateContextMenuListener { contextMenu, view, contextMenuInfo ->
                    contextMenu.setHeaderTitle("카테고리 관리")
                    mainActivity.menuInflater.inflate(R.menu.menu_main_context, contextMenu)
                    // 카테고리 이름 수정 기능
                    contextMenu.getItem(0).setOnMenuItemClickListener {
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("카테고리 관리")
                        builder.setMessage("변경할 카테고리의 이름을 입력주세요")
                        val editText: EditText = EditText(context)
                        builder.setView(editText)
                        builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                            // 카테고리 이름 중복 체크
                            val newCategoryTitle = editText.text.toString()
                            if(CategoryDAO.selectNameData(mainActivity, newCategoryTitle) == null){
                                CategoryDAO.updateData(mainActivity, newCategoryTitle, textviewRow.text.toString())
                                categoryList = CategoryDAO.selectAllData(mainActivity)
                                notifyDataSetChanged()
                            } else {
                                val builder = AlertDialog.Builder(context)
                                builder.setTitle("이미 존재하는 카테고리 입니다")
                                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int -> }
                                builder.show()
                            }
                        }
                        builder.setNegativeButton("취소"){ dialogInterface: DialogInterface, i: Int -> }
                        builder.show()
                        false
                    }
                    // 카테고리 삭제 기능
                    contextMenu.getItem(1).setOnMenuItemClickListener {
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("카테고리 삭제")
                        builder.setMessage("카테고리를 삭제하시겠습니까?")
                        builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                            CategoryDAO.deleteData(mainActivity, textviewRow.text.toString())
                            categoryList = CategoryDAO.selectAllData(mainActivity)
                            notifyDataSetChanged()
                        }
                        builder.setNegativeButton("취소"){ dialogInterface: DialogInterface, i: Int -> }
                        builder.show()
                        false
                    }
                }
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