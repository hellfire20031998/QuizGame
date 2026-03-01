package com.hellFire.QuizGame.controllers;

import com.hellFire.QuizGame.dto.DomainDto;
import com.hellFire.QuizGame.dto.request.CreateDomainRequest;
import com.hellFire.QuizGame.entity.Domain;
import com.hellFire.QuizGame.services.impl.DomainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DomainController.class)
@AutoConfigureMockMvc(addFilters = false)
class DomainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DomainService domainService;

    @Test
    void create_shouldReturnCreatedDomain() throws Exception {
        CreateDomainRequest request = new CreateDomainRequest();
        request.setName("Java");

        Domain domain = new Domain();
        domain.setId(1L);
        domain.setName("Java");

        DomainDto dto = new DomainDto();
        dto.setId(1L);
        dto.setName("Java");

        when(domainService.createDomain(ArgumentMatchers.any(CreateDomainRequest.class))).thenReturn(domain);
        when(domainService.toDto(domain)).thenReturn(dto);

        mockMvc.perform(post("/domains/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.name").value("Java"))
                .andExpect(jsonPath("$.message").value("Domain created successfully"));
    }

    @Test
    void getAll_shouldReturnListOfDomains() throws Exception {
        Domain domain = new Domain();
        domain.setId(1L);
        domain.setName("Java");

        DomainDto dto = new DomainDto();
        dto.setId(1L);
        dto.setName("Java");

        when(domainService.getAllDomains()).thenReturn(List.of(domain));
        when(domainService.toDtoList(List.of(domain))).thenReturn(List.of(dto));

        mockMvc.perform(get("/domains/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Java"));
    }

    @Test
    void searchDomains_shouldReturnRelatedDomains() throws Exception {
        DomainDto dto = new DomainDto();
        dto.setId(1L);
        dto.setName("Java");

        when(domainService.getRelatedDomains("jav")).thenReturn(List.of(dto));

        mockMvc.perform(get("/domains/search")
                        .param("query", "jav"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("Java"))
                .andExpect(jsonPath("$.message").value("Related domains fetched successfully"));
    }
}

