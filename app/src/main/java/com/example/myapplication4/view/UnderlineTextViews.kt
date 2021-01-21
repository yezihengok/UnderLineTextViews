package com.example.myapplication4.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import com.example.myapplication4.R
import java.util.*

/**
 * Created by yzh on 2021/1/11 17:12.
 */
//public class UnderlinedTextView extends AppCompatTextView {
//
//    private Rect lineBoundsRect;
//    private Paint underlinePaint;
//    float mStrokeWidth=4;
//    public UnderlinedTextView(Context context) {
//        this(context, null, 0);
//    }
//
//    public UnderlinedTextView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public UnderlinedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context, attrs, defStyleAttr);
//    }
//
//    private void init(Context context, AttributeSet attributeSet, int defStyle) {
//
//        float density = context.getResources().getDisplayMetrics().density;
//
//        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.UnderlinedTextView, defStyle, 0);
//        int mColor = typedArray.getColor(R.styleable.UnderlinedTextView_underlineColor, 0xFFFF0000);
//        mStrokeWidth = typedArray.getDimension(R.styleable.UnderlinedTextView_underlineWidth, density * 2);
//        typedArray.recycle();
//
//        lineBoundsRect = new Rect();
//        underlinePaint = new Paint();
//        underlinePaint.setStyle(Paint.Style.STROKE);
//        underlinePaint.setColor(mColor); //color of the underline
//        underlinePaint.setStrokeWidth(mStrokeWidth);
//    }
//
//    @ColorInt
//    public int getUnderLineColor() {
//        return underlinePaint.getColor();
//    }
//
//    public void setUnderLineColor(@ColorInt int mColor) {
//        underlinePaint.setColor(mColor);
//        invalidate();
//    }
//
//    public float getUnderlineWidth() {
//        return underlinePaint.getStrokeWidth();
//    }
//
//    public void setUnderlineWidth(float mStrokeWidth) {
//        underlinePaint.setStrokeWidth(mStrokeWidth);
//        invalidate();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//
//        int count = getLineCount();
//
//        final Layout layout = getLayout();
//        float x_start, x_stop, x_diff;
//        int firstCharInLine, lastCharInLine;
//
//        for (int i = 0; i < count; i++) {
//            int baseline = getLineBounds(i, lineBoundsRect);
//            firstCharInLine = layout.getLineStart(i);
//            lastCharInLine = layout.getLineEnd(i);
//
//            x_start = layout.getPrimaryHorizontal(firstCharInLine);
//            x_diff = layout.getPrimaryHorizontal(firstCharInLine + 1) - x_start;
//            x_stop = layout.getPrimaryHorizontal(lastCharInLine - 1) + x_diff;
//
//            int lineSpace=6;
//            canvas.drawLine(x_start, baseline + mStrokeWidth+lineSpace, x_stop, baseline + mStrokeWidth+lineSpace, underlinePaint);
//        }
//
//        super.onDraw(canvas);
//    }
//}
/**
 * 可跨行 指定索引的下划线
 * Created by yzh on 2021/1/11 17:12.
 */
class UnderlineTextViews @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {
    private val TAG = "UnderlineTextViews"
    lateinit var mRect: Rect
    lateinit var mPaint: Paint
    private var mColor = 0
    private var density = 0f
    private var mStrokeWidth = 0f
    private var mLineTopMargin = 0f
    private var mList: MutableList<Array<Int>>? = null
    private var content:String=""

    init {
        init(context, attrs, defStyleAttr)
    }

    /**
     * 前包含 后不包含
     * 添加多段索引
     * @param mList
     */
    fun setStartEnds(mList: MutableList<Array<Int>>?) {
        this.mList = mList
        if (!mList.isNullOrEmpty()) {
            invalidate()
        }
    }

