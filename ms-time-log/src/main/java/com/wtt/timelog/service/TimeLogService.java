package com.wtt.timelog.service;

import com.wtt.commondependencies.dto.ResponseDto;
import com.wtt.commondependencies.dto.StatusCode;
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

@Service
@RequiredArgsConstructor
public class TimeLogService {
    private final TimeLogMapper timeLogMapper;
    private final TimeLogRepository timeLogRepository;

    @Transactional
    public ResponseDto punchIn() {
        Long employeeId = null; // get from authenticatio token
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        TimeLog timeLog = timeLogRepository.findByEmployeeIdAndStartDate(employeeId, currentDate).orElse(null);
        if (timeLog != null) {
            return ResponseDto.builder().statusCode(StatusCode.FAIL).description("You already punched in.").build();
        } else {
            timeLog = TimeLog.builder().employeeId(employeeId).startDate(currentDate).startTime(currentTime).build();
            timeLogRepository.save(timeLog);
            return ResponseDto.builder().statusCode(StatusCode.SUCCESS).payload(timeLogMapper.toDto(timeLog)).build();
        }
    }

    @Transactional
    public ResponseDto punchOut() {
        Long employeeId = null; // get from authenticatio token
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        TimeLog timeLog = timeLogRepository.findByEmployeeIdAndStartDate(employeeId, currentDate).orElse(null);
        if (timeLog == null) {
            return ResponseDto.builder().statusCode(StatusCode.FAIL).description("You can not punch out because you didn't punch in before.").build();
        } else if (timeLog.getEndDate() != null) {
            return ResponseDto.builder().statusCode(StatusCode.FAIL).description("You have already punched out before.").build();
        } else {
            timeLog.setEndDate(currentDate);
            timeLog.setEndTime(currentTime);
            timeLogRepository.save(timeLog);
            return ResponseDto.builder().statusCode(StatusCode.SUCCESS).payload(timeLogMapper.toDto(timeLog)).build();
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto getTimeLog(Long id) {
        TimeLog timeLog = timeLogRepository.findById(id).orElse(null);
        TimeLogDto timeLogDto = timeLogMapper.toDto(timeLog);
        return ResponseDto.builder().statusCode(StatusCode.SUCCESS).payload(timeLogDto).build();
    }

    @Transactional(readOnly = true)
    public ResponseDto search(List<Long> employeeIds, LocalDate startDate, LocalDate endDate) {
        List<TimeLog> timeLogs;
        if (!CollectionUtils.isEmpty(employeeIds)) {
            timeLogs = timeLogRepository.findByEmployeeIdInAndStartDateBetween(employeeIds, startDate, endDate).orElse(new ArrayList<>());
        } else {
            timeLogs = timeLogRepository.findByStartDateBetween(startDate, endDate).orElse(new ArrayList<>());
        }
        List<TimeLogDto> timeLogDtos = timeLogs.stream().map(timeLogMapper::toDto).collect(Collectors.toList());
        return ResponseDto.builder().statusCode(StatusCode.SUCCESS).payload(timeLogDtos).build();
    }
}
