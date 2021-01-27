package com.example.myapplication4

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.KeyEvent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication4.view.UnderlineTextViews

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text2 = findViewById<TextView>(R.id.text_2)
        val text3: UnderlineTextViews = findViewById(R.id.text_3)
        val text4: UnderlineTextViews = findViewById(R.id.text_4)
        val text5: UnderlineTextViews = findViewById(R.id.text_5)
        val content = getString(R.string.hello)


        // 系统原生下划线
        val spanString = SpannableStringBuilder(content)
        Toast.makeText(this, spanString.subSequence(5, spanString.length), Toast.LENGTH_SHORT).show()
        spanString.setSpan(UnderlineSpan(), 0, spanString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        text2.text = spanString


        //添加多段
        text3.text = getString(R.string.hello3)
        val list: MutableList<Array<Int>> =mutableListOf()
        list.add(arrayOf(7, 10))
        list.add(arrayOf(13, 18))
        list.add(arrayOf(22, 28))
        list.add(arrayOf(33, 38))
        list.add(arrayOf(45, 55))
        text3.setStartEnds(list)


        text4.text = getString(R.string.hello1)
        text4.setStartEnd(15, 50)

        text5.text = getString(R.string.hello2)
        text5.setStartEnd(0, getString(R.string.hello2).length )
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {


            when (keyCode) {
                KeyEvent.KEYCODE_VOLUME_DOWN -> {
                    val text5: UnderlineTextViews = findViewById(R.id.text_5)
                    text5.text = getString(R.string.hello3)
                    text5.setStartEnd(0,text5.text.length)

                }
                KeyEvent.KEYCODE_VOLUME_UP ->{
//                    val text5: UnderlineTextViews = findViewById(R.id.text_5)
//                    text5.text = getString(R.string.hello2)
//                    text5.setStartEnd(0,text5.text.length - 1)

                    val text5: UnderlineTextViews = findViewById(R.id.text_5)
                    text5.text = "Look! Look!"
                    val list: MutableList<Array<Int>> =mutableListOf()
                    list.add(arrayOf(0, 5))
                    list.add(arrayOf(6, 10))
                    text5.setStartEnds(list)
                }

                KeyEvent.KEYCODE_BACK ->
                    return super.onKeyDown(keyCode, event)
                else -> {
                }
            }

        return super.onKeyDown(keyCode, event)
    }
}