    /**
     * 添加下划线起止的起止索引。[start,end)  例如 3,5  则等于 包含3不包含5
     * @param start
     * @param end
     */
    fun setStartEnd(start: Int, end: Int) {
        if (start < 0) {
            return
        }
        if (start < end) {
            val data = arrayOf(start, end)
            mList = mutableListOf()
            mList?.add(data)
            setStartEnds(mList)
        }
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        //获取屏幕密度
        density = context.resources.displayMetrics.density
        //获取自定义属性
        val array = context.obtainStyledAttributes(attrs, R.styleable.UnderlinedTextView, defStyleAttr, 0)
        mColor = array.getColor(R.styleable.UnderlinedTextView_underlineColor, -0x10000)
        mStrokeWidth = array.getDimension(R.styleable.UnderlinedTextView_underlineWidth, density * 2)
        mLineTopMargin = array.getDimension(R.styleable.UnderlinedTextView_underlineTopMargin, density * 2)
        //mLineTopMargin 设值较大的时候需要适当增加行点间距，否则会显示不下。
        setLineSpacing(mLineTopMargin, 1.1.toFloat())
        setPadding(left, top, right, bottom)
        array.recycle()
        mRect = Rect()
        mPaint = Paint()
        mPaint.style = Paint.Style.STROKE
        mPaint.color = mColor
        mPaint.strokeWidth = mStrokeWidth
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onDraw(canvas: Canvas) {
        content= text.toString()
        if (mList.isNullOrEmpty() || content.isEmpty()) {
            super.onDraw(canvas)
            return
        }
        //        if(BuildConfig.DEBUG){
//            setBackgroundColor(getContext().getColor(R.color.black_alpha10));
//        }

        //得到TextView显示有多少行
        val count = lineCount
        //得到TextView的布局
        val layout = layout

        Log.e(TAG, "layout--------$layout")
        //    TextView layout属性：
//
//    TextView 的 layout里面包含各种获取字符位置、行数、列数等的方法
//    layout.getLineForOffset 获取该字符所在行数.
//    layout.getLineBounds获取该行的外包矩形（Rect) 这样 这个字符的顶部Y坐标就是rect的top 底部Y坐标就是rect的bottom
//    layout.getPrimaryHorizontal获取该字符左边的X坐标

//    layout.getSecondaryHorizontal获取该字符字符的右边X坐标   ----感觉无效 和getPrimaryHorizontal 值一样
        for (indexes in mList!!) {
            if (indexes.isEmpty()) {
                Log.e(TAG, "起止索引有误!")
                continue
            }
            val start = indexes[0]
            val end = indexes[1]
            if (start < 0 || end == 0) {
                Log.e(TAG, "起止索引有误!")
                continue
            }
            val lineStart = layout.getLineForOffset(start)
            val lineEnd = layout.getLineForOffset(end)
            //只存在单行
            if (lineStart == lineEnd) {
                //getLineBounds得到这一行的外包矩形,这个字符的顶部Y坐标就是rect的top 底部Y坐标就是rect的bottom
                val baseline = layout.getLineBounds(lineStart, mRect)
                // int yStart=mRect.bottom;//字符底部y坐标
                val xStart = layout.getPrimaryHorizontal(start) //开始字符左边x坐标
                
                val msg: String = content[start].toString()
                val msg2: String = content[end].toString()
                Log.i(TAG, "$start============>$end")
                Log.w(TAG, "$msg============>$msg2")

                val xEnd = layout.getPrimaryHorizontal(end)  //结束字符左边x坐标
                val offsetY = baseline + mLineTopMargin + mStrokeWidth
                canvas.drawLine(xStart, offsetY, xEnd, offsetY, mPaint)

            } else {
                //下划线跨行了
                for (i in lineStart..lineEnd) {
                    val baseline = layout.getLineBounds(i, mRect)
                    val firstCharInLine = layout.getLineStart(i)
                    val lastCharInLine = layout.getLineEnd(i) - 1
                    val startIndex = if (i == lineStart) start else firstCharInLine //开始的索引
                    var endIndex = if (i == lineEnd) end else lastCharInLine //结束的索引
//
//                    if(endIndex<content.length-1){
//                        endIndex++
//                    }
                    if(endIndex>=content.length){
                        endIndex--
                    }
                    Log.i(TAG, "$startIndex---------->$endIndex")
                    //开始字符左边x坐标
                    val xStart = layout.getPrimaryHorizontal(startIndex) //第一行从指定start 后面行从每行开头开始


                    val msg: String = content[startIndex].toString()
                    val msg2: String = content[endIndex].toString()
                    Log.i(TAG, "$start---------->$end")
                    Log.w(TAG, "$msg---------->$msg2")


                    //tips ：xOffset 是按开始索引位置的 1个字符所占的x 来计算的。 因为可能出现中英文混合情况。而1个汉字的x坐标占位宽度 大概是1个英文字母的 1.8倍
                    //如果下划线的开头 是英文、结尾是中文 。实际偏移量x1.8   反之 除以1.8

                    //tips2：英文单词 一个单词 一行显示不下时。 会强行换行，而下划线是按原本占位画的，所以下划线会突出一点，末尾是空格的可以-1个占位 不添加偏移量（因为英文单词强制换行了）


                    //,因为无法或者行末尾字符的右边x坐标 layout.getSecondaryHorizontal 与 getPrimaryHorizontal 值是一样的。
                    //所以截止末尾的前一个字符作为,作为最后字符的x偏移量加上.
                    var xOffset = layout.getPrimaryHorizontal(endIndex)-layout.getPrimaryHorizontal(endIndex - 1)

                    xOffset = getxOffset(i, endIndex, xOffset)
                    val xEnd = layout.getPrimaryHorizontal(endIndex) + xOffset //最后行从指定截止，其余每行末尾截止

                    //直接取文字的最右边
                  //  val xEnd= mRect.right.toFloat()
                    val offsetY = baseline + mLineTopMargin + mStrokeWidth
                    canvas.drawLine(xStart, offsetY, xEnd, offsetY, mPaint)
                }
            }
        }
        super.onDraw(canvas)
    }

    /**
     * 处理中英文混排调整xOffset 偏移量
     *
     * @param startIndex
     * @param endIndex
     * @param xOffset
     * @return
     */
    private fun getxOffset(i: Int, endIndex: Int, xOffset: Float): Float {
        var endIndex =endIndex
        var xOffset = xOffset
        if(endIndex==content.length){
            endIndex--
        }
        val content = text.toString()
        val offsetEndStr=content[endIndex - 1]
        val endStr=content[endIndex]

        if (ChineseAndEnglish.isChinese(offsetEndStr)) {
            if (ChineseAndEnglish.isEnglish(endStr.toString())) {
                Log.v("xOffset", "xOffset-->$xOffset")
                xOffset /= 1.8f
                Log.w("xOffset", "xOffset-->$xOffset")
            }
        }
        if (ChineseAndEnglish.isEnglish(endStr.toString())) {
            if (ChineseAndEnglish.isChinese(offsetEndStr)) {
                Log.v("xOffset", "xOffset-->$xOffset")
                xOffset *= 1.8f
                Log.w("xOffset", "xOffset-->$xOffset")
            }
        }
        if (Character.isWhitespace(endStr)) {
            xOffset = 0f
            Log.w("xOffset", "末尾是空格不添加偏移量xOffset")
        }
        Log.i("xOffset", "$i====xOffset===>$xOffset")
        return xOffset
    }


    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom + mLineTopMargin.toInt() + mStrokeWidth.toInt())
    }

    var underLineColor: Int
        get() = mColor
        set(mColor) {
            this.mColor = mColor
            invalidate()
        }

    var underlineWidth: Float
        get() = mStrokeWidth
        set(mStrokeWidth) {
            this.mStrokeWidth = mStrokeWidth
            invalidate()
        }

}
