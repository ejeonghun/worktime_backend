package com.lunadev.worktime.schedule.controller;

import com.lunadev.worktime.schedule.dto.ScheduleRequestDto;
import com.lunadev.worktime.schedule.entity.Schedule;
import com.lunadev.worktime.schedule.service.ScheduleService;
import com.lunadev.worktime.utils.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
public class ScheduleApiController {
    private final ScheduleService scheduleService;

    @GetMapping("list")
    public ResultDTO<Object> getSchedules(@RequestParam String date) {
        return scheduleService.getSchedules(date);
    }

    @PostMapping("create")
    public ResultDTO<Object> createSchedule(@RequestBody ScheduleRequestDto dto) {
        return scheduleService.createSchedule(dto);
    }

    @PostMapping("update")
    public ResultDTO<Object> updateSchedule(@RequestBody ScheduleRequestDto dto) {
        return scheduleService.updateSchedule(dto);
    }

    @GetMapping("delete")
    public ResultDTO<Object> deleteSchedule(@RequestBody Long scheduleId) {
        return scheduleService.deleteSchedule(scheduleId);
    }

    @GetMapping("info")
    public ResultDTO<Object> getScheduleInfo(@RequestParam Long scheduleId) {
        return scheduleService.getSchedule(scheduleId);
    }
}
