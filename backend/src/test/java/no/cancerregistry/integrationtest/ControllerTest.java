package no.cancerregistry.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.cancerregistry.service.UserService;
import no.cancerregistry.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void testGetUserById_UserNotFound() throws Exception {
        UserDTO user = new UserDTO(null, null, "");
        String userJson = objectMapper.writeValueAsString(user);

        UserDTO mockedUser = new UserDTO(
                Optional.of(1L),
                Optional.of(1),
                "");
        when(userService.createUser(any(UserDTO.class))).thenReturn(mockedUser);

        mockMvc.perform(post("/api/users")
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(userJson))
             .andExpect(status().isNotFound());
    }
}