package com.wtt.timelog.mapper;

import com.wtt.timelog.dto.TimeLogDto;
import com.wtt.timelog.entity.TimeLog;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TimeLogMapper {
    TimeLogDto toDto(TimeLog timeLog);

    TimeLog toEntity(TimeLogDto timeLogDto);
}
