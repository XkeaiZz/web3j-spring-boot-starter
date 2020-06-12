# web3j-spring-boot-starter
## ʹ�÷���
��������
```xml
<!-- https://mvnrepository.com/artifact/com.github.Jntmkk/web3j-spring-boot-starter -->
<dependency>
    <groupId>com.github.Jntmkk</groupId>
    <artifactId>web3j-spring-boot-starter</artifactId>
    <version>0.0.2</version>
</dependency>

```
���ò�����
```yaml
web3j:
  # �������ó�ʵ��ʹ�õ�
  # ��������ַ��֧�� HTTP��HTTPS��websocket 
  server-address: https://rinkeby.infura.io/v3/e4fd1742a2d4435587f43abebeb48485
  # �뱣�ֹر�  
  admin-client: false
  # OKHTTPS����  
  include-raw-response: true
  # ��Լɨ��·��������ϸ�˶�  
  scan-package: xyz.utools.web3j.contract
  # ��Լ��ַ�����Ϊ�������ϸ���ͬ���ұ�Ϊ��Լ���ַ������Ҫ���´��������� new
  # ע���Լ��ַ����ʹ��˫���Ű�������Ȼ�ᱻ��Ϊ������   
  contracts:
    RegisterAndManager: "0x9203da4357cc12acc297c5baf8d744154927674b"
  #  �˻���ַ���Ǳ���
  account-addr: 0x43a87239B4CF34536C257a7193E21bf46d0d2F25
  # ��Կ������  
  secrete: 2539897EE3C10E19886C59357F536933A7AFED4F0218E16EBEF4EA1414792504
  #  Ĭ�ϵ� GasProvider �ɱ������ṩ��������gas����������
  # ���Լ�ʵ�ִ˽ӿڣ�������ָ����·�������� �˴�ΪĬ��
  gas-provider: com.github.jntm.web3jspringbootstarter.DefaultGasProvider
```