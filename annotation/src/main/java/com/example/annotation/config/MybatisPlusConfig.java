package com.example.annotation.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class MybatisPlusConfig {


    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(1000);
        // 开启 count 的 join 优化,只针对部分 left join
//        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    /**
     * 自定义id生成器
     *
     * @return
     */
    @Bean
    public IdentifierGenerator idGenerator() {
//        Integer workId = Integer.parseInt(env.getProperty("sequence.workId"));
//        Integer dataCenterId = Integer.parseInt(env.getProperty("sequence.dataCenterId"));
//        final Sequence sequence = new Sequence(workId, dataCenterId);
        final Sequence sequence = new Sequence(0, 0);
        return new IdentifierGenerator() {
            @Override
            public Number nextId(Object entity) {
                return sequence.nextId();
            }
        };
    }

}
