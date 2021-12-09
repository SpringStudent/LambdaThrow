# LambdaThrow

java通过lambda方式抛出异常

### 环境要求

jdk1.8+


### 测试用例

```java
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
```