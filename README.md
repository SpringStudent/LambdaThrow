# What's LambdaThrow

java throws exceptions using lambda,Parameter verification can be performed with Swagger's ApiModelProperty annoation,really sweet.

### Env Require

jdk1.8+


### Test Case

```java
    public class ThrowUtilsTest {


    class ThrowBean{

        @ApiModelProperty("主键id")
        private String id;
        @ApiModelProperty("尺寸")
        private Integer size;
        @ApiModelProperty("其他key value属性")
        private Map<String,Object> map;
        @ApiModelProperty("notNull属性")
        private Object notNull = new Object();

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Map<String, Object> getMap() {
            return map;
        }

        public void setMap(Map<String, Object> map) {
            this.map = map;
        }

        public Object getNotNull() {
            return notNull;
        }

        public void setNotNull(Object notNull) {
            this.notNull = notNull;
        }
    }

    @Test
    public void testThrowUtils() throws Exception {
        List<Exception> exs = new ArrayList<>();
        ThrowBean throwBean = new ThrowBean();
        try {
            ThrowExceptionUtil.validAndThrowParamInvalidException(throwBean.getNotNull(),ThrowBean::getNotNull);
        }catch (ParamInvalidException e){
            System.out.println(e.getMessage());
            exs.add(e);
            assertEquals(e.getClass(),ParamInvalidException.class);
        }

        try {
            ThrowExceptionUtil.validAndThrowParamInvalidException(throwBean.getId(),ThrowBean::getId);
        }catch (ParamInvalidException e){
            System.out.println(e.getMessage());
            exs.add(e);
            assertEquals(e.getClass(),ParamInvalidException.class);
        }
        try {
            ThrowExceptionUtil.validAndThrowParamInvalidException(throwBean.getMap(),ThrowBean::getMap);
        }catch (Exception e){
            System.out.println(e.getMessage());
            exs.add(e);
            assertEquals(e.getClass(),ParamInvalidException.class);
        }
        try {
            ThrowExceptionUtil.validAndThrowParamInvalidException(throwBean.getSize(),ThrowBean::getSize);
        }catch (Exception e){
            System.out.println(e.getMessage());
            exs.add(e);
            assertEquals(e.getClass(),ParamInvalidException.class);
        }

        try {
            ThrowExceptionUtil.compareAndThrowExceptionByClass(1, 2, (c1, c2) -> c2 > c1, "c2大于c1抛出异常", ParamInvalidException.class);
        }catch (ParamInvalidException e){
            System.out.println(e.getMessage());
            exs.add(e);
            assertEquals(e.getClass(),ParamInvalidException.class);
        }
        try {
            ThrowExceptionUtil.validAndThrowExceptionByClass(1, i -> i==1,"i不能为1", ResultException.class);
        }catch (ResultException e){
            System.out.println(e.getMessage());
            exs.add(e);
            assertEquals(e.getClass(),ResultException.class);
        }
        try {
            ThrowExceptionUtil.validAndThrowNoPermissionException("nopermiss", s -> s.equals("nopermiss"),"无权限");
        }catch (NoPermissionException e){
            System.out.println(e.getMessage());
            exs.add(e);
            assertEquals(e.getClass(),NoPermissionException.class);
        }
        assertTrue(exs.size()==6);
    }
}
```