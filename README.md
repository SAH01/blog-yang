# blog

#### 介绍
个人博客

#### 软件架构
软件架构说明


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

==技术点：==
1. jwt + redis
  使用token令牌的登录方式，访问认证速度快，实现session共享，安全性较高
  redis做了token令牌和用户信息的对应管理。1. 进一步增加了安全性 2. 登录用户做了缓存 3. 灵活控制用户的过期时间（可以续期，踢掉线等）
2. ThreadLocal 使用了保存用户信息，请求的线程之内，可以随时获取登录的用户，做了线程隔离
  在使用完ThreadLocal之后，做了value的删除，防止了内存泄漏
3. 线程安全 update table set value = newValue where id=1 and value=oldValue
4. 线程池 对当前的主业务流程无影响的操作，放入线程池执行
5. 后台权限管理系统 Spring Security
6. 统一日志记录
7. 统一缓存处理
8. 统一异常处理
9. 统一登录拦截
