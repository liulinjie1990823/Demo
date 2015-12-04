package cn.com.llj.demo.activity.other;

import android.view.View;

import com.common.library.llj.utils.LogUtilLj;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.com.llj.demo.DemoActivity;
import cn.com.llj.demo.R;

/**
 * Created by liulj on 15/12/2.
 */
public class GsonDemo extends DemoActivity {
    @Override
    public int getLayoutId() {
        return R.layout.gson_demo;
    }

    public void deserialize(View view) {
        Employee employee1 = new Employee(111, "Maynard", "Ron", new Date());
        String str = new Gson().toJson(employee1);//Dec 2, 2015 4:48:44 PM
        //Wed Dec 02 16:48:44 GMT+08:00 2015
        //y年M月d日 ah:mm:ss
        //MMM d, y h:mm:ss a
        //yyyy-MM-dd'T'HH:mm:ss'Z'
        Employee employee = new Gson().fromJson(str, Employee.class);
        LogUtilLj.LLJi(employee.toString());
    }

    public void deserializemap(View view) {
        HashMap<String, Employee2.po> map = new HashMap<>();
        map.put("A", new Employee2.po(100, 101));
        map.put("B", new Employee2.po(102, 103));
        Employee2 employee2 = new Employee2(111, "Maynard", "Ron", map);
        String str = new Gson().toJson(employee2);
        LogUtilLj.LLJi("json:"+str);
        Employee2 employee3 = new Gson().fromJson(str, Employee2.class);
        LogUtilLj.LLJi(employee3.toString());
    }

    static class Employee1 {
        public Employee1(Integer employee_id, String surname, String givenname, String lastlogin) {
            this.employee_id = employee_id;
            this.surname = surname;
            this.givenname = givenname;
            this.lastlogin = lastlogin;
        }

        private Integer employee_id;

        private String surname;

        private String givenname;

        private String lastlogin;
    }

    static class Employee {
        public Employee(Integer employee_id, String surname, String givenname, Date lastlogin) {
            this.employee_id = employee_id;
            this.surname = surname;
            this.givenname = givenname;
            this.lastlogin = lastlogin;
        }

        private Integer employee_id;

        private String surname;

        private String givenname;

        private Date lastlogin;

        @Override
        public String toString() {
            return "Employee{" +
                    "employee_id=" + employee_id +
                    ", surname='" + surname + '\'' +
                    ", givenname='" + givenname + '\'' +
                    ", lastlogin=" + lastlogin +
                    '}';
        }
    }

    static class Employee2 {
        public Employee2(Integer employee_id, String surname, String givenname, Map<String, po> lastlogin) {
            this.employee_id = employee_id;
            this.surname = surname;
            this.givenname = givenname;
            this.lastlogin = lastlogin;
        }

        private Integer employee_id;

        private String surname;

        private String givenname;

        private Map<String, po> lastlogin;

        @Override
        public String toString() {
            return "Employee2{" +
                    "employee_id=" + employee_id +
                    ", surname='" + surname + '\'' +
                    ", givenname='" + givenname + '\'' +
                    ", lastlogin=" + lastlogin +
                    '}';
        }

        static class po {
            private int x;
            private int y;

            public po(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }
        }
    }
}
