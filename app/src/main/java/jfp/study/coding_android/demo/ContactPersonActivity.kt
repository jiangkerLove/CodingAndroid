package jfp.study.coding_android.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import jfp.study.coding_android.R
import jfp.study.coding_android.databinding.ActivityContactPersonBinding

class ContactPersonActivity : AppCompatActivity() {

    private val list = listOf<ContactPersonAdapter.Person>(
        ContactPersonAdapter.Person("啊哦",'A'),
        ContactPersonAdapter.Person("啊此",'A'),
        ContactPersonAdapter.Person("版本",'B'),
        ContactPersonAdapter.Person("宝宝",'B'),
        ContactPersonAdapter.Person("辨别",'B'),
        ContactPersonAdapter.Person("存储",'C'),
        ContactPersonAdapter.Person("尺寸",'C'),
        ContactPersonAdapter.Person("传参",'C'),
        ContactPersonAdapter.Person("擦车",'C'),
        ContactPersonAdapter.Person("等待",'D'),
        ContactPersonAdapter.Person("迭代",'D'),
        ContactPersonAdapter.Person("抖动",'D'),
        ContactPersonAdapter.Person("滴滴",'D'),
        ContactPersonAdapter.Person("呃呃",'E'),
        ContactPersonAdapter.Person("谔谔",'E'),
        ContactPersonAdapter.Person("方法",'F'),
        ContactPersonAdapter.Person("分发",'F'),
        ContactPersonAdapter.Person("防范",'F'),
        ContactPersonAdapter.Person("更改",'G'),
        ContactPersonAdapter.Person("哥哥",'G'),
        ContactPersonAdapter.Person("更改",'G'),
        ContactPersonAdapter.Person("公共",'G'),
        ContactPersonAdapter.Person("还好",'H'),
        ContactPersonAdapter.Person("哈哈",'H'),
        ContactPersonAdapter.Person("花花",'H'),
        ContactPersonAdapter.Person("纠结",'J'),
        ContactPersonAdapter.Person("经济",'J'),
        ContactPersonAdapter.Person("看看",'K'),
        ContactPersonAdapter.Person("苛刻",'K'),
        ContactPersonAdapter.Person("开口",'K'),
        ContactPersonAdapter.Person("哦哦",'K'),
        ContactPersonAdapter.Person("品牌",'P'),
        ContactPersonAdapter.Person("匹配",'P'),
        ContactPersonAdapter.Person("批评",'P'),
        ContactPersonAdapter.Person("怕怕",'P'),
        ContactPersonAdapter.Person("亲求",'Q'),
        ContactPersonAdapter.Person("球球",'Q'),
        ContactPersonAdapter.Person("侵权",'Q'),
        ContactPersonAdapter.Person("让人",'R'),
        ContactPersonAdapter.Person("仍然",'R'),
        ContactPersonAdapter.Person("融入",'R'),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityContactPersonBinding>(
            this,
            R.layout.activity_contact_person
        )
        binding.recyclerContact.adapter = ContactPersonAdapter(list)
        binding.recyclerContact.addItemDecoration(ContactPersonDecoration())
    }



}