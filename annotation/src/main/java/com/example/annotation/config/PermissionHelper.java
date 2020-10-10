package com.example.annotation.config;

import com.example.annotation.annotation.DataAuth;
import com.example.annotation.utils.DataAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author shen_xi
 * @create 2020/10/09 10:14
 */
@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PermissionHelper implements Interceptor, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PermissionHelper.applicationContext = applicationContext;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object parameterObject = args[1];
        if (SqlCommandType.SELECT != mappedStatement.getSqlCommandType() || StatementType.CALLABLE == mappedStatement.getStatementType()) {
            return invocation.proceed();
        }
        Class<?> classType = Class.forName(mappedStatement.getId().substring(0, mappedStatement.getId().lastIndexOf(".")));
        String methodName = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".") + 1);
        List<Method> methods = Arrays.stream(classType.getMethods()).filter(m -> m.getName().equals(methodName)).limit(1L).collect(Collectors.toList());
        Method method = methods.get(0);
        DataAuth annotation = method.getAnnotation(DataAuth.class);
        if (null == annotation) {
            return invocation.proceed();
        }
        // 拼接sql
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        // 组装新的 sql
        String newSql;
        try {
            DataAuthService dataAuthService = getBean(DataAuthService.class);
            Class<?> sequenceClass = dataAuthService.getClass();
            Method sequenceSql = sequenceClass.getMethod(annotation.method(), String.class, DataAuth.class);
            newSql = (String) sequenceSql.invoke(dataAuthService, new Object[]{boundSql.getSql(), annotation});
        } catch (Exception e) {
            log.error("反射异常，{}是否存在。{}", annotation.method(), e);
            return invocation.proceed();
        }

        // 重新new一个查询语句对象
        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), newSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        // 把新的查询放到statement里
        MappedStatement newMs = newMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        Object[] queryArgs = invocation.getArgs();
        queryArgs[0] = newMs;
        return invocation.proceed();
    }

    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new
                MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    /**
     * 当目标类是Executor类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
     */
    @Override
    public Object plugin(Object target) {
        log.info("plugin方法：{}", target);
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * 定义一个内部辅助类，作用是包装 SQL
     */
    class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }

    }

    //通过class获取Bean.
    public <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
