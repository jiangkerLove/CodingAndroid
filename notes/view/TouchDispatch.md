
对于事件分发，首先的是方法返回true表示消费了事件，false表示继续传递

1. 首先点击事件产生之后，是交给activity来处理的，而activity主要管理的是界面的生命周期，而对于界面的显示等都是由activity中的成员window来管理的，算是一种职责上的分离吧。
```java
public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
```
2. window中维护的最上层的view就decorView，我们activity中添加的界面就是添加到这个view中的，所以事件会传递到decorView中
```java
	//PhoneWindow.java
 @Override
    public boolean superDispatchTouchEvent(MotionEvent event) {
        return mDecor.superDispatchTouchEvent(event);
    }
    //DecorView
    public boolean superDispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
```
3. DecorView实际上也是继承至viewgroup的，所以这里调用的其实viewgroup的dispatchTouchEvent(MotionEvent ev)方法，大致流程如下
```java
@Override
public boolean dispatchTouchEvent(MotionEvent ev) {
	//down表示一个事件的开始，所以进行初始化清空targets
	if (actionMasked == MotionEvent.ACTION_DOWN) {
		cancelAndClearTouchTargets(ev);
		resetTouchState();
	}
	//是否拦截的标记
	final boolean intercepted;
	//如果是down事件或者target不为空则会进入这个方法判断(有子view可以消费这个事件)
	if (actionMasked == MotionEvent.ACTION_DOWN|| mFirstTouchTarget != null) {
		//如果子事件设置了不让父控件拦截则不会进行onInterceptTouchEvent判断了
		final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
		if (!disallowIntercept) {
			//若需要拦截这个事件，则重写这个方法，返回true
			intercepted = onInterceptTouchEvent(ev);
			ev.setAction(action); // restore action in case it was changed
		} else {
			intercepted = false;
		}
	} else {
		intercepted = true;
	}
	//如果事件被自己拦截了，就不会执行分发给子view的事件了
	if (!canceled && !intercepted) {
			final int childrenCount = mChildrenCount;
			if (newTouchTarget == null && childrenCount != 0) {
				//如果有子view则倒序遍历看子view是否能接收到这个事件，相对于点击的位置是否在子view上
				for (int i = childrenCount - 1; i >= 0; i--) {
					//传递给子view是否可以处理
					if (dispatchTransformedTouchEvent(ev, false, child, idBitsToAssign)) {
						//若可以拦截这个事件则给target添加view
						break;
					}
				}
			}
		}
	}
	if (mFirstTouchTarget == null) {
		//因为target中没有元素，则调用这个方法，传入的子view是空，去回调自己父类view的dispatchTouchEvent方法
		handled = dispatchTransformedTouchEvent(ev, canceled, null,TouchTarget.ALL_POINTER_IDS);
	} else{
		//当传给子view的事件又被他自己拦截了，就会传递给子view一个取消事件，然后清空target
        final boolean cancelChild = resetCancelNextUpFlag(target.child)|| intercepted;
        dispatchTransformedTouchEvent(ev, cancelChild,target.child, target.pointerIdBits);
        // 清空target
        if (cancelChild) {
            if (predecessor == null) {
                mFirstTouchTarget = next;
            } else {
                predecessor.next = next;
            }
            target.recycle();
            target = next;
            continue;
        }
	}
	return handled;
}
```
4. 当上述流程中这个viewgroup拦截了事件的话，target就会一直为空，就会执行下放为空的方法
```java
private boolean dispatchTransformedTouchEvent(MotionEvent event, boolean cancel,
            View child, int desiredPointerIdBits) {
	if (child == null) {
		//如果child为空就调用父类的dispatchTouchEvent方法
		handled = super.dispatchTouchEvent(event);
	} else {
		//如果child不为空就传递给child的dispatchTouchEvent去继续分发
		handled = child.dispatchTouchEvent(event);
	}
	return handled;
}
```
5. 然后在view的dispatchTouchEvent中

```java
public boolean dispatchTouchEvent(MotionEvent event) {
	ListenerInfo li = mListenerInfo;
	if (li != null && li.mOnTouchListener != null
			&& (mViewFlags & ENABLED_MASK) == ENABLED
			&& li.mOnTouchListener.onTouch(this, event)) {
		result = true;
	}
	//如果上方得到了result=true（设置了setOnTouchListener并返回true），则onTouchEvent就会被屏蔽，也不会有click触发了
	if (!result && onTouchEvent(event)) {
		result = true;
	}
	return result;
}
```
6. 然后在最后的onTouchEvent中的up事件中会调用熟悉的OnClickListener

```java
public boolean onTouchEvent(MotionEvent event) {
	//如果view是可以点击的，这个方法就会一直返回true的
	switch (action) {
		case MotionEvent.ACTION_UP:
		if (mPerformClick == null) {
			mPerformClick = new PerformClick();
		}
		if (!post(mPerformClick)) {
			performClickInternal();
		}
	}
}
public boolean performClick() {
     final boolean result;
     final ListenerInfo li = mListenerInfo;
     if (li != null && li.mOnClickListener != null) {
         playSoundEffect(SoundEffectConstants.CLICK);
         li.mOnClickListener.onClick(this);
         result = true;
     } else {
         result = false;
     }
     return result;
}
```



总结：

- dispatchTouchEvent是分发点击事件的方法，首先会在DOWN事件或者TouchTarget不为空时去调用onInterceptTouchEvent方法来判断是否自己拦截这个事件。
- 若不拦截则会传递给子View，若子View也是ViewGroup则继续执行其dispatchTouchEvent进行分发。若没有子View接收或没有子View则会触发自己的View类中的dispatchTouchEvent方法，然后调用到onTouchEvent中去。
- onTouchEvent中在UP事件时会触发设置的click事件，设置click事件时，若View是不可点击的（CLCIKABLE = false），则会改为可点击。
- 设置onTouchListener在触发时返回true会屏蔽掉onTouchEvent的执行。
- 子View在Down事件时可以调用requestDisallowInterceptTouchEvent方法（ViewParent中的），设置为true，则父View不能再拦截事件。
- 若在后续事件中父View拦截掉了事件，则子View会受到一个cancel事件，并清空touchTarget