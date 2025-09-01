# xfg-frame-archetype - DDD 脚手架 - @小傅哥 v2.2

- docker 使用文档：[https://bugstack.cn/md/road-map/docker.html](https://bugstack.cn/md/road-map/docker.html)
- DDD 教程；
  - [DDD 概念理论](https://bugstack.cn/md/road-map/ddd-guide-01.html)
  - [DDD 建模方法](https://bugstack.cn/md/road-map/ddd-guide-02.html)
  - [DDD 工程模型](https://bugstack.cn/md/road-map/ddd-guide-03.html)
  - [DDD 架构设计](https://bugstack.cn/md/road-map/ddd.html)
  - [DDD 建模案例](https://bugstack.cn/md/road-map/ddd-model.html)




- api：
 - 放置接口定义，如 DTO、VO、API 请求/响应对象，通常用于服务间通信或对外暴露的接口规范。
- app：
 - 应用层，负责业务用例编排，调用领域服务，处理事务，通常包含 Service、ApplicationService 等。
- domain：
 - 领域层，核心业务逻辑和领域模型，如实体、值对象、领域服务、聚合根等。
- infrastructure：
 - 基础设施层，技术实现细节，如数据库访问、消息中间件、第三方服务集成、Repository 实现等。
- trigger：
 - 触发器层，负责接收外部请求，如 Controller（HTTP）、消息监听器、定时任务等，作为系统的入口。
- types：
 - 类型定义，通常放置通用类型、枚举、常量等，供各层复用。