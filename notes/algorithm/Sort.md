#### 算法分类

十种常见排序算法可以分为两大类：

- **比较类排序**：通过比较来决定元素间的相对次序，由于其时间复杂度不能突破O(nlogn)，因此也称为非线性时间比较类排序。
- **非比较类排序**：不通过比较来决定元素间的相对次序，它可以突破基于比较排序的时间下界，以线性时间运行，因此也称为线性时间非比较类排序。
  ![image](../../images/algorithm/sort.png) 排序算法一般都会有几个较为基础通用的方法

```kotlin
class Sort {
    private fun check(array: Array<Int>, a: Int, b: Int) = array[a] > array[b]

    private fun swap(array: Array<Int>, a: Int, b: Int) {
        val max = array[a]
        array[a] = array[b]
        array[b] = max
    }
}
```

#### 算法复杂度

| 排序算法 | 时间复杂度(平均) | 时间复杂度（最坏） | 时间复杂度（最好） | 空间复杂度 | 稳定性 |
| -------- | ---------------- | ------------------ | ------------------ | ---------- | ------ |
| 插入排序 | O(n²)            | O(n²)              | O(n)               | O(1)       | 稳定   |
| 希尔排序 | O(n²)            | O(n²)              | O(n)               | O(1)       | 不稳定 |
| 选择排序 | O(n²)            | O(n²)              | O(n²)              | O(1)       | 不稳定 |
| 堆排序   | O(nlogn)         | O(nlogn)           | O(nlogn)           | O(1)       | 不稳定 |
| 冒泡排序 | O(n²)            | O(n²)              | O(n)               | O(1)       | 稳定   |
| 快速排序 | O(nlogn)         | O(n²)              | O(nlogn)           | O(nlogn)   | 不稳定 |
| 归并排序 | O(nlogn)         | O(nlogn)           | O(nlogn)           | O(n)       | 稳定   |
| 计数排序 | O(n+k)           | O(n+k)             | O(n+k)             | O(n+k)     | 稳定   |
| 桶排序   | O(n+k)           | O(n²)              | O(n)               | O(n+k)     | 稳定   |
| 基数排序 | O(n*k)           | O(n*k)             | O(n*k)             | O(n+k)     | 稳定   |

之前是用泛型来做的，后考虑学习算法主要的是理解逻辑，所以使用简单的int简单而且更直观

#### 1 冒泡排序

冒泡排序是一种简单的排序算法。它重复地走访过要排序的数列，一次比较两个元素，如果它们的顺序错误就把它们交换过来。走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。这个算法的名字由来是因为越小的元素会经由交换慢慢“浮”到数列的顶端。

##### 1.1 算法描述

1. 比较相邻的元素。如果第一个比第二个大，就交换它们两个；
2. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
3. 针对所有的元素重复以上的步骤，除了最后一个；
4. 重复步骤1~3，直到排序完成。

##### 1.2 动图演示

![image](../../images/algorithm/bubbleSort.gif)

##### 1.3 代码演示

```kotlin
fun bubbleSort(array: Array<Int>) {
    val size = array.size
    //i 表示排序完成了多少个，以此遍历，最后一个为排好的
    for (i in 0 until size - 1) {
        //从第一个元素开始向后遍历，比较次数为个数减一
        for (r in 0 until size - 1 - i) {
            if (array[r] > array[r + 1]) {
                val max = array[r]
                array[r] = array[r+1]
                array[r+1] = max
            }
        }
    }
}
```

#### 2 选择排序

选择排序(Selection-sort)是一种简单直观的排序算法。它的工作原理：首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。

##### 2.1 算法描述

n个记录的直接选择排序可经过n-1趟直接选择排序得到有序结果。具体算法描述如下：

