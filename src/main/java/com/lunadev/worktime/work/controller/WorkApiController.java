package com.lunadev.worktime.work.controller;

import com.lunadev.worktime.utils.ResultDTO;
import com.lunadev.worktime.work.dto.LocationDto;
import com.lunadev.worktime.work.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/work")
public class WorkApiController {
    private final WorkService workService;

    @PostMapping("checkIn")
    public ResultDTO<Object> checkIn(@RequestBody LocationDto locationDto) {
        return workService.checkIn(locationDto);
    }

    @PostMapping("checkOut")
    public ResultDTO<Object> checkOut(@RequestBody LocationDto locationDto) {
        return workService.checkOut(locationDto);
    }

    @GetMapping("list")
    public ResultDTO<Object> workList() {
        return workService.workList();
    }
}
