# moshi lenient

 * 解析JSON 结构数据，容忍一些常见的不规范的格式。
 * 允许：{} <- [] / ""，[] <- "", Integer <- "", Float <- ""

## 实现方案(两种方案)

1.通过分析JsonDataException的message格式进行分别处理

2.根据不同的数据类型自定义对应的JsonAdapter(主要参考源码中对应类型的内置JsonAdapter)

