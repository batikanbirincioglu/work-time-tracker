package com.wtt.timelog.controller;

import com.wtt.commondependencies.dto.ResponseDto;
import com.wtt.timelog.dto.TimeLogDto;
import com.wtt.timelog.service.TimeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timeLogs")
public class TimeLogController {
    private final TimeLogService timeLogService;

    @GetMapping("/{id}")
    public ResponseDto<TimeLogDto> getTimeLog(@PathVariable Long id) {
        return timeLogService.getTimeLog(id);
    }

    @PostMapping("/punchIn")
    public ResponseDto<TimeLogDto> punchIn() {
        return timeLogService.punchIn();
    }

    @PutMapping("/punchOut")
    public ResponseDto<TimeLogDto> punchOut() {
        return timeLogService.punchOut();
    }

    @PreAuthorize("isManager()")
    @GetMapping("/search")
    public ResponseDto<List<TimeLogDto>> search(@RequestParam(name = "employeeIds", required = false) List<Long> employeeIds,
                                                @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return timeLogService.search(employeeIds, startDate, endDate);
    }
}
