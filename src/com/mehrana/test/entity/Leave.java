package com.mehrana.test.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Leave {
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Long personnelId;
    private LocalDateTime loginTime;  // اضافه کردن فیلد زمان ورود به سیستم

<<<<<<< HEAD
    public long getId() {
=======
    private Personnel personnel;


    public int getId() {
>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", personnelId=" + personnelId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leave leave = (Leave) o;
        return id == leave.id &&
                Objects.equals(description, leave.description) &&
                Objects.equals(startDate, leave.startDate) &&
                Objects.equals(endDate, leave.endDate) &&
                Objects.equals(personnelId, leave.personnelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, startDate, endDate, personnelId);
    }
}
