## 1. 常用命令

| 命令 | 作用                                     |
| ---- | ---------------------------------------- |
| dd   | 删除（剪切）光标所在行                   |
| 5dd  | 删除（剪切）从光标处开始的5行            |
| yy   | 复制光标所在的整行                       |
| 5yy  | 复制光标处开始的5行                      |
| n    | 显示搜索命令定位到的下一个字符串         |
| N    | 显示搜索命令定位到的上一个字符串         |
| u    | 撤销上一步的操作                         |
| p    | 将之前删除的或复制过的数据粘贴在光标后面 |

## 2. 末行模式中可用的命令

| 命令          | 作用                                 |
| ------------- | ------------------------------------ |
| :w            | 保存                                 |
| :q            | 退出                                 |
| :wq           | 保存退出                             |
| :wq!          | 强制保存退出                         |
| :q!           | 强制退出（舍弃修改）                 |
| :set nu       | 显示行号                             |
| :set nonu     | 不显示行号                           |
| :命令         | 执行该命令                           |
| :n            | 跳转到第n行                          |
| :s/one/two    | 将当前光标所在行的第一个one替换成two |
| :s/one/two/g  | 将光标所在行的所有one替换成two       |
| :%s/one/two/g | 将全文中所有one替换成two             |
| ?jiangker     | 在文本中从下至上搜索jiangker         |
| /jiangker     | 在文本中从上至下搜索jiangker         |

## 3.模式切换

#### 3.1 命令模式 ==> 输入模式

| 命令 | 作用                                   |
| ---- | -------------------------------------- |
| a    | append - 光标后一位切换到输入模式      |
| A    | 在行尾切换到输入模式                   |
| i    | insert - 在当前位置切换到输入模式      |
| I    | 在行首切换到输入模式                   |
| o    | 在光标下一行创建一个空行切换到输入模式 |
| O    | 在光标上一行创建一个空行切换到输入模式 |

