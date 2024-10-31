package com.lunadev.worktime.schedule.repository;

import com.lunadev.worktime.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
