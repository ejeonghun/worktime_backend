package com.lunadev.worktime.schedule.service.Impl;

import com.lunadev.worktime.Enum.ApiResponseCode;
import com.lunadev.worktime.Enum.ScheduleType;
import com.lunadev.worktime.Enum.WorkType;
import com.lunadev.worktime.company.entity.Company;
import com.lunadev.worktime.company.entity.Department;
import com.lunadev.worktime.member.entity.Member;
import com.lunadev.worktime.schedule.dto.ScheduleDto;
import com.lunadev.worktime.schedule.dto.ScheduleRequestDto;
import com.lunadev.worktime.schedule.entity.Schedule;
import com.lunadev.worktime.schedule.repository.ScheduleMapper;
import com.lunadev.worktime.schedule.repository.ScheduleRepository;
import com.lunadev.worktime.schedule.service.ScheduleService;
import com.lunadev.worktime.utils.AuthUserInfo;
import com.lunadev.worktime.utils.ResultDTO;
import com.lunadev.worktime.work.entity.Work;
import com.lunadev.worktime.work.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final WorkRepository workRepository;
    private final AuthUserInfo authUserInfo;
    private final ScheduleMapper scheduleMapper;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ResultDTO<Object> createSchedule(ScheduleRequestDto dto) {
        Company company = authUserInfo.getAuthenticatedCompany(); // 사용자 회사정보
        Member member = authUserInfo.getAuthenticatedMember();    // 사용자 정보
        Department dept = member.getDepartment();                 // 사용자 부서

        // 만약 휴가라면 휴가 일정을 work 테이블에 추가
        if (dto.getScheduleType() == ScheduleType.VACATION) {
            Stream.iterate(dto.getStartDate().toLocalDate(), date -> date.plusDays(1))
                    .limit(dto.getEndDate().toLocalDate().toEpochDay() - dto.getStartDate().toLocalDate().toEpochDay() + 1)
                    .forEach(date -> {
                        Work work = Work.builder()
                                .member(member)
                                .company(company)
                                .date(date)
                                .workType(WorkType.VACATION)
                                .build();
                        workRepository.save(work);
                    });
        }
        // ScheduleRequestDto에 들어온 값 저장
        Schedule schedule = Schedule.builder()
                .scheduleName(dto.getScheduleName())
                .scheduleType(dto.getScheduleType())
                .scheduleDetails(dto.getScheduleDetails())
                .startDate(dto.getStartDate())
                .department(dept)
                .member(member)
                .company(company)
                        .build();

        if (dto.getEndDate() != null) {
            schedule.setEndDate(dto.getEndDate());
        }

        try {
            Schedule saved = scheduleRepository.save(schedule);
            ScheduleDto scheduleDto = modelMapper.map(saved, ScheduleDto.class);
            return ResultDTO.of(true, ApiResponseCode.CREATED.getCode(), ApiResponseCode.CREATED.getMessage(), scheduleDto);
        } catch (Exception e) {
            return ResultDTO.of(false, ApiResponseCode.FAILED.getCode(), "오류 발생: " + e.getMessage(), null);
        }
    }


    @Transactional
    @Override
    public ResultDTO<Object> updateSchedule(ScheduleRequestDto dto) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(dto.getScheduleId());
        if (scheduleOptional.isEmpty()) {
            return ResultDTO.of(false, ApiResponseCode.NOT_FOUND.getCode(), "해당 일정을 찾을 수 없습니다.", null);
        }

        Schedule schedule = scheduleOptional.get();
        schedule.setScheduleName(dto.getScheduleName());
        schedule.setScheduleType(dto.getScheduleType());
        schedule.setScheduleDetails(dto.getScheduleDetails());
        schedule.setStartDate(dto.getStartDate());

        if (dto.getEndDate() != null) {
            schedule.setEndDate(dto.getEndDate());
        }

        try {
            Schedule updated = scheduleRepository.save(schedule);
            return ResultDTO.of(true, ApiResponseCode.UPDATED.getCode(), ApiResponseCode.UPDATED.getMessage(), updated);
        } catch (Exception e) {
            return ResultDTO.of(false, ApiResponseCode.FAILED.getCode(), "오류 발생: " + e.getMessage(), null);
        }
    }

    @Transactional
    @Override
    public ResultDTO<Object> deleteSchedule(Long id) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (scheduleOptional.isEmpty()) {
            return ResultDTO.of(false, ApiResponseCode.NOT_FOUND.getCode(), "해당 일정을 찾을 수 없습니다.", null);
        }

        try {
            scheduleRepository.delete(scheduleOptional.get());
            return ResultDTO.of(true, ApiResponseCode.DELETED.getCode(), ApiResponseCode.DELETED.getMessage(), null);
        } catch (Exception e) {
            return ResultDTO.of(false, ApiResponseCode.FAILED.getCode(), "오류 발생: " + e.getMessage(), null);
        }
    }

    @Override
    public ResultDTO<Object> getSchedule(Long id) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (scheduleOptional.isEmpty()) {
            return ResultDTO.of(false, ApiResponseCode.NOT_FOUND.getCode(), "해당 일정을 찾을 수 없습니다.", null);
        }
        return ResultDTO.of(true, ApiResponseCode.SUCCESS.getCode(), ApiResponseCode.SUCCESS.getMessage(), scheduleOptional.get());
    }

    @Transactional(readOnly = true)
    @Override
    public ResultDTO<Object> getSchedules(String date) {
        Company company = authUserInfo.getAuthenticatedCompany();
        Long companyId = company.getCompanyId();
        try {
            List<ScheduleDto> scheduleList = scheduleMapper.getScheduleList(date, companyId);
            return ResultDTO.of(true, ApiResponseCode.SUCCESS.getCode(), "스케줄 조회 성공", scheduleList);
        } catch (Exception e) {
            return ResultDTO.of(false, ApiResponseCode.FAILED.getCode(), "오류 발생: " + e.getMessage(), null);
        }
    }

}