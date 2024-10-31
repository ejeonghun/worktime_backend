package com.lunadev.worktime.work.entity;


import com.lunadev.worktime.Enum.WorkType;
import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Work")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_id")
    private Long workId;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name="date")
    private LocalDate date;

    @Column(name="start_time")
    private LocalDateTime startTime;

    @Column(name="end_time")
    private LocalDateTime endTime;

    @Column(name="work_type")
    @Enumerated(EnumType.ORDINAL)
    private WorkType workType;

    @Column(name="work_time")
    private Long workTime;

    @Column(name="latitude")
    private Double latitude;

    @Column(name="longitude")
    private Double longitude;

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