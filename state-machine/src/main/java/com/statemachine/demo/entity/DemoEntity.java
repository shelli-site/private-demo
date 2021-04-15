package com.statemachine.demo.entity;

import com.statemachine.demo.entity.base.BaseStateEntity;
import lombok.Data;

/**
 * create by shen_xi on 2021/04/15
 */
@Data
public class DemoEntity implements BaseStateEntity {

    private Long id;
    private String name;
    private String state;

    @Override
    public String getStateMachineState() {
        return state;
    }

    @Override
    public void setStateMachineState(String state) {
        this.state = state;
    }
}
