## Serializable接口
因为序列化和反序列化都需要大量的IO操作，开销大，适用于将对象序列化到存储设备中或者将对象序列化后通过网络传输
```java
public class Test implements Serializable {

    //辅助序列化，严重是否为相同的类，防止序列化失败
    private static final long serialVersionUID = 1234567890;

    //添加了transient关键字的和静态变量都不会参与序列化
    transient String name = "jiangker";
    
    /**
     * 自定义序列化规则
     */
    private void writeObject(ObjectOutputStream s) throws IOException{ }
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException { }
}
```
## Parcelable
使用麻烦，效率高，适合内存序列化
```java
//其中的对象都需要是可以进行序列化的
public class Test implements Parcelable {

    private String name;
    private int age;

    public Test(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //从序列化后的对象中创建原始对象
    protected Test(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    /**
     * 将当前对象写入序列化对象中
     * @flags 0 或 1 ，一般写0
     * 1表示当前对象需要作为返回值返回，不能立即释放资源
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    //返回当前文件的内容描述，若含有文件描述符则返回1，一般为0
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        //从序列化后的对象中创建原始对象
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        //创建指定长度的原始对象数组
        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };
}
```