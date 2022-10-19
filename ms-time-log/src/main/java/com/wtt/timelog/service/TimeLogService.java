package com.wtt.timelog.service;

import com.wtt.commondependencies.dto.ResponseDto;
import com.wtt.commondependencies.dto.StatusCode;
import com.wtt.commondependencies.exception.BusinessException;
import com.wtt.commondependencies.utils.SecurityUtils;
import com.wtt.timelog.TimeLogError;
import com.wtt.timelog.dto.TimeLogDto;
import com.wtt.timelog.entity.TimeLog;
import com.wtt.timelog.mapper.TimeLogMapper;
import com.wtt.timelog.repository.TimeLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wtt.timelog.TimeLogError.ALREADY_PUNCHED_IN_TODAY;

@Service
@RequiredArgsConstructor
public class TimeLogService {
    private final TimeLogMapper timeLogMapper;
    private final TimeLogRepository timeLogRepository;

    @Transactional
    public ResponseDto<TimeLogDto> punchIn() {
        Long employeeId = SecurityUtils.getUserId();
        String employeeName = SecurityUtils.getName();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        timeLogRepository.findByEmployeeIdAndStartDate(employeeId, currentDate).ifPresent(tl -> {throw new BusinessException(TimeLogError.ALREADY_PUNCHED_IN_TODAY);});
        TimeLog timeLog = TimeLog.builder()
                .employeeId(employeeId).employeeName(employeeName)
                .startDate(currentDate).startTime(currentTime)
                .build();
        timeLogRepository.save(timeLog);
        return ResponseDto.from(StatusCode.SUCCESS, timeLogMapper.toDto(timeLog));
    }

    @Transactional
    public ResponseDto<TimeLogDto> punchOut() {
        Long employeeId = SecurityUtils.getUserId();
        String employeeName = SecurityUtils.getName();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        TimeLog timeLog = timeLogRepository.findByEmployeeIdAndStartDate(employeeId, currentDate).orElseThrow(() -> new BusinessException(TimeLogError.NO_PUNCHED_IN_TODAY));
        if (timeLog.getEndDate() != null) {
            throw new BusinessException(TimeLogError.ALREADY_PUNCHED_OUT_TODAY);
        } else {
            timeLog.setEndDate(currentDate);
            timeLog.setEndTime(currentTime);
            timeLogRepository.save(timeLog);
            return ResponseDto.from(StatusCode.SUCCESS, timeLogMapper.toDto(timeLog));
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<TimeLogDto> getTimeLog(Long id) {
        TimeLog timeLog = timeLogRepository.findById(id).orElse(null);
        TimeLogDto timeLogDto = timeLogMapper.toDto(timeLog);
        return ResponseDto.from(StatusCode.SUCCESS, timeLogDto);
    }

    @Transactional(readOnly = true)
    public ResponseDto<List<TimeLogDto>> search(List<Long> employeeIds, LocalDate startDate, LocalDate endDate) {
        List<TimeLog> timeLogs;
        if (!CollectionUtils.isEmpty(employeeIds)) {
            timeLogs = timeLogRepository.findByEmployeeIdInAndStartDateBetween(employeeIds, startDate, endDate).orElse(new ArrayList<>());
        } else {
            timeLogs = timeLogRepository.findByStartDateBetween(startDate, endDate).orElse(new ArrayList<>());
        }
        List<TimeLogDto> timeLogDtos = timeLogs.stream().map(timeLogMapper::toDto).collect(Collectors.toList());
        return ResponseDto.from(StatusCode.SUCCESS, timeLogDtos);
    }
}
