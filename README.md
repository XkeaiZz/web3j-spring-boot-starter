# web3j-spring-boot-starter
## 使用方法
引入依赖
```xml
<!-- https://mvnrepository.com/artifact/com.github.Jntmkk/web3j-spring-boot-starter -->
<dependency>
    <groupId>com.github.Jntmkk</groupId>
    <artifactId>web3j-spring-boot-starter</artifactId>
    <version>0.0.2</version>
</dependency>

```
配置参数：
```yaml
web3j:
  # 参数配置成实际使用的
  # 服务器地址，支持 HTTP、HTTPS、websocket 
  server-address: https://rinkeby.infura.io/v3/e4fd1742a2d4435587f43abebeb48485
  # 请保持关闭  
  admin-client: false
  # OKHTTPS设置  
  include-raw-response: true
  # 合约扫描路径，请仔细核对  
  scan-package: xyz.utools.web3j.contract
  # 合约地址，左边为类名，严格相同，右边为合约类地址，若需要重新创建，请填 new
  # 注意合约地址必须使用双引号包裹，不然会被人为是数字   
  contracts:
    RegisterAndManager: "0x9203da4357cc12acc297c5baf8d744154927674b"
  #  账户地址，非必须
  account-addr: 0x43a87239B4CF34536C257a7193E21bf46d0d2F25
  # 秘钥，必须  
  secrete: 2539897EE3C10E19886C59357F536933A7AFED4F0218E16EBEF4EA1414792504
  #  默认的 GasProvider 由本工具提供，若出现gas不足等情况，
  # 请自己实现此接口，并在这指明类路径及类名 此处为默认
  gas-provider: com.github.jntm.web3jspringbootstarter.DefaultGasProvider
```