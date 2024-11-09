package com.lunadev.worktime.schedule.service;

import com.lunadev.worktime.schedule.dto.ScheduleRequestDto;
import com.lunadev.worktime.schedule.entity.Schedule;
import com.lunadev.worktime.utils.ResultDTO;

public interface ScheduleService {

    ResultDTO<Object> createSchedule(ScheduleRequestDto dto);
    ResultDTO<Object> updateSchedule(ScheduleRequestDto dto);
    ResultDTO<Object> deleteSchedule(Long id);
    ResultDTO<Object> getSchedule(Long id);
    ResultDTO<Object> getSchedules(String date);
}
