### 简介
MyBatis-Plus（简称 MP）是一个 MyBatis 的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

### 快速开始
和使用mybatis一样只需加入mybatis-plus依赖即可使用 ，注意添加mybatis-plus依赖就不用再加入mybatis的依赖了，会关联加入
```xml
<!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.4.0</version>
</dependency>
```
### CRUD
与mybatis比较，普通的crud不用再编写sql也不用在编写mapper.xml或者使用注解，只需继承 com.baomidou.mybatisplus.core.mapper.BaseMapper 就可以使用普通的crud了
```java
@Component
@Mapper
public interface EmpDao extends BaseMapper<EmpBean> {
    //如果是复杂的数据库操作，这里依然可以添加自己的实现
}
```

###  插件扩展
主体插件: MybatisPlusInterceptor

该插件内部插件集:
* 分页插件: PaginationInnerInterceptor
* 多租户插件: TenantLineInnerInterceptor
* 动态表名插件: DynamicTableNameInnerInterceptor
* 乐观锁插件: OptimisticLockerInnerInterceptor
* sql性能规范插件: IllegalSQLInnerInterceptor
* 防止全表更新与删除插件: BlockAttackInnerInterceptor
* 非法sql拦截器插件: IllegalSQLInnerInterceptor
#### 分页插件
第一步：添加拦截器
```java
@Configuration
public class MybatisPlusConfig {

    private Logger logger = LoggerFactory.getLogger(MybatisPlusConfig.class);

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        logger.info("load PaginationInnerInterceptor");
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }

}
```
第二步：使用分页查询功能
```java
Page<EmpBean> empBeanPage = empDao.selectPage(new Page<>(2, 3), null);
for (EmpBean record : empBeanPage.getRecords()) {
    logger.info(record.toString());
}
```

___注意:___

如果内部插件都是使用,需要注意顺序关系,建议使用如下顺序

* 多租户插件,动态表名插件
* 分页插件,乐观锁插件
* sql性能规范插件,防止全表更新与删除插件

___总结: 对sql进行单次改造的优先放入,不对sql进行改造的最后放入___

# 其它插件 
其它的插件见如下链接 [mybatis-plus interceptor](https://baomidou.com/guide/interceptor.html)
