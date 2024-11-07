package ru.gb.timesheet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Описание структуры json-ответа на REST-запросы.
 * Т.е. запросы, ответ на которые - JSON.
 */
@Data
@Entity
@Table(name = "timesheet")
public class Timesheet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;
  private Long projectId;
  private Integer minutes;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate createdAt;

}
