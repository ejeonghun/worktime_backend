package com.lunadev.worktime.schedule.service.Impl;

import com.lunadev.worktime.Enum.ScheduleType;
import com.lunadev.worktime.Enum.WorkType;
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
    public void createSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);

        if (schedule.getScheduleType() == ScheduleType.VACATION) {
            Stream.iterate(schedule.getStartDate().toLocalDate(), date -> date.plusDays(1))
                    .limit(schedule.getEndDate().toLocalDate().toEpochDay() - schedule.getStartDate().toLocalDate().toEpochDay() + 1)
                    .forEach(date -> {
                        Work work = Work.builder()
                                .member(schedule.getMember())
                                .company(schedule.getCompany())
                                .date(date)
                                .workType(WorkType.VACATION)
                                .build();
                        workRepository.save(work);
                    });
        }
    }
}