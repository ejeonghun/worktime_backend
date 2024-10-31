package com.lunadev.worktime.schedule.entity;

import com.lunadev.worktime.Enum.ScheduleType;
import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.company.entity.Department;
import com.lunadev.worktime.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="schedule_id")
    private Long scheduleId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name="schedule_name")
    private String scheduleName;

    @Enumerated(EnumType.STRING)
    @Column(name="schedule_type")
    private ScheduleType scheduleType;

    @Column(name="schedule_details")
    private String scheduleDetails;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(name="end_date")
    private LocalDateTime endDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() { // 새로 생성될 때
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() { // 업데이트 될 때
        this.updatedAt = LocalDateTime.now();
    }
}