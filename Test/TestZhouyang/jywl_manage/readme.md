##### 1、项目介绍
 基于iview+ WebSocket+SpringBoot搭建的测试用例平台。页面Url等作为测试用例入口，WebSocket作为结果推送器。根据测试项划分的测试模块。

##### 2、文件结构
 1) aspect: 定义切面，测试注解等
 2) config: webConfig配置,webSocketConfig配置
 3) constant:常量
 4) controller:测试项页面,单元测试入口等
 5) exception:异常等
 6) runTest:测试器，根据测试项模块划分
 7) utils:工具类等
 8) vo：页面视图类
 9) webSocket:后台执行完测试用例，主动推送结果，前台JS根据对应的name做出相应的修正