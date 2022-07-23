package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    ClinicService clinicService;

    @Mock
    Map<String, Object> model;

    @InjectMocks
    VetController vetController;

    List<Vet> vetList = new ArrayList<>();

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
//        given
        vetList.add(new Vet());

        given(clinicService.findVets()).willReturn(vetList);

        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
    }

    @Test
    void showVetList() {
//         when
        String result = vetController.showVetList(model);
//        then
        assertThat(result).isEqualToIgnoringCase("vets/vetList");
        then(clinicService).should(times(1)).findVets();
        then(clinicService).shouldHaveNoMoreInteractions();
        then(model).should().put(anyString(), any());
        then(model).shouldHaveNoMoreInteractions();
    }

    @Test
    void testControllerShowVetList() throws Exception {
        mockMvc.perform(get("/vets.html"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vets"))
                .andExpect(view().name("vets/vetList"));
    }

    @Test
    void showResourcesVetList() {
//         when
        List<Vet> result = vetController.showResourcesVetList().getVetList();
//        then
        assertThat(result).isEqualTo(vetList);
        assertThat(result.size()).isEqualTo(vetList.size());
        then(clinicService).should(times(1)).findVets();
        then(clinicService).shouldHaveNoMoreInteractions();
    }
}