/*
Navicat MySQL Data Transfer

Source Server         : reliable
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2022-07-13 23:04:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ms_admin
-- ----------------------------
DROP TABLE IF EXISTS `ms_admin`;
CREATE TABLE `ms_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ms_admin
-- ----------------------------
INSERT INTO `ms_admin` VALUES ('1', 'admin', '$2a$10$/VksDKuMkOpQBawXfpdhaeQ3GObIhEaFrQzfl5SpUpUKt/P1OGW4i');
INSERT INTO `ms_admin` VALUES ('2', 'test', '$2a$10$/VksDKuMkOpQBawXfpdhaeQ3GObIhEaFrQzfl5SpUpUKt/P1OGW4i');

-- ----------------------------
-- Table structure for ms_admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `ms_admin_permission`;
CREATE TABLE `ms_admin_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ms_admin_permission
-- ----------------------------
INSERT INTO `ms_admin_permission` VALUES ('1', '1', '1');
INSERT INTO `ms_admin_permission` VALUES ('2', '2', '2');

-- ----------------------------
-- Table structure for ms_article
-- ----------------------------
DROP TABLE IF EXISTS `ms_article`;
CREATE TABLE `ms_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment_counts` int(11) DEFAULT NULL COMMENT '评论数量',
  `create_date` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '简介',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '标题',
  `view_counts` int(11) DEFAULT NULL COMMENT '浏览数量',
  `weight` int(11) NOT NULL COMMENT '是否置顶',
  `author_id` bigint(20) DEFAULT NULL COMMENT '作者id',
  `body_id` bigint(20) DEFAULT NULL COMMENT '内容id',
  `category_id` int(11) DEFAULT NULL COMMENT '类别id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1546324428613824514 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ms_article
-- ----------------------------
INSERT INTO `ms_article` VALUES ('1546324428613824513', '0', '1657507447756', '博客系统小结', '个人博客系统技术亮点', '7', '0', '1546323951893426177', '1546324428630601729', '2');

-- ----------------------------
-- Table structure for ms_article_body
-- ----------------------------
DROP TABLE IF EXISTS `ms_article_body`;
CREATE TABLE `ms_article_body` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `content_html` longtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `article_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `article_id` (`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1546324428630601730 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ms_article_body
-- ----------------------------
INSERT INTO `ms_article_body` VALUES ('1546324428630601729', '### 页面效果：\n\n主页面：\n\n![image-20220710202031794](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710202031794.png)\n\n![image-20220710202231419](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710202231419.png)\n\n分类标签等：\n\n![image-20220710202109911](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710202109911.png)\n\n归档：\n\n![image-20220710202122923](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710202122923.png)\n\n写文章：\n\n![image-20220710202202021](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710202202021.png)\n\n\n\n----------\n\n\n\n- ==jwt + redis 使用token令牌的登录==，访问认证速度快，实现session共享，安全性较高。 redis做了token令牌和用户信息的对应管理。1. 访问接口token验证，进一步增加了安全性 2. 登录用户做了缓存 3. 灵活控制用户的过期时间（可以续期，踢掉线等）\n\n> jwt 可以生成 一个==加密的token==，做为用户登录的令牌，当用户登录成功之后，发放给客户端。\n> 当请求需要登录的资源或者接口的时候，将token携带，后端验证token是否合法。\n>\n> 首先在登陆之前在redis数据库中对数据进行查询，看是否存在该条数据，==如果不存在的话，就去数据库查找，然后在查找到之后，在正常登录的时候将数据存储到redis中，当然这个存储信息的键值对也就是在redis查询的那个数据==，然后下次如果再次执行访问的时候，在redis中就有了此数据，进而提高了访问的效率。细节：存储用户的登录信息，存储在redis中的时候使用的是hash数据结构，【hash数据结构其实就是，对应的键值对的值是一个字典类型。】此时就可以将用户携带的唯一标识作为值的键，将用户的其他某个信息作为该键的值存储起来。\n>\n> \n>\n> ```java\n> private RedisTemplate<String, String> redisTemplate;\n> redisTemplate.opsForValue().set(\"TOKEN_\"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);\n> ```\n\n\n\n- ==ThreadLocal 本地线程变量==保存用户信息副本到每一个线程中【线程封闭，不会出现线程并发安全问题】。\n\n    在请求的线程之内，可以随时获取登录的用户，在使用完ThreadLocal之后，**做了对value的删除，防止了内存泄漏**。\n\n    【内存泄露】\n\n    > `ThreadLocalMap` 为 `ThreadLocal` 的一个静态内部类，里面定义了`Entry` 来保存数据。而且是继承的弱引用。在`Entry`内部使用`ThreadLocal`作为`key`，使用我们设置的`value`作为`value`。\n    >\n    > 如果 `key threadlocal` 为 `null` 了，这个 `entry` 就可以清除了。\n    >\n    > ThreadLocalMap中的key为ThreadLocal的弱引用，当key为`null`时，ThreadLocal会被当成垃圾回收 。\n    >\n    > ```java\n    > //首先获取当前线程对象\n    > Thread t = Thread.currentThread();\n    > //获取线程中变量 ThreadLocal.ThreadLocalMap\n    > ThreadLocalMap map = getMap(t);	//弱引用\n    > ```\n    >\n    > **虽然ThreadLocalMap的key没了，但是value还在，这就造成了内存泄漏。**\n\n![image-20220710175107187](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710175107187.png)\n\n\n\n- 线程安全 update table set value = [newValue] where id=[xx] and value=[oldValue]\n\n    - > **CAS**：CAS是Compare And Swap的简称，即：比较并交换。这是当前的处理器基本都支持的一种指令。每个CAS指令包括三个运算符，一个内存地址V，一个期望值A和一个新值B，CAS指令执行的时候是去判断这个地址V上的值和期望值A是否相等，相等则将地址V上的值修改为新值B，不等则不作任何操作。CAS操作实际实现是在一个循环中不断执行CAS指令，直到成功为止。\n\n    - 线程池**@Async** 对当前的主业务流程无影响的操作，放入线程池执行。\n\n        比如加载文章详情和文章阅读数更新，这两个业务流程需要分开执行，互不影响。\n\n\n\n- 文章发布 ==@Transactional 事务处理==。\n\n\n\n- 后台权限管理系统 **Spring Security**\n\n\n\n![image-20220710202726918](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710202726918.png)\n\n\n\n- 统一日志记录\n- 定义切点、实现切面\n\n![image-20220710203446152](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710203446152.png)\n\n- 统一缓存处理\n    - 定义切点、实现切面\n\n![image-20220710203703333](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710203703333.png)\n\n- 统一异常处理\n    - @ControllerAdvice 对所有带Controller注解的类实施异常拦截\n\n![image-20220710203759963](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710203759963.png)\n\n- 统一登录拦截\n    - 先写Handler拦截类，再到WebConfig类配置生效\n\n![image-20220710204028104](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710204028104.png)\n\n![image-20220710204301660](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710204301660.png)', '<h3><a id=\"_0\"></a>页面效果：</h3>\n<p>主页面：</p>\n<p><img src=\"https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710202031794.png\" alt=\"image-20220710202031794\" /></p>\n<p><img src=\"https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710202231419.png\" alt=\"image-20220710202231419\" /></p>\n<p>分类标签等：</p>\n<p><img src=\"https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710202109911.png\" alt=\"image-20220710202109911\" /></p>\n<p>归档：</p>\n<p><img src=\"https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710202122923.png\" alt=\"image-20220710202122923\" /></p>\n<p>写文章：</p>\n<p><img src=\"https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710202202021.png\" alt=\"image-20220710202202021\" /></p>\n<hr />\n<ul>\n<li><mark>jwt + redis 使用token令牌的登录</mark>，访问认证速度快，实现session共享，安全性较高。 redis做了token令牌和用户信息的对应管理。1. 访问接口token验证，进一步增加了安全性 2. 登录用户做了缓存 3. 灵活控制用户的过期时间（可以续期，踢掉线等）</li>\n</ul>\n<blockquote>\n<p>jwt 可以生成 一个<mark>加密的token</mark>，做为用户登录的令牌，当用户登录成功之后，发放给客户端。<br />\n当请求需要登录的资源或者接口的时候，将token携带，后端验证token是否合法。</p>\n<p>首先在登陆之前在redis数据库中对数据进行查询，看是否存在该条数据，<mark>如果不存在的话，就去数据库查找，然后在查找到之后，在正常登录的时候将数据存储到redis中，当然这个存储信息的键值对也就是在redis查询的那个数据</mark>，然后下次如果再次执行访问的时候，在redis中就有了此数据，进而提高了访问的效率。细节：存储用户的登录信息，存储在redis中的时候使用的是hash数据结构，【hash数据结构其实就是，对应的键值对的值是一个字典类型。】此时就可以将用户携带的唯一标识作为值的键，将用户的其他某个信息作为该键的值存储起来。</p>\n<pre><div class=\"hljs\"><code class=\"lang-java\"><span class=\"hljs-keyword\">private</span> RedisTemplate&lt;String, String&gt; redisTemplate;\nredisTemplate.opsForValue().set(<span class=\"hljs-string\">\"TOKEN_\"</span>+token, JSON.toJSONString(sysUser),<span class=\"hljs-number\">1</span>, TimeUnit.DAYS);\n</code></div></pre>\n</blockquote>\n<ul>\n<li>\n<p><mark>ThreadLocal 本地线程变量</mark>保存用户信息副本到每一个线程中【线程封闭，不会出现线程并发安全问题】。</p>\n<p>在请求的线程之内，可以随时获取登录的用户，在使用完ThreadLocal之后，<strong>做了对value的删除，防止了内存泄漏</strong>。</p>\n<p>【内存泄露】</p>\n<blockquote>\n<p><code>ThreadLocalMap</code> 为 <code>ThreadLocal</code> 的一个静态内部类，里面定义了<code>Entry</code> 来保存数据。而且是继承的弱引用。在<code>Entry</code>内部使用<code>ThreadLocal</code>作为<code>key</code>，使用我们设置的<code>value</code>作为<code>value</code>。</p>\n<p>如果 <code>key threadlocal</code> 为 <code>null</code> 了，这个 <code>entry</code> 就可以清除了。</p>\n<p>ThreadLocalMap中的key为ThreadLocal的弱引用，当key为<code>null</code>时，ThreadLocal会被当成垃圾回收 。</p>\n<pre><div class=\"hljs\"><code class=\"lang-java\"><span class=\"hljs-comment\">//首先获取当前线程对象</span>\nThread t = Thread.currentThread();\n<span class=\"hljs-comment\">//获取线程中变量 ThreadLocal.ThreadLocalMap</span>\nThreadLocalMap map = getMap(t);	<span class=\"hljs-comment\">//弱引用</span>\n</code></div></pre>\n<p><strong>虽然ThreadLocalMap的key没了，但是value还在，这就造成了内存泄漏。</strong></p>\n</blockquote>\n</li>\n</ul>\n<p><img src=\"https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710175107187.png\" alt=\"image-20220710175107187\" /></p>\n<ul>\n<li>\n<p>线程安全 update table set value = [newValue] where id=[xx] and value=[oldValue]</p>\n<ul>\n<li>\n<blockquote>\n<p><strong>CAS</strong>：CAS是Compare And Swap的简称，即：比较并交换。这是当前的处理器基本都支持的一种指令。每个CAS指令包括三个运算符，一个内存地址V，一个期望值A和一个新值B，CAS指令执行的时候是去判断这个地址V上的值和期望值A是否相等，相等则将地址V上的值修改为新值B，不等则不作任何操作。CAS操作实际实现是在一个循环中不断执行CAS指令，直到成功为止。</p>\n</blockquote>\n</li>\n<li>\n<p>线程池**@Async** 对当前的主业务流程无影响的操作，放入线程池执行。</p>\n<p>比如加载文章详情和文章阅读数更新，这两个业务流程需要分开执行，互不影响。</p>\n</li>\n</ul>\n</li>\n<li>\n<p>文章发布 <mark>@Transactional 事务处理</mark>。</p>\n</li>\n<li>\n<p>后台权限管理系统 <strong>Spring Security</strong></p>\n</li>\n</ul>\n<p><img src=\"https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710202726918.png\" alt=\"image-20220710202726918\" /></p>\n<ul>\n<li>统一日志记录</li>\n<li>定义切点、实现切面</li>\n</ul>\n<p><img src=\"https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710203446152.png\" alt=\"image-20220710203446152\" /></p>\n<ul>\n<li>统一缓存处理\n<ul>\n<li>定义切点、实现切面</li>\n</ul>\n</li>\n</ul>\n<p><img src=\"https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710203703333.png\" alt=\"image-20220710203703333\" /></p>\n<ul>\n<li>统一异常处理\n<ul>\n<li>@ControllerAdvice 对所有带Controller注解的类实施异常拦截</li>\n</ul>\n</li>\n</ul>\n<p><img src=\"https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710203759963.png\" alt=\"image-20220710203759963\" /></p>\n<ul>\n<li>统一登录拦截\n<ul>\n<li>先写Handler拦截类，再到WebConfig类配置生效</li>\n</ul>\n</li>\n</ul>\n<p><img src=\"https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710204028104.png\" alt=\"image-20220710204028104\" /></p>\n<p><img src=\"https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220710204301660.png\" alt=\"image-20220710204301660\" /></p>\n', '1546324428613824513');

-- ----------------------------
-- Table structure for ms_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `ms_article_tag`;
CREATE TABLE `ms_article_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `article_id` (`article_id`) USING BTREE,
  KEY `tag_id` (`tag_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1546324428622213122 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ms_article_tag
-- ----------------------------
INSERT INTO `ms_article_tag` VALUES ('1546324428622213121', '1546324428613824513', '5');

-- ----------------------------
-- Table structure for ms_category
-- ----------------------------
DROP TABLE IF EXISTS `ms_category`;
CREATE TABLE `ms_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ms_category
-- ----------------------------
INSERT INTO `ms_category` VALUES ('1', '/static/category/front.png', '前端', '前端是什么，大前端');
INSERT INTO `ms_category` VALUES ('2', '/static/category/back.png', '后端', '后端最牛叉');
INSERT INTO `ms_category` VALUES ('3', '/static/category/lift.jpg', '生活', '生活趣事');
INSERT INTO `ms_category` VALUES ('4', '/static/category/database.png', '数据库', '没数据库，啥也不管用');
INSERT INTO `ms_category` VALUES ('5', '/static/category/language.png', '编程语言', '好多语言，该学哪个？');

-- ----------------------------
-- Table structure for ms_comment
-- ----------------------------
DROP TABLE IF EXISTS `ms_comment`;
CREATE TABLE `ms_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_date` bigint(20) NOT NULL,
  `article_id` int(11) NOT NULL,
  `author_id` bigint(20) NOT NULL,
  `parent_id` bigint(20) NOT NULL,
  `to_uid` bigint(20) NOT NULL,
  `level` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `article_id` (`article_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ms_comment
-- ----------------------------

-- ----------------------------
-- Table structure for ms_permission
-- ----------------------------
DROP TABLE IF EXISTS `ms_permission`;
CREATE TABLE `ms_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ms_permission
-- ----------------------------
INSERT INTO `ms_permission` VALUES ('1', 'xx', '/admin/permission/permissionList', '主页');
INSERT INTO `ms_permission` VALUES ('2', 'xxx', '222', '222');

-- ----------------------------
-- Table structure for ms_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `ms_sys_log`;
CREATE TABLE `ms_sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` bigint(20) DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `module` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `nickname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `operation` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `time` bigint(20) DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ms_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for ms_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `ms_sys_user`;
CREATE TABLE `ms_sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '账号',
  `admin` bit(1) DEFAULT NULL COMMENT '是否管理员',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `create_date` bigint(20) DEFAULT NULL COMMENT '注册时间',
  `deleted` bit(1) DEFAULT NULL COMMENT '是否删除',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `last_login` bigint(20) DEFAULT NULL COMMENT '最后登录时间',
  `mobile_phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '加密盐',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1546323951893426178 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ms_sys_user
-- ----------------------------
INSERT INTO `ms_sys_user` VALUES ('1546323951893426177', 'ycw', '', '/static/img/yang.jpg', '1657507334096', '\0', '', '1657507334096', null, '测试昵称', '9f2bc4480ffeb0ca67f33e899ae37c53', '', '');

-- ----------------------------
-- Table structure for ms_tag
-- ----------------------------
DROP TABLE IF EXISTS `ms_tag`;
CREATE TABLE `ms_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ms_tag
-- ----------------------------
INSERT INTO `ms_tag` VALUES ('5', '/static/tag/java.png', 'springboot');
INSERT INTO `ms_tag` VALUES ('6', '/static/tag/java.png', 'spring');
INSERT INTO `ms_tag` VALUES ('7', '/static/tag/java.png', 'springmvc');
INSERT INTO `ms_tag` VALUES ('8', '/static/tag/css.png', '11');
