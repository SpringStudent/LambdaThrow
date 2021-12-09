package io.github.springstudent;

import io.github.springstudent.exception.NoPermissionException;
import io.github.springstudent.exception.ParamInvalidException;
import io.github.springstudent.exception.ResultException;
import io.github.springstudent.utils.ThrowExceptionUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ThrowUtilsTest {

    @Test
    public void testThrowUtils() throws Exception {
        List<Exception> exs = new ArrayList<>();
        try {
            ThrowExceptionUtil.compareAndThrowExceptionByClass(1, 2, (c1, c2) -> 2 > 1, "累困饿睡觉", ParamInvalidException.class);
        }catch (ParamInvalidException e){
            exs.add(e);
            assertEquals(e.getClass(),ParamInvalidException.class);
        }
        try {
            ThrowExceptionUtil.validAndThrowExceptionByClass(1, i -> i==1,"不能为1", ResultException.class);
        }catch (ResultException e){
            exs.add(e);
            assertEquals(e.getClass(),ResultException.class);
        }
        try {
            ThrowExceptionUtil.validAndThrowNoPermissionException("nopermiss", s -> s.equals("nopermiss"),"无权限");
        }catch (NoPermissionException e){
            exs.add(e);
            assertEquals(e.getClass(),NoPermissionException.class);
        }
        assertTrue(exs.size()==3);
    }
}
