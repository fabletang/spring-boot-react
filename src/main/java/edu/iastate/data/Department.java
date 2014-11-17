package edu.iastate.data;

import org.springframework.data.annotation.Id;

/**
 * Created by jjhanso on 11/16/14.
 */
public class Department {
    @Id
    private String id;

    private String deptCode;

    private String departmentTitle;

    public Department(String deptCode, String departmentTitle) {
        this.deptCode = deptCode;
        this.departmentTitle = departmentTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDepartmentTitle() {
        return departmentTitle;
    }

    public void setDepartmentTitle(String departmentTitle) {
        this.departmentTitle = departmentTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (!departmentTitle.equals(that.departmentTitle)) return false;
        if (!deptCode.equals(that.deptCode)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = deptCode.hashCode();
        result = 31 * result + departmentTitle.hashCode();
        return result;
    }
}
