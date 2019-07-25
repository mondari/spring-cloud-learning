## eureka-server 

注册中心（Service Registry），用来给其它eureka客户端提供注册服务

### 单节点注册中心启动方式

1. 在 `Run Dashboard` 面板里右击 `EurekaServerApplication`
2. 选择 `Edit Configuration`（或者按 `F4` 键）
3. 在 `Active Profiles` 里填写 `standalone` （这是以单节点方式启动的配置文件）
4. 点击OK
5. 然后启动该应用，即可以单节点的方式运行该应用

### 多节点注册中心启动方式
双击运行script目录下的脚本 start.cmd。该脚本会先进行构建，然后将 target 目录构建好的 jar 包复制到 script 目录，接着会启动三个命令行窗口，分别以 peer1、peer2和peer3的配置方式运行该应用。