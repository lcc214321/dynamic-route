## dynamic-route
通用动态理由服务，基于**spring cloud zuul**实现  
[博客地址](https://my.oschina.net/u/2402525/blog/1581210)

### 路径表达式
**Ant风格**  
? : 匹配任意单字符  
\* : 匹配0或者任意数量的字符  
\*\* : 匹配0或者更多的目录

### 路由规则
最长匹配原则(has more characters)  
说明：URL请求/app/get/file，现在存在两个路径匹配模式/app/\*\*和/app/get/*，那么会根据模式/app/get/*来匹配。path粒度越细的将被优先匹配

### 依赖
**初始化路由表**  
sql脚本文件路径：classpath/sql.txt  
更改数据库配置： classpath/application.yml

### 运行
服务启动类：DynamicRouteApplication  

### 说明
重启服务or添加、更新路由信息请重置路由信息读取标志状态
``UPDATE dynamic_route SET is_read = 1``
