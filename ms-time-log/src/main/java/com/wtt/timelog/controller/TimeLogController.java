package com.wtt.timelog.controller;

import com.wtt.commondependencies.dto.ResponseDto;
import com.wtt.timelog.dto.SearchRequest;
import com.wtt.timelog.service.TimeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timeLogs")
public class TimeLogController {
    private final TimeLogService timeLogService;

    @GetMapping("/{id}")
    public ResponseDto getTimeLog(@PathVariable Long id) {
        return timeLogService.getTimeLog(id);
    }

    @PostMapping("/punchIn")
    public ResponseDto punchIn() {
        return timeLogService.punchIn();
    }

    @PutMapping("/punchOut")
    public ResponseDto punchOut() {
        return timeLogService.punchOut();
    }

    @GetMapping("/search")
    public ResponseDto search(@RequestBody SearchRequest searchRequest) {
        return timeLogService.search(searchRequest.getEmployeeIds(), searchRequest.getStartDate(), searchRequest.getEndDate());
    }
}
