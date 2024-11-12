package com.lunadev.worktime.company.repository;

import com.lunadev.worktime.company.dto.DeptDto;
import com.lunadev.worktime.company.entity.Department;
import com.lunadev.worktime.work.dto.WorkListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface DepartmentMapper {

    /**
     * 해당 회사의 모든 부서를 쿼리하는 메서드
     * @param verifyCode 회사 인증 코드
     * @return 부서 정보 리스트
     */
    List<DeptDto> getDeptList(@Param("verifyCode") String verifyCode);

}
