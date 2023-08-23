package model;

import com.alibaba.fastjson2.annotation.JSONField;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Salary {
    private Integer id;
    private String eno;
    private String ename;
    private BigDecimal basicSalary;//基本工资
    private BigDecimal rewardSalary;//奖励工资
    private BigDecimal overtimeSalary;//加班工资
    private BigDecimal subsidySalary;//补贴工资
    private BigDecimal reduceSalary;//扣除工资
    private BigDecimal totalSalary;//扣除工资
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime settlementTime;//结算时间

    public Salary() {
    }

    public Salary(Integer id, String eno, String ename, BigDecimal basicSalary, BigDecimal rewardSalary, BigDecimal overtimeSalary, BigDecimal subsidySalary, BigDecimal reduceSalary, BigDecimal totalSalary, LocalDateTime settlementTime) {
        this.id = id;
        this.eno = eno;
        this.ename = ename;
        this.basicSalary = basicSalary;
        this.rewardSalary = rewardSalary;
        this.overtimeSalary = overtimeSalary;
        this.subsidySalary = subsidySalary;
        this.reduceSalary = reduceSalary;
        this.totalSalary = totalSalary;
        this.settlementTime = settlementTime;
    }

    public Salary(String eno, String ename, BigDecimal basicSalary, BigDecimal rewardSalary, BigDecimal overtimeSalary, BigDecimal subsidySalary, BigDecimal reduceSalary, BigDecimal totalSalary, LocalDateTime settlementTime) {
        this.eno = eno;
        this.ename = ename;
        this.basicSalary = basicSalary;
        this.rewardSalary = rewardSalary;
        this.overtimeSalary = overtimeSalary;
        this.subsidySalary = subsidySalary;
        this.reduceSalary = reduceSalary;
        this.totalSalary = totalSalary;
        this.settlementTime = settlementTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEno() {
        return eno;
    }

    public void setEno(String eno) {
        this.eno = eno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getRewardSalary() {
        return rewardSalary;
    }

    public void setRewardSalary(BigDecimal rewardSalary) {
        this.rewardSalary = rewardSalary;
    }

    public BigDecimal getOvertimeSalary() {
        return overtimeSalary;
    }

    public void setOvertimeSalary(BigDecimal overtimeSalary) {
        this.overtimeSalary = overtimeSalary;
    }

    public BigDecimal getSubsidySalary() {
        return subsidySalary;
    }

    public void setSubsidySalary(BigDecimal subsidySalary) {
        this.subsidySalary = subsidySalary;
    }

    public BigDecimal getReduceSalary() {
        return reduceSalary;
    }

    public void setReduceSalary(BigDecimal reduceSalary) {
        this.reduceSalary = reduceSalary;
    }

    public BigDecimal getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(BigDecimal totalSalary) {
        this.totalSalary = totalSalary;
    }

    public LocalDateTime getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(LocalDateTime settlementTime) {
        this.settlementTime = settlementTime;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "id=" + id +
                ", eno='" + eno + '\'' +
                ", ename='" + ename + '\'' +
                ", basicSalary=" + basicSalary +
                ", rewardSalary=" + rewardSalary +
                ", overtimeSalary=" + overtimeSalary +
                ", subsidySalary=" + subsidySalary +
                ", reduceSalary=" + reduceSalary +
                ", totalSalary=" + totalSalary +
                ", settlementTime=" + settlementTime +
                '}'+"\n";
    }
}
