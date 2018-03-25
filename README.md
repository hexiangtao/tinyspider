# tinyspider 
几十行代码实现 一个简单的爬虫程序 



### 核心文件共4个文件
```
Downloader.java
LinkStorage.java
PageProcessor.java
Spider.java
```



##  启动方式 
### 1: 只包含一个参数启动

 >   java -jar  tinyspider.jar  www.baidu.com    

### 2:包含多个参数启动

> java -jar tinyspider.jar www.dbmeinv.com  100  img[src]  www.dbmeinv.com 1 C:/test


### 启动参数说明
 1. 参数0(www.dbmeinv.com)            要爬取的网站 
 2. 参数1(100)                        开始的线程数
 3. 参数2(img)                        要提取的内容 
 4. 参数3(www.dbmeinv.com)            url包含的内容
 5. 参数4(1)                          日志打印级别
 6. 参数5(C:/test)                     提取的内容保存目录

交流群:580889921

#### [直接下载jar包(文件大小:397kb)](https://github.com/enohe/tinyspider/blob/master/tinyspider/src/main/resources/assert/tinyspider.jar)

#### 代码示例图
![demo](https://github.com/enohe/tinyspider/blob/master/tinyspider/src/main/resources/assert/code.png)




##### 运行示例图:
![demo](https://github.com/enohe/tinyspider/blob/master/tinyspider/src/main/resources/assert/console.png)


