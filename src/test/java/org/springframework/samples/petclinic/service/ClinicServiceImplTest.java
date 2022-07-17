package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    @Mock
    PetRepository petRepository;

    @Mock
    VetRepository vetRepository;

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    ClinicServiceImpl clinicService;

    @DisplayName("test findPetTypes")
    @Test
    void findPetTypes() {
//        given
        List<PetType> petTypeList = new ArrayList<>();
        given(petRepository.findPetTypes()).willReturn(petTypeList);
//        when
        List<PetType> result = new ArrayList<>(clinicService.findPetTypes());
//        then
        assertThat(result).isEqualTo(petTypeList);
        assertThat(result.size()).isEqualTo(petTypeList.size());
        then(petRepository).should(times(1)).findPetTypes();
        then(petRepository).shouldHaveNoMoreInteractions();

    }

//    @DisplayName("test findPetTypes throws DataAccessException")
//    @Test
//    void findPetTypesError() {
////        given
////        List<PetType> petTypeList = new ArrayList<>();
////        given(petRepository.findPetTypes()).willThrow(DataAccessException.class);
//        when(petRepository.findPetTypes()).thenThrow(DataAccessException.class);
////        willThrow(DataAccessException.class).given(petRepository).findPetTypes();
////        when
////        then
//        assertThrows(DataAccessException.class, () -> clinicService.findPetTypes());
////        assertThatThrownBy(() -> clinicService.findPetTypes()).isInstanceOf(DataAccessException.class);
//
////        then(petRepository).should(times(1)).findPetTypes();
////        then(petRepository).shouldHaveNoMoreInteractions();
//    }
}