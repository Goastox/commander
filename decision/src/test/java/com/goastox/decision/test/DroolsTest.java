package com.goastox.decision.test;

import com.alibaba.fastjson.JSON;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.Globals;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.utils.KieHelper;

import java.util.ArrayList;

public class DroolsTest {
    public static void main(String[] args) {

        String xxx = DroolsUtil.getDrlString("com/goastox/decision/test/activities.drl");
        KieHelper helper = new KieHelper();
        helper.addContent(xxx, ResourceType.DRL);
        StatelessKieSession session = helper.build().newStatelessKieSession();
        Person person = new Person();
        person.name = 1000;
        session.setGlobal("my", new ArrayList());
        session.execute(person);
        Object my = session.getGlobals().get("my");
        System.out.println(JSON.toJSONString(my));


    }
}
