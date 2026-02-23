package com.weddingmate.api.schedule;

import com.weddingmate.api.schedule.dto.ScheduleCreateRequest;
import com.weddingmate.api.schedule.dto.ScheduleResponse;
import com.weddingmate.api.schedule.dto.ScheduleUpdateRequest;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.schedule.entity.Schedule;
import com.weddingmate.domain.schedule.repository.ScheduleRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScheduleResponse create(Long userId, ScheduleCreateRequest request) {
        User user = getUser(userId);
        Schedule schedule = scheduleRepository.save(Schedule.builder()
                .user(user)
                .title(request.title())
                .description(request.description())
                .startAt(request.startAt())
                .endAt(request.endAt())
                .allDay(request.allDay() != null && request.allDay())
                .sharedWithCouple(request.sharedWithCouple() == null || request.sharedWithCouple())
                .reminderMinutes(request.reminderMinutes())
                .build());
        return ScheduleResponse.from(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> findByRange(Long userId, LocalDateTime from, LocalDateTime to) {
        LocalDateTime fromDate = from != null ? from : LocalDateTime.now().minusMonths(1);
        LocalDateTime toDate = to != null ? to : LocalDateTime.now().plusMonths(1);
        return scheduleRepository.findByUserIdAndStartAtBetweenOrderByStartAtAsc(userId, fromDate, toDate)
                .stream().map(ScheduleResponse::from).toList();
    }

    @Transactional
    public ScheduleResponse update(Long userId, Long scheduleId, ScheduleUpdateRequest request) {
        Schedule schedule = getOwnedSchedule(userId, scheduleId);
        schedule.update(
                request.title(),
                request.description(),
                request.startAt(),
                request.endAt(),
                request.allDay(),
                request.sharedWithCouple(),
                request.reminderMinutes(),
                request.status()
        );
        return ScheduleResponse.from(schedule);
    }

    @Transactional
    public void delete(Long userId, Long scheduleId) {
        Schedule schedule = getOwnedSchedule(userId, scheduleId);
        scheduleRepository.delete(schedule);
    }

    private Schedule getOwnedSchedule(Long userId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SCHEDULE_NOT_FOUND, "일정을 찾을 수 없습니다."));
        if (!schedule.getUser().getId().equals(userId)) {
            throw new NotFoundException(ErrorCode.SCHEDULE_NOT_FOUND, "일정을 찾을 수 없습니다.");
        }
        return schedule;
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }
}