1. 初始状态：无序区为R[1..n]，有序区为空；
2. 第i趟排序(i=1,2,3…n-1)开始时，当前有序区和无序区分别为R[1..i-1]和R(i..n）。该趟排序从当前无序区中-选出关键字最小的记录R[k]，将它与无序区的第1个记录R交换，使R[1..i]和R[i+1..n)分别变为记录个数增加1个的新有序区和记录个数减少1个的新无序区；
3. n-1趟结束，数组有序化了。

##### 2.2 动图演示

![image](../../images/algorithm/selectionSort.gif)

##### 2.3 代码演示

```kotlin
fun selectionSort(array: IntArray) {
    for (i in array.indices) {
        //最小值的序号
        var min = i
        //遍历从当前元素开始的元素，从新设置最小序号
        for (r in i + 1 until array.size) {
            if (array[min] > array[r]) {
                min = r
            }
        }
        //交换选中的最小值
        if (min != i){
            val value = array[min]
            array[min] = array[i]
            array[i] = value
        }
    }
}
```

##### 2.4 算法分析

表现最稳定的排序算法之一，因为无论什么数据进去都是O(n2)的时间复杂度，所以用到它的时候，数据规模越小越好。唯一的好处可能就是不占用额外的内存空间了吧。理论上讲，选择排序可能也是平时排序一般人想到的最多的排序方法了吧。

#### 3 插入排序（Insertion Sort）

插入排序（Insertion-Sort）的算法描述是一种简单直观的排序算法。它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。

##### 3.1 算法描述

一般来说，插入排序都采用in-place在数组上实现。具体算法描述如下：

1. 从第一个元素开始，该元素可以认为已经被排序；
2. 取出下一个元素，在已经排序的元素序列中从后向前扫描；
3. 如果该元素（已排序）大于新元素，将该元素移到下一位置；
4. 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
5. 将新元素插入到该位置后；
6. 重复步骤2~5。、

##### 3.2 动图演示

![image](../../images/algorithm/insertSort.gif)

##### 3.3 代码实现

```kotlin
fun insertSort(array: IntArray) {
    for (i in 1 until array.size) {
        //要插入的值
        val value = array[i]
        //插入到哪里
        var index = i
        while (index > 0 && array[index - 1] > value) {
            array[index] = array[index - 1]
            index--
        }
        array[index] = value
    }
}
```

##### 3.4 算法分析

插入排序在实现上，通常采用in-place排序（即只需用到O(1)的额外空间的排序），因而在从后向前扫描过程中，需要反复把已排序元素逐步向后挪位，为最新元素提供插入空间。

#### 4、希尔排序（Shell Sort）

1959年Shell发明，第一个突破O(n2)的排序算法，是简单插入排序的改进版。它与插入排序的不同之处在于，它会优先比较距离较远的元素。希尔排序又叫缩小增量排序。

##### 4.1 算法描述

先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序，具体算法描述：

1. 选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；
2. 按增量序列个数k，对序列进行k 趟排序；
3. 每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。

##### 4.2 动图演示

![image](../../images/algorithm/shellSort.gif)

##### 4.3 代码示例

```kotlin
fun shellSort(array: IntArray) {
    /**
     * 使数组中任意间隔为h的位置都是有序的
     * 相当于h个独立的间隔为h的数组
     */
    var h = 0
    val size = array.size
    while (h < size / 3) h = 3 * h + 1 //1、4、13、40、121、364...
    //直到进行间隔h为1的插入排序
    while (h >= 1) {
        for (i in h until size){
            val value = array[i]
            var index = i
            //从h位置开始，以此往前进行间隔为h的插入排序
            while (index >= h && array[index - h] > value){
                array[index] = array[index - h]
                index -= h
            }
            array[index] = value
        }
        h /= 3
    }
}
```

##### 4.4 算法分析

希尔排序的核心在于间隔序列的设定。既可以提前设定好间隔序列，也可以动态的定义间隔序列。动态定义间隔序列的算法是《算法（第4版）》的合著者Robert Sedgewick提出的。

#### 5、归并排序（Merge Sort）

归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。

##### 5.1 算法描述

1. 把长度为n的输入序列分成两个长度为n/2的子序列；
2. 对这两个子序列分别采用归并排序；
3. 将两个排序好的子序列合并成一个最终的排序序列。

##### 5.2 动图演示

![image](../../images/algorithm/mergeSort.gif)

##### 5.3代码实现

```kotlin
fun mergeSort(array: IntArray) {
    val temp = IntArray(array.size)
    divide(array, temp, 0, array.size - 1)
}

private fun divide(array: IntArray, temp: IntArray, left: Int, right: Int) {
    if (left >= right) return
    val mid = left + (right - left) / 2
    divide(array, temp, left, mid)
    divide(array, temp, mid + 1, right)
    //如果左边最大元素大于右边最小元素才需要合并
    if (array[mid] > array[mid + 1])
        merge(array, temp, left, mid, right)
}

private fun merge(array: IntArray, temp: IntArray, start: Int, mid: Int, end: Int) {
    var left = start
    var right = mid + 1
    //把需要归并的那段数据复制到临时数组中    
    System.arraycopy(array, start, temp, start, end - start + 1)
    //归并回原数组
    for (i in start..end) {
        if (left > mid) break
        else if (right > end) array[i] = temp[left++]
        else if (temp[right] < temp[left]) array[i] = temp[right++]
        else array[i] = temp[left++]
    }
}
```

##### 5.4 算法分析

归并排序是一种稳定的排序方法。和选择排序一样，归并排序的性能不受输入数据的影响，但表现比选择排序好的多，因为始终都是O(nlogn）的时间复杂度。代价是需要额外的内存空间。

#### 6、快速排序（Quick Sort）

快速排序的基本思想：通过一趟排序将待排记录分隔成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，则可分别对这两部分记录继续进行排序，以达到整个序列有序。

##### 6.1 算法描述

快速排序使用分治法来把一个串（list）分为两个子串（sub-lists）。具体算法描述如下：

1. 从数列中挑出一个元素，称为 “基准”（pivot）；
2. 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
3. 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。

##### 6.2 动图演示

![image](../../images/algorithm/quickSort.gif)

##### 6.3 代码实现

```kotlin
fun quickSort(array: IntArray) {
    quickSort(array, 0, array.size - 1)
}

private fun quickSort(array: IntArray, start: Int, end: Int) {
    if (start >= end) return
    val mid = partition(array, start, end)
    quickSort(array, start, mid - 1)
    quickSort(array, mid + 1, end)
}

/**
 * 分为小于以及大于等于  不足：当重复元素多时，计算速度退化明显
 * mid 表示此处以及左边的都小于标定值的坐标
 * 当i处值小于标定值时，把mid处元素和i处交换
 * 其余时候直接增加i值即可，相当于把大于和等于的放置在右边
 */
private fun partition(array: IntArray, start: Int, end: Int): Int {
    var mid = start
    for (i in start + 1..end) {
        if (array[start] > array[i]) {
            mid++
            val value = array[mid]
            array[mid] = array[i]
            array[i] = value
        }
    }
    val value = array[mid]
    array[mid] = array[start]
    array[start] = value
    return mid
}
```

#### 7、堆排序（Heap Sort）

堆排序（Heapsort）是指利用堆这种数据结构所设计的一种排序算法。堆积是一个近似完全二叉树的结构，并同时满足堆积的性质：即子结点的键值或索引总是小于（或者大于）它的父节点。

##### 7.1 算法描述

1. 将初始待排序关键字序列(R1,R2….Rn)构建成大顶堆，此堆为初始的无序区；
2. 将堆顶元素R[1]与最后一个元素R[n]交换，此时得到新的无序区(R1,R2,……Rn-1)和新的有序区(Rn),且满足R[1,2…n-1]<=R[n]；
3. 由于交换后新的堆顶R[1]可能违反堆的性质，因此需要对当前无序区(R1,R2,……Rn-1)调整为新堆，然后再次将R[1]与无序区最后一个元素交换，得到新的无序区(R1,R2….Rn-2)和新的有序区(Rn-1,Rn)。不断重复此过程直到有序区的元素个数为n-1，则整个排序过程完成。

##### 7.2 动图演示

![image](../../images/algorithm/heapSort.gif)

##### 7.3代码实现

```kotlin
/**
 * 因为是从小到大排序，所以先排为最大堆，然后取出最大元素放置到末尾即可
 */
fun heapSort(array: IntArray) {
    //从第一个元素开始以此往后进行最大堆的创建
    for (index in array.indices) {
        siftUp(array, index)
    }
    //把最大元素依次与末尾元素进行交换，然后进行下沉操作
    for (index in array.size - 1 downTo 1) {
        heapify(array, index)
    }
}

private fun siftUp(array: IntArray, index: Int) {
    var i = index
    while (i > 0) {
        val pre = if (i % 2 == 0) i / 2 - 1 else i / 2
        if (array[pre] < array[i]) {
            swap(array, pre, i)
            i = pre
        } else {
            break
        }
    }
}

/**
 * 将index的元素和第一个位置元素交换，然后进行下沉操作
 */
private fun heapify(array: IntArray, index: Int) {
    swap(array, 0, index)
    var i = 0
    val end = index - 1
    //如果有子元素则进行下沉操作
    while (2 * i < end) {
        val right = 2 * (i + 1)
        val left = right - 1
        //如果有两个子元素，则用最大的去交换，如果只有一个子元素，则比较后结束
        if (right < end) {
            val maxIndex = if (array[right] > array[left]) right else left
            if (array[maxIndex] > array[i]) {
                swap(array, maxIndex, i)
                i = maxIndex
            } else {
                break
            }
        } else {
            if (array[left] > array[i]) {
                swap(array, left, i)
            }
            break
        }
    }
}

private fun swap(array: IntArray, a: Int, b: Int) {
    val value = array[a]
    array[a] = array[b]
    array[b] = value
}
```

#### 8、计数排序（Counting Sort）

计数排序不是基于比较的排序算法，其核心在于将输入的数据值转化为键存储在额外开辟的数组空间中。 作为一种线性时间复杂度的排序，计数排序要求输入的数据必须是有确定范围的整数。

##### 8.1 算法描述

1. 找出待排序的数组中最大和最小的元素；
2. 统计数组中每个值为i的元素出现的次数，存入数组C的第i项；
3. 对所有的计数累加（从C中的第一个元素开始，每一项和前一项相加）；
4. 反向填充目标数组：将每个元素i放在新数组的第C(i)项，每放一个元素就将C(i)减去1。

##### 8.2 动图演示

![image](../../images/algorithm/countingSort.gif)

##### 8.3 代码实现

```kotlin
//TODO
```

##### 8.4 算法分析

计数排序是一个稳定的排序算法。当输入的元素是 n 个 0到 k 之间的整数时，时间复杂度是O(n+k)，空间复杂度也是O(n+k)，其排序速度快于任何比较排序算法。当k不是很大并且序列比较集中时，计数排序是一个很有效的排序算法。

#### 9、桶排序（Bucket Sort）

桶排序是计数排序的升级版。它利用了函数的映射关系，高效与否的关键就在于这个映射函数的确定。桶排序 (Bucket sort)的工作的原理：假设输入数据服从均匀分布，将数据分到有限数量的桶里，每个桶再分别排序（有可能再使用别的排序算法或是以递归方式继续使用桶排序进行排）。

##### 9.1 算法描述

1. 设置一个定量的数组当作空桶；
2. 遍历输入数据，并且把数据一个一个放到对应的桶里去；
3. 对每个不是空的桶进行排序；
4. 从不是空的桶里把排好序的数据拼接起来。

##### 9.2 图片演示

![image](../../images/algorithm/bucketSort.png)

##### 9.3 代码实现

```kotlin
//TODO
```

##### 9.4 算法分析

桶排序最好情况下使用线性时间O(n)，桶排序的时间复杂度，取决与对各个桶之间数据进行排序的时间复杂度，因为其它部分的时间复杂度都为O(n)。很显然，桶划分的越小，各个桶之间的数据越少，排序所用的时间也会越少。但相应的空间消耗就会增大。

#### 10、基数排序（Radix Sort）

基数排序是按照低位先排序，然后收集；再按照高位排序，然后再收集；依次类推，直到最高位。有时候有些属性是有优先级顺序的，先按低优先级排序，再按高优先级排序。最后的次序就是高优先级高的在前，高优先级相同的低优先级高的在前。

##### 10.1 算法描述

1. 取得数组中的最大数，并取得位数；
2. arr为原始数组，从最低位开始取每个位组成radix数组；
3. 对radix进行计数排序（利用计数排序适用于小范围数的特点）；

##### 10.2 动图演示

![image](../../images/algorithm/radixSort.gif)

##### 10.3 代码实现

```kotlin
//TODO
```

##### 10.4 算法分析

基数排序基于分别排序，分别收集，所以是稳定的。但基数排序的性能比桶排序要略差，每一次关键字的桶分配都需要O(n)的时间复杂度，而且分配之后得到新的关键字序列又需要O(n)的时间复杂度。假如待排数据可以分为d个关键字，则基数排序的时间复杂度将是O(d*2n) ，当然d要远远小于n，因此基本上还是线性级别的。

基数排序的空间复杂度为O(n+k)，其中k为桶的数量。一般来说n>>k，因此额外空间需要大概n个左右。
