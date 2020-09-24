package com.jiashi.entity;

public class Archive_Master {
    private String id;

    private String patientId;

    private String inpNo;

    private String visitId;

    private String name;

    private String sex;

    private String deptName;

    private String dischargeDateTime;

    private String archivestate;

    private String admissionDateTime;

    private String deptAdmissionTo;

    private String checkDoctor;

    private String checkDatetime;

    private String checkedDoctor;

    private String checkedDatetime;

    private String lockinfo;

    private String doctorInCharge;

    private String idNo;

    private String dischargeDisposition;

    private String deptCodeLend;

    private String returntoRole;

    private String returnOperUsername;

    private String changeReason;

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getReturntoRole() {
        return returntoRole;
    }

    public void setReturntoRole(String returntoRole) {
        this.returntoRole = returntoRole;
    }

    public String getReturnOperUsername() {
        return returnOperUsername;
    }

    public void setReturnOperUsername(String returnOperUsername) {
        this.returnOperUsername = returnOperUsername;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId == null ? null : patientId.trim();
    }

    public String getInpNo() {
        return inpNo;
    }

    public void setInpNo(String inpNo) {
        this.inpNo = inpNo == null ? null : inpNo.trim();
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId == null ? null : visitId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getDischargeDateTime() {
        return dischargeDateTime;
    }

    public void setDischargeDateTime(String dischargeDateTime) {
        this.dischargeDateTime = dischargeDateTime == null ? null : dischargeDateTime.trim();
    }

    public String getArchivestate() {
        return archivestate;
    }

    public void setArchivestate(String archivestate) {
        this.archivestate = archivestate == null ? null : archivestate.trim();
    }

    public String getAdmissionDateTime() {
        return admissionDateTime;
    }

    public void setAdmissionDateTime(String admissionDateTime) {
        this.admissionDateTime = admissionDateTime == null ? null : admissionDateTime.trim();
    }

    public String getDeptAdmissionTo() {
        return deptAdmissionTo;
    }

    public void setDeptAdmissionTo(String deptAdmissionTo) {
        this.deptAdmissionTo = deptAdmissionTo == null ? null : deptAdmissionTo.trim();
    }

    public String getCheckDoctor() {
        return checkDoctor;
    }

    public void setCheckDoctor(String checkDoctor) {
        this.checkDoctor = checkDoctor == null ? null : checkDoctor.trim();
    }

    public String getCheckDatetime() {
        return checkDatetime;
    }

    public void setCheckDatetime(String checkDatetime) {
        this.checkDatetime = checkDatetime == null ? null : checkDatetime.trim();
    }

    public String getCheckedDoctor() {
        return checkedDoctor;
    }

    public void setCheckedDoctor(String checkedDoctor) {
        this.checkedDoctor = checkedDoctor == null ? null : checkedDoctor.trim();
    }

    public String getCheckedDatetime() {
        return checkedDatetime;
    }

    public void setCheckedDatetime(String checkedDatetime) {
        this.checkedDatetime = checkedDatetime == null ? null : checkedDatetime.trim();
    }

    public String getLockinfo() {
        return lockinfo;
    }

    public void setLockinfo(String lockinfo) {
        this.lockinfo = lockinfo == null ? null : lockinfo.trim();
    }

    public String getDoctorInCharge() {
        return doctorInCharge;
    }

    public void setDoctorInCharge(String doctorInCharge) {
        this.doctorInCharge = doctorInCharge == null ? null : doctorInCharge.trim();
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public String getDischargeDisposition() {
        return dischargeDisposition;
    }

    public void setDischargeDisposition(String dischargeDisposition) {
        this.dischargeDisposition = dischargeDisposition == null ? null : dischargeDisposition.trim();
    }

    public String getDeptCodeLend() {
        return deptCodeLend;
    }

    public void setDeptCodeLend(String deptCodeLend) {
        this.deptCodeLend = deptCodeLend == null ? null : deptCodeLend.trim();
    }
}