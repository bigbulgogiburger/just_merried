package com.weddingmate.domain.schedule.repository;

import com.weddingmate.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUserIdAndStartAtBetweenOrderByStartAtAsc(Long userId, LocalDateTime from, LocalDateTime to);
}
