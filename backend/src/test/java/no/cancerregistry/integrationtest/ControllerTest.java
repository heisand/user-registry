package no.cancerregistry.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.cancerregistry.exception.WrongVersionException;
import no.cancerregistry.service.UserService;
import no.cancerregistry.model.UserDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static UserService userService;

    @BeforeAll
    static void setup() {
        userService = mock(UserService.class);
    }

    @Test
    public void whenCreateNewUser_thenReturn201() throws Exception {
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
             .andExpect(status().isCreated());
    }

    @Test
    public void givenAbundantUser_whenUpdateUser_thenReturn404() throws Exception {
        UserDTO user = new UserDTO(null, Optional.of(1), "");
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(put("/api/users/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenWrongVersion_whenUpdateUser_thenReturn400() throws Exception {
        UserDTO user = new UserDTO(null, null, "");
        String userJson = objectMapper.writeValueAsString(user);

        doThrow(new WrongVersionException("")).when(userService).updateUser(any(), any());

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());
    }
}