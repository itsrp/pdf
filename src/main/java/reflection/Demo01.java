package reflection;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Arrays;
import java.util.List;

public class Demo01 {

    public static void main(String[] args) throws IllegalAccessException {
        Demo01 demo01 = new Demo01();
        //demo01.readField();
        demo01.writeField();

        /*String s = "4";
        Object o = castObject(Long.class, s);
        System.out.println(o.getClass());
        if(Long.class.isAssignableFrom(s.getClass())) {
            System.out.println("OK");
        }
        */

    }

    private static <T> T castObject(T clazz, Object object) {
        return (T) object;
    }


    public void readField() throws IllegalAccessException {
        Student student = new Student(1, 2, Arrays.asList(4L, 5L));

        System.out.println(FieldUtils.readDeclaredField(student, "codes", true));
    }

    public void writeField() throws IllegalAccessException {
        Student student = new Student(1, 2, Arrays.asList(4L, 5L));
        List<String> list = Arrays.asList("22", "11");
        FieldUtils.writeDeclaredField(student, "codes", list, true);

        System.out.println(student);
    }

    class Student {
        private int id;
        private long code;
        private List<Long> codes;

        public Student(int id, long code, List<Long> codes) {
            this.id = id;
            this.code = code;
            this.codes = codes;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getCode() {
            return code;
        }

        public void setCode(long code) {
            this.code = code;
        }

        public List<Long> getCodes() {
            return codes;
        }

        public void setCodes(List<Long> codes) {
            this.codes = codes;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", code=" + code +
                    ", codes=" + codes +
                    '}';
        }
    }
}
