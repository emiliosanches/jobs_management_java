package br.com.emiliosanches.jobs_management.modules.company.controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.emiliosanches.jobs_management.modules.company.dto.CreateJobDTO;
import br.com.emiliosanches.jobs_management.modules.company.entity.CompanyEntity;
import br.com.emiliosanches.jobs_management.modules.company.repositories.CompanyRepository;
import br.com.emiliosanches.jobs_management.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void shouldBeAbleToCreateAJob() throws Exception {
        var company = CompanyEntity.builder().description("company description").email("email@company.com")
                .password("1234567890").name("Company Name").build();

        company = companyRepository.saveAndFlush(company);

        CreateJobDTO dto = CreateJobDTO.builder().benefits("Benefits test").description("Description").level("Jr")
                .build();

        mvc
                .perform(
                        MockMvcRequestBuilders.post("/jobs").contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(dto))
                                .header("Authorization", "Bearer " + TestUtils.generateToken(company.getId(),
                                        "dummySecret")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldNotBeAbleToCreateAJobIfCompanyNotFound() throws Exception {
        CreateJobDTO dto = CreateJobDTO.builder().benefits("Benefits test").description("Description").level("Jr")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/jobs").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(dto))
                .header("Authorization", "Bearer " + TestUtils.generateToken(UUID.randomUUID(), "dummySecret")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        System.out.println("not catched");
    }
}
