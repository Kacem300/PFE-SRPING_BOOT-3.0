package com.pfe.pfekacemjwt;
import com.pfe.pfekacemjwt.Util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
@SpringBootTest
@AutoConfigureMockMvc
public class PreAuthorizeTest {
    @MockBean
    private UserDetailsService userDetailsService;

    private String jwtToken;

    @BeforeEach
    public void setUp() {
        UserDetails mockUserDetails = User.withUsername("mockUser")
                .password("mockPassword")
                .roles("CORRECT_ROLE")
                .build();

        Mockito.when(userDetailsService.loadUserByUsername("mockUser")).thenReturn(mockUserDetails);

        // Generate a mock JWT token for the mock user.
        // This will depend on how you're generating JWT tokens in your application.
        // For example, you might use a JWT utility class like this:
        JwtUtil jwtUtil = new JwtUtil();
        jwtToken = jwtUtil.generatedToken(mockUserDetails);
    }
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "Admin_ROLE")
    public void testPreAuthorizeWithWrongRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getOrderDetails"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "User_ROLE")
    public void testPreAuthorizeWithCorrectRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getAllOrderDetails/{status}"))
                .andExpect(status().isOk());
    }
}