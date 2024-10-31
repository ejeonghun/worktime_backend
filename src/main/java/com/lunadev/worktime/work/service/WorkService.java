package com.lunadev.worktime.work.service;

import com.lunadev.worktime.utils.ResultDTO;
import com.lunadev.worktime.work.dto.LocationDto;

import java.util.Date;

public interface WorkService {

    ResultDTO<Object> checkIn(LocationDto locationDto);

    ResultDTO<Object> checkOut(LocationDto locationDto);

    ResultDTO<Object> workList(String date);
}
