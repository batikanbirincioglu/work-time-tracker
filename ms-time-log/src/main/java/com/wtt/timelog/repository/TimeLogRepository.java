package com.wtt.timelog.repository;

import com.wtt.timelog.entity.TimeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeLogRepository extends JpaRepository<TimeLog, Long> {
    public Optional<TimeLog> findByEmployeeIdAndStartDate(Long employeeId, LocalDate startDate);
    public Optional<List<TimeLog>> findByEmployeeIdInAndStartDateBetween(List<Long> employeeIds, LocalDate startDate, LocalDate endDate);
    public Optional<List<TimeLog>> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
}
