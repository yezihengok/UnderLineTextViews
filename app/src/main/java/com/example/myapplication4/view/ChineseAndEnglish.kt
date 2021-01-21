package com.example.myapplication4.view

import java.util.regex.Pattern

/**
 * Created by yzh on 2021/1/14 10:49.
 */
object ChineseAndEnglish {
    // GENERAL_PUNCTUATION 判断中文的"号
    // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
    // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
    /**
     *
     * 是否是中文
     *
     * @param c
     *
     * @return
     */
    fun isChinese(c: Char): Boolean {
        val ub = Character.UnicodeBlock.of(c)
        return ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub === Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub === Character.UnicodeBlock.GENERAL_PUNCTUATION || ub === Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub === Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
    }

    /**
     *
     * 是否是英文
     *
     * @param
     *
     * @return
     */
    fun isEnglish(charaString: String): Boolean {
        return charaString.matches("^[a-zA-Z]*".toRegex())
    }

    fun isChinese(str: String?): Boolean {
        val regEx = "[\\u4e00-\\u9fa5]+"
        val p = Pattern.compile(regEx)
        val m = p.matcher(str)
        return m.find()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(isChinese('员'))
        println(isChinese('s'))
        println(isEnglish("挂靠费号开始"))
        println(isEnglish("robert"))
        println(isChinese("车费发送是否"))
    }
}
