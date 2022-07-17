package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    ClinicService clinicService;

    @InjectMocks
    VetController vetController;

    @Test
    void showVetList() {
//        given
        List<Vet> vetList = new ArrayList<>();

        given(clinicService.findVets()).willReturn(vetList);
//         when
        String result = vetController.showVetList(new HashMap<>());
//        then
        assertThat(result).isEqualToIgnoringCase("vets/vetList");
        then(clinicService).should(times(1)).findVets();
    }

    @Test
    void showResourcesVetList() {
//        given
        List<Vet> vetList = new ArrayList<>();

        given(clinicService.findVets()).willReturn(vetList);
//         when
        List<Vet> result = vetController.showResourcesVetList().getVetList();
//        then
        assertThat(result).isEqualTo(vetList);
        assertThat(result.size()).isEqualTo(vetList.size());
        then(clinicService).should(times(1)).findVets();
    }
}