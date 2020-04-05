
## 项目架构
![](./doc/pic/1.png)    

## 技术栈

> 1. Spring Boot
> 2. Vue
> 3. 微信小程序


## 快速启动

1. 配置最小开发环境：
    * [MySQL](https://dev.mysql.com/downloads/mysql/)
    * [JDK1.8或以上](http://www.oracle.com/technetwork/java/javase/overview/index.html)
    * [Maven](https://maven.apache.org/download.cgi)
    * [Nodejs](https://nodejs.org/en/download/)
    * [微信开发者工具](https://developers.weixin.qq.com/miniprogram/dev/devtools/download.html)
    
2. 数据库依次导入litemall-db/sql下的数据库文件
    * litemall_schema.sql
    * litemall_table.sql
 

3. 启动小商场和管理后台的后端服务

    打开命令行，输入以下命令
    ```bash
    cd litemall
    mvn install
    mvn clean package
    java -Dfile.encoding=UTF-8 -jar litemall-all/target/litemall-all-0.1.0-exec.jar
    ```
    
4. 启动管理后台前端

    打开命令行，输入以下命令
    ```bash
    npm install -g cnpm --registry=https://registry.npm.taobao.org
    cd litemall/litemall-admin
    cnpm install
    cnpm run dev
    ```
    此时，浏览器打开，输入网址`http://localhost:9527`, 此时进入管理后台登录页面。
    
5. 启动小商城前端
   
   小商场前端renard-wx，开发者可以分别导入和测试：
   
   1. 微信开发工具导入litemall-wx项目;
   2. 项目配置，启用“不校验合法域名、web-view（业务域名）、TLS 版本以及 HTTPS 证书”
   3. 点击“编译”，即可在微信开发工具预览效果；
   4. 也可以点击“预览”，然后手机扫描登录（但是手机需开启调试功能）。
      


