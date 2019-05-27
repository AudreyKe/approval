package com.weshareholdings.approval.handler;

import org.snaker.engine.SnakerEngine;
import org.snaker.engine.core.Execution;
import org.snaker.engine.entity.Task;
import org.snaker.engine.helper.StringHelper;

import java.util.List;

/**
 * @Author liling
 * @Date 2018/9/30
 **/
public class SurrogateInterceptor extends org.snaker.engine.impl.SurrogateInterceptor {
    @Override
    public void intercept(Execution execution) {
        SnakerEngine engine = execution.getEngine();
        List<Task> list = execution.getTasks();
        for (Task task : execution.getTasks()) {
            if (task.getActorIds() == null) {
                continue;
            }
            for (String actor : task.getActorIds()) {
                if (StringHelper.isEmpty(actor)) {
                    continue;
                }
                String agent = engine.manager().getSurrogate(actor, execution.getProcess().getName());
                if (StringHelper.isNotEmpty(agent) && !actor.equals(agent)) {
                    String[] actors = agent.split(",");
                    engine.task().addTaskActor(task.getId(), actors);
                }
            }
        }
    }
}
