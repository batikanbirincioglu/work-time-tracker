package com.wtt.timelog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeLogDto {
    private Long id;
    private Long employeeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
