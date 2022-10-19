package com.wtt.timelog.mapper;

import com.wtt.timelog.dto.TimeLogDto;
import com.wtt.timelog.entity.TimeLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring")
@Component
public interface TimeLogMapper {
    @Mapping(target = "workingHours", expression = "java(getWorkingHours(timeLog))")
    @Mapping(target = "workingMinutes", expression = "java(getWorkingMinutes(timeLog))")
    @Mapping(target = "workingSeconds", expression = "java(getWorkingSeconds(timeLog))")
    TimeLogDto toDto(TimeLog timeLog);

    TimeLog toEntity(TimeLogDto timeLogDto);

    default int getWorkingHours(TimeLog timeLog) {
        return Long.valueOf(ChronoUnit.HOURS.between(getStartDateTime(timeLog), getEndDateTime(timeLog))).intValue();
    }

    default int getWorkingMinutes(TimeLog timeLog) {
        return Long.valueOf(ChronoUnit.MINUTES.between(getStartDateTime(timeLog), getEndDateTime(timeLog))).intValue();
    }

    default int getWorkingSeconds(TimeLog timeLog) {
        return Long.valueOf(ChronoUnit.SECONDS.between(getStartDateTime(timeLog), getEndDateTime(timeLog))).intValue();
    }

    default LocalDateTime getStartDateTime(TimeLog timeLog) {
        return timeLog.getStartDate().atTime(timeLog.getStartTime());
    }

    default LocalDateTime getEndDateTime(TimeLog timeLog) {
        return timeLog.getEndDate() == null ? LocalDateTime.now() : timeLog.getEndDate().atTime(timeLog.getEndTime());
    }
}
