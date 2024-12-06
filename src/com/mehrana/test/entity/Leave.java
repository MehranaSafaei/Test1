package com.mehrana.test.entity;

import java.util.Date;
import java.util.Objects;

public class Leave {
    private int id;
    private String description;
    private Date startDate;
    private Date endDate;
    private Long personnelId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
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
        return id == leave.id && Objects.equals(description, leave.description) && Objects.equals(startDate, leave.startDate) && Objects.equals(endDate, leave.endDate) && Objects.equals(personnelId, leave.personnelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, startDate, endDate, personnelId);
    }
}
