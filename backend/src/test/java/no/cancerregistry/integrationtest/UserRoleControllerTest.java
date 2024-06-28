package no.cancerregistry.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.cancerregistry.model.UserRoleDTO;
import no.cancerregistry.service.UserRoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRoleService userRoleService;

    @Test
    public void testCreateUserRole_returns201() throws Exception {
        UserRoleDTO userRole = new UserRoleDTO(
                null, null, 1L, 1L, 1L,
                ZonedDateTime.now(), ZonedDateTime.now().plusDays(3)
        );

        String userJson = objectMapper.writeValueAsString(userRole);

        when(userRoleService.createUserRole(userRole)).thenReturn(userRole);

        mockMvc.perform(post("/api/user-roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateUserRole_returns400() throws Exception {
        UserRoleDTO userRole = new UserRoleDTO(
                null, null, 1L, 1L, 1L,
                ZonedDateTime.now(), ZonedDateTime.now().minusDays(3)
        );

        String userJson = objectMapper.writeValueAsString(userRole);

        mockMvc.perform(post("/api/user-roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());
    }
}