package com.pbrx.mylib.util;

import java.util.List;

public class UserInfoBean {

    /**
     * employee : {"id":1,"name":"江南一点雨","gender":"男","birthday":631123200000,"idCard":"610122199001011256","wedlock":"已婚","nativePlace":"陕西","email":"laowang@qq.com","phone":"15901503412","address":"深圳市南山区","posName":null,"engageForm":"劳务合同","tiptopDegree":"本科","specialty":"信息管理与信息系统","school":"深圳大学","beginDate":1514736000000,"workState":"在职","workID":"00000001","contractTerm":2,"conversionTime":1522512000000,"notWorkDate":null,"beginContract":1514736000000,"endContract":1577808000000,"workAge":null,"department":null,"nation":null,"jobLevel":null,"position":null,"politicsStatus":null,"salary":null,"password":null,"deviceId":"354730010301806"}
     */

    private EmployeeBean employee;

    public EmployeeBean getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeBean employee) {
        this.employee = employee;
    }

    public static class EmployeeBean {
        /**
         * id : 1
         * name : 江南一点雨
         * gender : 男
         * birthday : 631123200000
         * idCard : 610122199001011256
         * wedlock : 已婚
         * nativePlace : 陕西
         * email : laowang@qq.com
         * phone : 15901503412
         * address : 深圳市南山区
         * posName : null
         * engageForm : 劳务合同
         * tiptopDegree : 本科
         * specialty : 信息管理与信息系统
         * school : 深圳大学
         * beginDate : 1514736000000
         * workState : 在职
         * workID : 00000001
         * contractTerm : 2.0
         * conversionTime : 1522512000000
         * notWorkDate : null
         * beginContract : 1514736000000
         * endContract : 1577808000000
         * workAge : null
         * department : null
         * nation : null
         * jobLevel : null
         * position : null
         * politicsStatus : null
         * salary : null
         * password : null
         * deviceId : 354730010301806
         */

        private int id;
        private String name;
        private String gender;
        private long birthday;
        private String idCard;
        private String wedlock;
        private String nativePlace;
        private String email;
        private String phone;
        private String address;
        private Object posName;
        private String engageForm;
        private String tiptopDegree;
        private String specialty;
        private String school;
        private long beginDate;
        private String workState;
        private String workID;
        private double contractTerm;
        private long conversionTime;
        private String notWorkDate;
        private long beginContract;
        private long endContract;
        private String workAge;
        private String department;
        private String nation;
        private String jobLevel;
        private String position;
        private String politicsStatus;
        private String salary;
        private String password;
        private String deviceId;
        private String userface;
        private String companyName;
        private long departmentId;
        private long companyId;
        private List<String> roleNames;

        public long getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(long departmentId) {
            this.departmentId = departmentId;
        }

        public long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(long companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public List<String> getRoleNames() {
            return roleNames;
        }

        public void setRoleNames(List<String> roleNames) {
            this.roleNames = roleNames;
        }

        public String getUserface() {
            return userface;
        }

        public void setUserface(String userface) {
            this.userface = userface;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public long getBirthday() {
            return birthday;
        }

        public void setBirthday(long birthday) {
            this.birthday = birthday;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getWedlock() {
            return wedlock;
        }

        public void setWedlock(String wedlock) {
            this.wedlock = wedlock;
        }

        public String getNativePlace() {
            return nativePlace;
        }

        public void setNativePlace(String nativePlace) {
            this.nativePlace = nativePlace;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getPosName() {
            return posName;
        }

        public void setPosName(Object posName) {
            this.posName = posName;
        }

        public String getEngageForm() {
            return engageForm;
        }

        public void setEngageForm(String engageForm) {
            this.engageForm = engageForm;
        }

        public String getTiptopDegree() {
            return tiptopDegree;
        }

        public void setTiptopDegree(String tiptopDegree) {
            this.tiptopDegree = tiptopDegree;
        }

        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public long getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(long beginDate) {
            this.beginDate = beginDate;
        }

        public String getWorkState() {
            return workState;
        }

        public void setWorkState(String workState) {
            this.workState = workState;
        }

        public String getWorkID() {
            return workID;
        }

        public void setWorkID(String workID) {
            this.workID = workID;
        }

        public double getContractTerm() {
            return contractTerm;
        }

        public void setContractTerm(double contractTerm) {
            this.contractTerm = contractTerm;
        }

        public long getConversionTime() {
            return conversionTime;
        }

        public void setConversionTime(long conversionTime) {
            this.conversionTime = conversionTime;
        }

        public String getNotWorkDate() {
            return notWorkDate;
        }

        public void setNotWorkDate(String notWorkDate) {
            this.notWorkDate = notWorkDate;
        }

        public long getBeginContract() {
            return beginContract;
        }

        public void setBeginContract(long beginContract) {
            this.beginContract = beginContract;
        }

        public long getEndContract() {
            return endContract;
        }

        public void setEndContract(long endContract) {
            this.endContract = endContract;
        }

        public String getWorkAge() {
            return workAge;
        }

        public void setWorkAge(String workAge) {
            this.workAge = workAge;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getJobLevel() {
            return jobLevel;
        }

        public void setJobLevel(String jobLevel) {
            this.jobLevel = jobLevel;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getPoliticsStatus() {
            return politicsStatus;
        }

        public void setPoliticsStatus(String politicsStatus) {
            this.politicsStatus = politicsStatus;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }
    }
}
