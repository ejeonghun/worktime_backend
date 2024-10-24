package com.lunadev.worktime.member.entity;

import com.lunadev.worktime.Enum.RoleType;
import com.lunadev.worktime.company.entity.Company;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Member")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false, columnDefinition = "VARCHAR(30) DEFAULT 'USER'")
    private RoleType role = RoleType.USER; // 기본값 설정
}