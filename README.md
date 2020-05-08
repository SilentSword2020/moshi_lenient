# moshi lenient

 * 解析JSON 结构数据，容忍一些常见的不规范的格式。
 * 允许：{} <- [] / ""，[] <- "", Integer <- "", Float <- ""

## 实现方案(两种方案)

1. 通过分析JsonDataException的message格式进行分别处理

* 优点：代码少（只有一个类），只一个类，使用方便

* 缺点：如果JsonDataException的message内容有变动，需要调整相关的逻辑


2. 根据不同的数据类型自定义对应的JsonAdapter(主要参考源码中对应类型的内置JsonAdapter)

* 优点：使用方便

* 缺点：代码多（添加的自定义JsonAdapter类比较多），<br>后面Moshi相关的JsonAdapter源码代码逻辑调整（需要重新修改自定义JsonAdapter）;
性能方面：不管json是不是正常，都会先检查再处理，有点性能损失问题，不过应该不大（主要是相关流的拷贝）

可以考虑使用Aspectj来对Moshi中的JsonAdapter.fromJson方法进行切面编程


## 参考资料

[Moshi项目]("https://github.com/square/moshi") https://github.com/square/moshi

[新一代Json解析库Moshi使用及原理解析]("https://blog.csdn.net/cpongo4/article/details/86613947") https://blog.csdn.net/cpongo4/article/details/86613947
