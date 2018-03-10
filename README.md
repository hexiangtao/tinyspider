# smallspider 
几十行代码实现 一个简短的爬虫小程序 

### 共6个文件
```java 
DefaultDocumentListener.java
DocumentListener.java
LinkCollection.java
Logger.java
PageCrawer.java
PageCrawRunnable.java
```



##  启动方式 
1: 只带一个参数启动 
    java -jar  spider.jar  www.baidu.com
    
2:多个参数启动
java -jar  spider.jar   www.baidu.com  1000  urls.md


### 启动参数说明
1:要爬取的网站,如上：wwww.baidu.com
2:开启的线程数(可选，默认200),如上:1000
3:内容存放路径(默认不存储) 



#### [可以直接下载jar包体验](www.baidu.com)
