## MeasureSpec

- 是View的静态内部类，主要是定义的View的尺寸计算规则。32位的int值，前两位为测量模式，后30位为数值。
- UNPECIFIED ：未指定模式，View想要多大就多大，父容器不做限制，一般为系统布局的测量。
- AT_MOST： 最大模式，对应于wrap_content属性，子View的最终大小是父View指定的SpecSize，并且子View的大小不能大于这个值。
- EXACTLY： 精确模式，对应于match_parent属性和具体的数值，父容器测量出View所需要的大小，也就是SpecSize的值。

- 主要方法
  - makeMeasureSpec用来使用mode和size生成一个保存所有信息的int值。
  - getMode来获取int值中保存的模式信息。
  - getSize来获取int值中保存的Size信息。
  - getRootMeasureSpec 根据size以及layoutParams的模式来生成spec参数。

## Measure流程

- 如果自定义的View未重写onMeasure方法或未对warp_content进行特殊处理，则和match_parent效果是一样的。

- 建议的最小宽度为

  - 在未设置背景时为设置的最小值
  - 在设置了背景是为设置的最小值和背景的宽度中的最大值

- 因为ViewGroup对子View的测量方式的不同，所以ViewGroup的onMeasure需要在自定义时去根据自己的需求重写，不重写的话子view是不会被测量的，就没有measure后的值。

- ViewGroup在测量子View时，最大可以给予的宽高需要减去自己的padding值以及子View的margin值。

- ViewGroup中主要的测量方法

  - ```java
    // 根据父布局可以给予的sepc值以及padding和子view的布局参数计算子view的sepc值
    public static int getChildMeasureSpec(int spec, int padding, int childDimension)
    ```

  - ```java
    //测量指定的子view宽高，参数为父view可以给予的最大宽高，在内部会计算出自己的padding然后调用getChildMeasureSpec方法
    protected void measureChild(View child, int parentWidthMeasureSpec,
            int parentHeightMeasureSpec)
    ```

  - ```java
    // 通过遍历子view去通过measureChild方法进行测量
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec)
    ```

- 因为默认的view的layoutParams是不支持margin的，所以viewgroup中的measure方法是不带margin的，所以自定义时最常用的还是getChildMeasureSpec方法。

- 如果需要支持子View的margin，需要在自定义view时重写generateLayoutParams(AttributeSet attrs)和generateDefaultLayoutParams()返回ViewGroup.MarginLayoutParams或其子类的对象，前者会在解析布局参数时调用，而后者为addView时不带布局参数时调用生成一个默认的布局参数。
- onMeasure方法中必须调用setMeasuredDimension去设置值。

## Layout流程

- 若measure方法被跳过了，则会在layout时候进行测量。
- 调用setFrame方法进行位置的放置，若设置的值对比之前的有改变，则会触发onLayout方法，这个方法是需要在自定义的ViewGroup中进行重写的。
- 自定义ViewGroup必须重写onLayout方法对子View进行放置，layout方法的参数是相对于父布局左上角坐标的。
- 若ViewGroup支持margin，则需要在onLayout的时候对margin进行处理，并且需要注意自己的padding值。

## draw流程

- 流程
  - 如果需要，绘制背景 drawBackground(canvas)
  - 保存当前cavas层
  - 绘制view的内容 onDraw(canvas)
  - 绘制子view dispatchDraw(canvas)
  - 如果需要，绘制View的褪色边缘
  - 绘制装饰，滚动条以及foreground的绘制 onDrawForeground(canvas)

- view中有一个setWillNotDraw方法，若为true，会给View的mPrivateFlags参数增加PFLAG_SKIP_DRAW参数，View只会去绘制子View以及drawAutofilledHighlight方法。而ViewGroup初始化的时候会默认把这个值置为true，所以自定义Viewgroup时若需要重写onDraw方法则需要自己去调用这个方法设置为false。
- 设置WILL_NOT_DRAW标志时，若有background等需要绘制的内容，是无效的，不会跳过绘制。
- 重写onDraw时不能在其中new对象，因为会频繁调用，容易造成内存的抖动。
- 自定义View中，在onDraw中需要考虑padding对自己的影响，默认padding值是不会对View有影响的。 

## 自定义View需注意

- 自定义View中需要增加对warp_content的支持，否则和match_parent无差异
- 自定义ViewGroup时onMeasure需要考虑自己的padding和子view的margin对其影响
- 自定义View需要在onDraw时考虑padding对其的影响。
- 自定义ViewGroup如果需要在onDraw中进行绘制，需要在初始化时调用setWillNotDraw(false)
