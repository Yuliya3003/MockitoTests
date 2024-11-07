package ru.gb.timesheet.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.TimesheetService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TimesheetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TimesheetService timesheetService;

    @InjectMocks
    private TimesheetController timesheetController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(timesheetController).build();
    }

    @Test
    public void testGetTimesheetById_Found() throws Exception {
        Timesheet timesheet = new Timesheet();
        timesheet.setId(1L);
        timesheet.setCreatedAt(LocalDate.now());

        given(timesheetService.findById(anyLong())).willReturn(Optional.of(timesheet));

        mockMvc.perform(get("/timesheets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.createdAt").value(timesheet.getCreatedAt().toString()));

        verify(timesheetService).findById(1L);
    }


    @Test
    public void testGetAllTimesheets() throws Exception {
        Timesheet timesheet1 = new Timesheet();
        timesheet1.setId(1L);
        timesheet1.setCreatedAt(LocalDate.now());

        Timesheet timesheet2 = new Timesheet();
        timesheet2.setId(2L);
        timesheet2.setCreatedAt(LocalDate.now().plusDays(1));

        List<Timesheet> timesheets = Arrays.asList(timesheet1, timesheet2);

        given(timesheetService.findAll(null, null)).willReturn(timesheets);

        mockMvc.perform(get("/timesheets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(timesheetService).findAll(null, null);
    }

    @Test
    public void testCreateTimesheet() throws Exception {
        Timesheet timesheet = new Timesheet();
        timesheet.setId(1L);
        timesheet.setCreatedAt(LocalDate.now());

        given(timesheetService.create(any(Timesheet.class))).willReturn(timesheet);

        mockMvc.perform(post("/timesheets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"createdAt\": \"2024-11-07\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.createdAt").value(timesheet.getCreatedAt().toString()));

        verify(timesheetService).create(any(Timesheet.class));
    }

    @Test
    public void testDeleteTimesheet() throws Exception {
        mockMvc.perform(delete("/timesheets/1"))
                .andExpect(status().isNoContent());

        verify(timesheetService).delete(1L);
    }


}
