package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    @Autowired
    OwnerController ownerController;
    
    @Autowired
    ClinicService clinicService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }


    @Test
    void testFindByNameNotFound() throws Exception {
        mockMvc.perform(get("/owners").param("lastName", "don't find me!"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void testFindByNameReturnOneName() throws Exception{
        Owner owner = new Owner();
        owner.setLastName("test");
        owner.setId(1);
        given(clinicService.findOwnerByLastName("test")).willReturn(List.of(owner));

        mockMvc.perform(get("/owners").param("lastName", "test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + owner.getId()));
    }

    @Test
    void testFindByNameReturnList() throws Exception {
        Owner owner = new Owner();
        owner.setLastName("test");
        owner.setId(1);
        Owner owner2 = new Owner();
        owner2.setLastName("test");
        owner2.setId(2);

        given(clinicService.findOwnerByLastName("")).willReturn(List.of(owner, owner2));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"));

    }

    @Test
    void initCreationFormTest() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    void tempTest() {

    }
}