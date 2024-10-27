package com.lunadev.worktime.company.entity;

import com.lunadev.worktime.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Dept")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="dept_id")
    private Long deptId;

    @ManyToOne
    @JoinColumn(name="company_id", nullable = false)
    private Company company;

    @Column(name="dept_name")
    private String deptName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
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
