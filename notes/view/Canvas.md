- cavas可以对画布进行绘制，最终会绘制到bitmap上
- cavas的来源可以时onDraw的参数，也可以是来源于bitmap，绘制是绘制到对应的bitmap上。
- 可以调用save或saveLayer来对当前的画布状态进行保存并在当前状态基础上生成一个新的画布，生成的画布最好根据需要指定大小，否则比较浪费。对应的回退可以使用restore或者restoreToCount来回退，类似于栈的结构。

- cavas也可以调用自己的裁剪方法来裁剪画布。
- cavas还可以使用旋转缩放平移等方法来改变画布的位置。

