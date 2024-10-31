package com.lunadev.worktime.work.service;

import com.lunadev.worktime.utils.ResultDTO;
import com.lunadev.worktime.work.dto.LocationDto;

public interface WorkService {

    ResultDTO<Object> checkIn(LocationDto locationDto);

    ResultDTO<Object> checkOut(LocationDto locationDto);

    ResultDTO<Object> workList(); // 해당 회사의 당일 기록


}
