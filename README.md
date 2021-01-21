# UnderLineTextViews


>众所周知 android自带的下划线，如 SpannableString 是基于BaseLine去画的无法单独更改颜色、与文字的间距。 遇到如：good 的g 下划线甚至与字符重合 参见下图。
基于此便自定义实现需求： 自定义 可设置与文本间距、颜色 、指定开始结束索引的方式添加下划线的textView


该项目基于原作者 [(UnderLineTextView)](https://github.com/lixiaote/UnderLineTextView) 功能修复 优化而来  

 ### UnderLineTextViews 
  * 新增 下划线可添加与文字间距 （注意下划线与文字间隔值设置的过于大时，请适当设置调大setLineSpacing 行间距）
  * 新增 下划线可设指定的索引位置 添加下划线，也可文本全部添加。
  * 优化了中引文混排时 行末最后一个字符下划线 显示过长过短问题。（原作者是基于下划线第一个字符为偏移量，而下划线可能第一个字符是英文。下划线最后一个字符为中文的情况）
  
####截图：
![MvvmApp-master](https://github.com/yezihengok/UnderLineTextViews/blob/master/screenshots/device-1.png)

 
 

 




