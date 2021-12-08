# Android自定义组件库

## 引用

1.在根目录build.gradle文件中添加

```gradle
    maven { url 'https://jitpack.io' }
 ```

2.项目build.gradle文件中添加

```gradle
    implementation 'com.github.Jun1orChan:Widget-Android:1.2.0'
 ```

## 使用

- 部分使用参考Demo


## 版本记录

### 1.2.0

- 删除下拉刷新控件的实现
- 适配Android 12
- 依赖库改为编译期间依赖

### 1.1.1

- 国际化默认中文

### 1.1.0

- 文案支持国际化（中文、繁体香港、繁体台湾、英文）

### 1.0.0

- DividerItemDecoration：RecyclerView配合LinearLayoutManager使用设置分割线样式
- GridDividerItemDecoration：RecyclerView配合GridLayoutManager使用设置分割线样式
- PhotoView：图片手势操作
- PressedImageView：触摸图片时，出现灰色蒙版效果
- PageIndicatorView：配合ViewPager使用的指示器 参考demo
- SquareFrameLayout：根据宽度决定高度，宽度等于高度
- TouchEventContainerLayout：用于拦截事件分发，如ScrollView中嵌套Map
- JCircleProgress：圆形转动环
- NumberProgressBar：带数字的进行条
- AlphaButton：单击改变透明度
- AlphaImageButton：单击改变透明度
- AlphaTextView：单击改变透明度
- UnScrollViewPager：禁止左右滑动的ViewPager
