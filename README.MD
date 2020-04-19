# Android自定义组件库

## 引用

1.在根目录build.gradle文件中添加

```gradle
         maven {
                url "http://114.215.182.48:7777/nexus/content/repositories/Android/"
            }
 ```

2.项目build.gradle文件中添加

```gradle
    implementation 'com.istrong.baselib:widget:1.0.14'
 ```

## 使用

- 部分使用参考Demo


## 版本记录

### 1.0.14

- 适配Android 9

### 1.0.10

- 新增 RecBanner组件，用于轮播图

### 1.0.9

- PhotoView 添加长按事件的监听，setOnLongClickListener()

### 1.0.8

- PullRefreshLayout 修复当处于COMPLETE状态时，内容控件如：RecyclerView可以向上滚动的问题。
- DividerItemDecoration 去除是否第一个Item绘制上方divider的构造方法。

### 1.0.7

- PullRefreshLayout 修改refreshComplete()方法为默认带停留效果
- PullRefreshLayout 新增设置刷新完成停留时间（支持XML和代码设置）。
- PullRefreshLayout 新增方法：refreshCompleteImmediately();
- ClassicHeaderView 修改下拉刷新图标
- OnRefreshListener 新增onRefreshComplete()方法回调
- DividerItemDecoration 新增是否第一个Item绘制上方divider的构造方法。

### 1.0.6

- 新增HorizentalProgressBar，显示水平方向上的进度，支持自定义背景和前景色

### 1.0.5

- 去除PullRefreshLayout切换Content布局的方法，通过设置Content显示与否完成。
- 所有的自定义属性都添加上模块前缀，避免和其他组件发生冲突的情况。
改变的属性：

    1、NumberProgressBar
    * widget_progress_current
    * widget_progress_max
    * widget_progress_unreached_color
    * widget_progress_reached_color
    * widget_progress_reached_bar_height
    * widget_progress_unreached_bar_height
    * widget_progress_text_size
    * widget_progress_text_color
    * widget_progress_text_offset
    * widget_progress_text_visibility
    * widget_numberProgressBarStyle

    2、SlidingDrawerLayout
    * widget_interval
    * widget_selectedDrawable
    * widget_unSelectedDrawable

    3、JCircleProgress
    * widget_startColor
    * widget_endColor
    * widget_strokeWidth

    4、ClassicHeaderView
    * widget_textColor
    * widget_circleProgressStartColor
    * widget_circleProgressEndColor

### 1.0.4

- 增加PullRefreshLayout切换content布局的时候，设置LayoutParams

### 1.0.3

- 修复PullRefreshLayout当在刷新状态时的上划越界问题。

### 1.0.2

- 重写RefreshLayout，改名为：PullToRefreshLayout。
- 修复DividerItemDecoration设置了最后一行分割线显示时，不绘制的bug。

### 1.0.1

- RefreshLayout：下拉刷新组件
- ClassicHeaderView：内置的下拉刷新头部
- 更新JCirCleProgress控件，支持设置开始和结束颜色、线条宽度、支持XML设置

### 1.0.0

- DividerItemDecoration：RecyclerView配合LinearLayoutManager使用设置分割线样式
- GridDividerItemDecoration：RecyclerView配合GridLayoutManager使用设置分割线样式
- PhotoView：图片手势操作
- PressedImageView：触摸图片时，出现灰色蒙版效果
- PageIndicatorView：配合ViewPager使用的指示器 参考demo
- SlidingDrawerLayout：替代系统的SlidingDrawer
- SquareFrameLayout：根据宽度决定高度，宽度等于高度
- TouchEventContainerLayout：用于拦截事件分发，如ScrollView中嵌套Map
- JCircleProgress：圆形转动环
- NumberProgressBar：带数字的进行条
- AlphaButton：单击改变透明度
- AlphaImageButton：单击改变透明度
- AlphaTextView：单击改变透明度
- UnScrollViewPager：禁止左右滑动的ViewPager
