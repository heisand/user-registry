package no.cancerregistry.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.cancerregistry.CancerRegistryController;
import no.cancerregistry.CancerRegistryService;
import no.cancerregistry.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CancerRegistryController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CancerRegistryService cancerRegistryService;

    @Test
    public void testGetUserById_UserNotFound() throws Exception {
        UserDTO user = new UserDTO(null, null, "");
        String userJson = objectMapper.writeValueAsString(user);

     mockMvc.perform(post("/users")
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(userJson))
             .andExpect(status().isNotFound());
    }
}