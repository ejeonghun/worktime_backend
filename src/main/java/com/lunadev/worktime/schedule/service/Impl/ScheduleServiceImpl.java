package com.lunadev.worktime.schedule.service.Impl;

import com.lunadev.worktime.Enum.ScheduleType;
import com.lunadev.worktime.Enum.WorkType;
import com.lunadev.worktime.schedule.dto.ScheduleRequestDto;
import com.lunadev.worktime.schedule.entity.Schedule;
import com.lunadev.worktime.schedule.repository.ScheduleRepository;
import com.lunadev.worktime.work.entity.Work;
import com.lunadev.worktime.work.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl {

    private final ScheduleRepository scheduleRepository;
    private final WorkRepository workRepository;

    @Transactional
    public void createSchedule(ScheduleRequestDto dto) {
        // scheduleRepository.save(dto);


        // 만약 휴가라면 휴가 일정을 work 테이블에 추가
        if (dto.getScheduleType() == ScheduleType.VACATION) {
            Stream.iterate(dto.getStartDate().toLocalDate(), date -> date.plusDays(1))
                    .limit(dto.getEndDate().toLocalDate().toEpochDay() - dto.getStartDate().toLocalDate().toEpochDay() + 1)
                    .forEach(date -> {
                        Work work = Work.builder()
                                .member(dto.getMember())
                                .company(dto.getCompany())
                                .date(date)
                                .workType(WorkType.VACATION)
                                .build();
                        workRepository.save(work);
                    });
        }
    }
}