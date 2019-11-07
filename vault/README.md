## spring-cloud-vault

### 使用方法
1. 安装 vault
2. 安装 vault-autocomplete 并重启shell
```shell script
$ vault -autocomplete-install
$ exec $SHELL
```

3. 以开发模式启动 vault
```shell script
$ vault server -dev -dev-root-token-id="00000000-0000-0000-0000-000000000000" -dev-listen-address="0.0.0.0:8200"
```

4. 新打开一个终端，执行以下命令，向 vault 中存储配置
```shell script
$ export VAULT_ADDR="http://0.0.0.0:8200"
$ export VAULT_DEV_ROOT_TOKEN_ID="00000000-0000-0000-0000-000000000000"
$ vault kv put secret/appliation example.username=demouser example.password=demopassword
$ vault kv put secret/appliation/cloud example.username=clouduser example.password=cloudpassword
```

上述命令储存的配置其实相当于：

**applicaton.properties**

```properties
example.username=demouser 
example.password=demopassword
```

**applicaton-cloud.properties**

```properties
example.username=clouduser 
example.password=cloudpassword
```

5. 启动应用，会看到控制台打印以下信息

```shell
----------------------------------------
Configuration properties
        example.username is demouser
        example.password is demopassword
----------------------------------------
```

如果以 `cloud` 配置启动应用，会看到

```shell
----------------------------------------
Configuration properties
        example.username is clouduser
        example.password is cloudpassword
----------------------------------------
```

如果将上面名称存储的配置名改为bootstrap.yml中spring.application.name定义的名称，同样可获得相同的结果。