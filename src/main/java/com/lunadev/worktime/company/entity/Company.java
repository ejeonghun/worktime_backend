package com.lunadev.worktime.company.entity;

import com.lunadev.worktime.Enum.RoleType;
import com.lunadev.worktime.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="Company")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="company_id")
    private Long companyId;

    @Column(name="company_name")
    private String companyName;

    @Column(name="verify_code")
    private String verifyCode;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Member> members;
}