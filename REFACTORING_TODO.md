# Tinyspider 重构计划

本文件用于跟踪 tinyspider 项目重构为生产级框架的进度。

## ✅ 第一阶段: 结构重塑与核心模型建立

- [x] **项目结构**
    - [x] 创建新的包结构: `core`, `downloader`, `engine`, `exception`, `http`, `middleware`, `pipeline`, `scheduler`, `spider`.
- [x] **依赖管理**
    - [x] 从 `pom.xml` 中移除 `hutool-http` 依赖。
    - [x] 添加 `SLF4J` + `Logback` 作为新的日志框架。
- [x] **核心数据模型**
    - [x] 创建 `http.Request` 类。
    - [x] 创建 `http.Response` 类。
    - [x] 创建 `core.Item` 类。
- [x] **下载器 (Downloader)**
    - [x] 创建 `downloader.Downloader` 接口。
    - [x] 将 `DefaultPageDownloader` 重构为 `downloader.DefaultDownloader`，实现新接口，并处理 `Request` 和 `Response` 对象。

## ✅ 第二阶段: 引擎与调度器

- [x] **爬虫 (Spider)**
    - [x] 创建 `spider.Spider` 接口/基类，包含 `startRequests()` 和 `parse(Response response)` 方法。
- [x] **调度器 (Scheduler)**
    - [x] 创建 `scheduler.Scheduler` 接口。
    - [x] 创建 `scheduler.DuplicateRemover` 接口，用于可插拔的URL去重。
    - [x] 创建一个默认的内存调度器 `scheduler.DefaultScheduler`。
- [x] **引擎 (Engine)**
    - [x] 创建 `engine.Engine` 类。
    - [x] 在 `Engine` 中实现核心爬取循环。
    - [x] 在 `Engine` 中将 `Spider`, `Scheduler`, `Downloader` 组装起来。

## ✅ 第三阶段: 数据管道与中间件

- [x] **数据管道 (Item Pipeline)**
    - [x] 创建 `pipeline.ItemPipeline` 接口。
    - [x] 实现一个用于在控制台打印 Item 的 `ConsolePipeline`。
    - [x] 在 `Engine` 中集成 Pipeline 处理流程。
- [x] **下载中间件 (Downloader Middleware)**
    - [x] 创建 `middleware.DownloaderMiddleware` 接口。
    - [x] 在 `Engine` 的请求/响应流中集成中间件钩子。
    - [x] 创建一个 `DefaultHeadersMiddleware` 用于设置默认请求头。
    - [x] 创建一个 `RetryMiddleware` 用于处理失败的请求。

## ✅ 第四阶段: 配置与收尾

- [x] **配置系统**
    - [x] 创建 `core.Settings` 类来管理配置。
    - [x] 调整 `@Site` 和 `@Selector` 注解，使其与新的 `Spider` 和 `Settings` 协同工作。
- [x] **代码清理**
    - [x] 删除旧的、不再使用的类 (如 `Scheduler`, `PageProcessor` 等)。
    - [x] 更新 `main` 方法入口，使用新的 `Engine` 来启动爬虫。
- [x] **示例与文档**
    - [x] 编写一个使用新框架的 `Spider` 示例。
    - [x] 更新 `README.md`，介绍新的架构和使用方法。
- [x] **最终审查**
    - [x] 全面审查代码，确保其一致性、可读性和生产就绪状态。
