package com.zyifly.sys;

import org.junit.Test;

/**
 * Created by zhaoyifei on 16/8/12.
 */
public class TestSystemParameter {

    @Test
    public void testLoadConfiguration(){
        SystemParameter sp = new SystemParameter();
        sp.loadConfiguration("");
    }
}
