package com.statemachine.demo.handle.event;

import com.statemachine.demo.entity.base.BaseStateEntity;
import com.statemachine.demo.state.DemoStateConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.OnTransition;

import java.util.Optional;

/**
 * create by shen_xi on 2021/04/14
 * <p>
 * <p>
 * 一个状态机的建立（包括恢复）
 * <p>@OnTransitionStart</p>
 * 一个状态机的结束
 * <p>@OnTransitionEnd</p>
 * 特殊状态
 * <p>@OnStateMachineError 状态错误</p>
 * <p>@OnEventNotAccepted 状态未匹配</p>
 * 一次transition所调用的事件顺序 (source, target)
 * <p>1. @OnTransitionStart transition开始(source -> target) #source</p>
 * <p>2. @OnTransition transition(source -> target) #source</p>
 * <p>3. @OnStateExit 上个状态(source)退出  #source</p>
 * <p>4. @OnStateEntry 状态(target)进入 #target</p>
 * <p>5. @OnStateChanged 状态改变(source -> target) #target</p>
 * <p>6. @OnTransitionEnd transition结束(source -> target) #target</p>
 */
@Slf4j
//@WithStateMachine(id = DemoStateConfig.STATE_MACHINE_ID)
public class DemoEventHandler {

    @OnTransition
    public void stateChange(Message<DemoStateConfig.Events> message) {
        if (message == null) {
            log.info("无message");
            return;
        }
        BaseStateEntity entity = Optional.ofNullable(message).map(Message::getHeaders).map(headers -> (BaseStateEntity) headers.get("entity")).orElse(null);
        entity.setStateMachineState(message.getPayload().name());
        log.info("state change to {}", message.getPayload().name());
    }


    @OnStateChanged(target = "NOT_STORAGE")
    public void create() {
        log.info("对象创建");
    }

    @OnStateChanged(target = "REVIEWING")
    public void submit(Message<DemoStateConfig.Events> message) {
        log.info("提交审核 {}", message);
    }

    @OnStateChanged(target = "REVIEW_FAIL")
    public void reviewFail(Message<DemoStateConfig.Events> message) {
        log.info("审核失败 \n{}", message);
    }

    @OnStateChanged(target = "STORED")
    public void stored(Message<DemoStateConfig.Events> message) {
        log.info("审核通过 \n{}", message);
    }
}
