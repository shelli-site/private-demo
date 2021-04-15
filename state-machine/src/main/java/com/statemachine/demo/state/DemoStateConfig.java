package com.statemachine.demo.state;

//import org.springframework.context.annotation.Configuration;

import cn.hutool.extra.spring.SpringUtil;
import com.statemachine.demo.entity.DemoEntity;
import com.statemachine.demo.entity.base.BaseStateEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;

/**
 * create by shen_xi on 2021/04/14
 */
@Slf4j
@Configuration
@EnableStateMachineFactory(name = DemoStateConfig.STATE_MACHINE_FACTORY)
public class DemoStateConfig extends EnumStateMachineConfigurerAdapter<DemoStateConfig.States, DemoStateConfig.Events> {

    public final static String STATE_MACHINE_ID = "normal";
    public final static String STATE_MACHINE_FACTORY = "normalFactory";
    public final static String STATE_RESTORE_BEAN = "normalRestore";

    public enum States {
        NOT_STORAGE, // 未入库
        REVIEWING, // 审核中
        REVIEW_FAIL, // 审核失败
        STORED // 已入库
    }

    public enum Events {
        SUBMIT_REVIEW, // 提交审核
        REVIEW_PASS, // 审核通过
        REVIEW_REJECT // 审核驳回
    }

    @EventListener
    public void test(ContextRefreshedEvent event) {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setState("REVIEWING");
        StateMachine<States, Events> restore = restore(demoEntity);
        Message<Events> message = MessageBuilder.withPayload(Events.REVIEW_PASS).setHeader("entity", demoEntity).build();
        restore.sendEvent(message);
    }


    public static StateMachine<States, Events> build() {
        StateMachineFactory<States, Events> stateMachineFactory = SpringUtil.getBean(STATE_MACHINE_FACTORY);
        return stateMachineFactory.getStateMachine(STATE_MACHINE_ID);
    }

    public static StateMachine<States, Events> restore(BaseStateEntity entity) {
        StateMachine<States, Events> stateMachine = build();
        States state = States.valueOf(entity.getStateMachineState());
        StateMachine<States, Events> restore = null;
        try {
            StateMachinePersister<States, Events, States> persist = SpringUtil.getBean(STATE_RESTORE_BEAN);
            restore = persist.restore(stateMachine, state);
        } catch (Exception e) {
            log.error("状态恢复失败！", e);
        }
        return restore;
    }

    @Bean(STATE_RESTORE_BEAN)
    public StateMachinePersister<States, Events, States> endurance() {
        return new DefaultStateMachinePersister<>(new StateMachinePersist<States, Events, States>() {
            @Override
            public void write(StateMachineContext<States, Events> context, States contextObj) throws Exception {
            }

            @Override
            public StateMachineContext<States, Events> read(States state) throws Exception {
                StateMachineContext<States, Events> result =
                        new DefaultStateMachineContext<States, Events>(state,
                                null, null, null, null, STATE_MACHINE_ID);
                return result;
            }
        });
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.NOT_STORAGE)
                .end(States.STORED)
                .states(EnumSet.allOf(States.class));

    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.NOT_STORAGE).target(States.REVIEWING)
                .event(Events.SUBMIT_REVIEW)
                .and()
                .withExternal()
                .source(States.REVIEWING).target(States.STORED)
                .event(Events.REVIEW_PASS)
                .and()
                .withExternal()
                .source(States.REVIEWING).target(States.REVIEW_FAIL)
                .event(Events.REVIEW_REJECT)
                .and()
                .withExternal()
                .source(States.REVIEW_FAIL).target(States.REVIEWING)
                .event(Events.SUBMIT_REVIEW);
    }

}
