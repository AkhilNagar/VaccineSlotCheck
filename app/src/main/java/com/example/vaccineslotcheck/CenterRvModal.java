package com.example.vaccineslotcheck;

public class CenterRvModal
{

    private String centerName;
    private String centerAddress;
    private String centerFromTime;
    private String centerToTime;
    private String fee_type;
    private int ageLimit;
    private String vaccineName;
    private int availableCapacity;
    public CenterRvModal(String centerName, String centerAddress, String centerFromTime, String centerToTime, String fee_type, int ageLimit, String vaccineName, int availableCapacity)
    {
        this.centerName=centerName;
        this.centerAddress=centerAddress;
        this.centerFromTime=centerFromTime;
        this.centerToTime=centerToTime;
        this.fee_type=fee_type;
        this.ageLimit=ageLimit;
        this.vaccineName=vaccineName;
        this.availableCapacity=availableCapacity;

    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }
    public String getCenterAddress() {
        return centerAddress;
    }

    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    public String getCenterFromTime() {
        return centerFromTime;
    }

    public void setCenterFromTime(String centerFromTime) {
        this.centerFromTime = centerFromTime;
    }

    public String getCenterToTime() {
        return centerToTime;
    }

    public void setCenterToTime(String centerToTime) {
        this.centerToTime = centerToTime;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(int availableCapacity) {
        this.availableCapacity = availableCapacity;
    }


}