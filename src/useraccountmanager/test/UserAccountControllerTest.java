package useraccountmanager.test;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import useraccountmanager.Application;
import useraccountmanager.model.UserAccount;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserAccountControllerTest {

    private MediaType contentType = new MediaType(
	    MediaType.APPLICATION_JSON.getType(),
	    MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
	this.mappingJackson2HttpMessageConverter = Arrays
		.asList(converters)
		.stream()
		.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
		.findAny().get();
    }

    @Before
    public void setup() throws Exception {
	mockMvc = webAppContextSetup(webApplicationContext).build();

	createUserAccount();
    }

    @Test
    public void getUserAccount() throws Exception {
	mockMvc.perform(get("/userAccounts/1")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasKey("firstName")))
		.andExpect(jsonPath("$", hasKey("lastName")))
		.andExpect(jsonPath("$", hasKey("email")))
		.andExpect(jsonPath("$", hasKey("dateOfBirth")));
    }

    @Test
    public void userAccountNotFound() throws Exception {
	mockMvc.perform(get("/userAccounts/-1")).andExpect(
		status().isNotFound());
    }

    @Test
    public void getUserAccountList() throws Exception {
	mockMvc.perform(get("/userAccounts")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType));
    }

    @Test
    public void createUserAccount() throws Exception {
	final String FIRST_NAME = "Viktor";
	final String LAST_NAME = "Ivanov";
	final String EMAIL = "v.kr.ivanov@gmail.com";

	Calendar cal = Calendar.getInstance();
	cal.set(1992, 0, 28);
	final Date DATE_OF_BIRTH = cal.getTime();

	UserAccount userAccount = new UserAccount();

	userAccount.setFirstName(FIRST_NAME);
	userAccount.setLastName(LAST_NAME);
	userAccount.setEmail(EMAIL);
	userAccount.setDateOfBirth(DATE_OF_BIRTH);

	mockMvc.perform(
		post("/userAccounts").contentType(contentType).content(
			json(userAccount))).andExpect(status().isCreated());
    }

    @Test
    public void unableToCreateInvalidUserAccount() throws Exception {
	final String FIRST_NAME = "   ";
	final String LAST_NAME = "   ";
	final String EMAIL = "invalid#email.com";
	final Date DATE_OF_BIRTH = null;

	UserAccount userAccount = new UserAccount();

	userAccount.setFirstName(FIRST_NAME);
	userAccount.setLastName(LAST_NAME);
	userAccount.setEmail(EMAIL);
	userAccount.setDateOfBirth(DATE_OF_BIRTH);

	mockMvc.perform(
		post("/userAccounts").contentType(contentType).content(
			json(userAccount))).andExpect(status().isBadRequest());
    }

    @Test
    public void updateUserAccount() throws Exception {
	final String FIRST_NAME = "Ivan";
	final String LAST_NAME = "Petrov";
	final String EMAIL = "ivan_petrov@abv.bg";

	Calendar cal = Calendar.getInstance();
	cal.set(1995, 3, 13);
	final Date DATE_OF_BIRTH = cal.getTime();
	final String DATE_OF_BIRTH_STRING = "1995-04-13";

	UserAccount userAccount = new UserAccount();

	userAccount.setFirstName(FIRST_NAME);
	userAccount.setLastName(LAST_NAME);
	userAccount.setEmail(EMAIL);
	userAccount.setDateOfBirth(DATE_OF_BIRTH);

	mockMvc.perform(
		put("/userAccounts/1").contentType(contentType).content(
			json(userAccount))).andExpect(status().isOk());
	mockMvc.perform(get("/userAccounts/1")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
		.andExpect(jsonPath("$.lastName", is(LAST_NAME)))
		.andExpect(jsonPath("$.email", is(EMAIL)))
		.andExpect(jsonPath("$.dateOfBirth", is(DATE_OF_BIRTH_STRING)));
    }

    @Test
    public void unableToIpdateUserAccountWithInvalidData() throws Exception {
	final String FIRST_NAME = "   ";
	final String LAST_NAME = "   ";
	final String EMAIL = "invalid#email.com";
	final Date DATE_OF_BIRTH = null;

	UserAccount userAccount = new UserAccount();

	userAccount.setFirstName(FIRST_NAME);
	userAccount.setLastName(LAST_NAME);
	userAccount.setEmail(EMAIL);
	userAccount.setDateOfBirth(DATE_OF_BIRTH);

	mockMvc.perform(
		put("/userAccounts/1").contentType(contentType).content(
			json(userAccount))).andExpect(status().isBadRequest());
    }

    protected String json(Object o) throws IOException {
	MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();

	mappingJackson2HttpMessageConverter.write(o,
		MediaType.APPLICATION_JSON, mockHttpOutputMessage);

	return mockHttpOutputMessage.getBodyAsString();
    }
}
