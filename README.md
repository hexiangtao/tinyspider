# smallspider 
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
```
    java -jar  spider.jar  www.baidu.com
```    
### 2:包含多个参数启动
````
java -jar  spider.jar   www.baidu.com  1000  D:/urls.md
````

### 启动参数说明
1. wwww.baidu.com  要爬取的网站,如上：
2. 1000            开启的线程数(可选，默认200)
3. D:/url.text     内容存放路径(默认不存储) 


交流群:580889921

#### [直接下载jar包体验(文件大小:397kb)](https://github.com/enohe/smallspider/blob/master/smallspider/src/main/resources/assert/spider-0.0.1-SNAPSHOT-shaded.jar)

#### 代码示例图
![demo](https://github.com/enohe/smallspider/blob/master/smallspider/src/main/resources/assert/code.png)




##### 运行示例图:
![demo](https://github.com/enohe/smallspider/blob/master/smallspider/src/main/resources/assert/20180315144556.png)


