package ee.ivar.smit.proovitoo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class AppSecurityIntegTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void shouldRejectUnauthorizedRequest() throws Exception {
        // when
        ResultActions response = mockMvc.perform(get("/api/books/search?searchTerm=abc"));

        // then
        response.andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldAllowAuthorizedRequests() throws Exception {
        // when
        ResultActions response = mockMvc.perform(get("/api/books/search?searchTerm=abc"));

        // then
        response.andExpect(status().isOk());
    }
}
