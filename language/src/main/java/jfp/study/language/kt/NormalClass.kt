package jfp.study.language.kt

class NormalClass(
    val str1: String,
    var str2: String?,
    var int1: Int,
    var int2: Int?,
    val list: List<Int>
) {
    var str3: String = ""
        set(value) {
            field = value
        }
        get() = "field"

    /**
     * public final String getStr4() {
     *      return "field";
     * }
     */
    var str4: String = ""
        get() {
            return "field"
        }

    /**
     *  public final void testFun1() {
     *      this.setStr3("this is fun");
     *      this.setStr3("this is fun");
     *  }
     */
    fun testFun1() {
        str3 = "this is fun"
        this.str3 = "this is fun"
    }
}