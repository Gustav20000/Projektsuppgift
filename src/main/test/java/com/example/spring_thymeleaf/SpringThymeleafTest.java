package com.example.spring_thymeleaf;

import com.example.spring_thymeleaf.entities.LapTime;
import com.example.spring_thymeleaf.repo.LapTimeRepo;
import com.example.spring_thymeleaf.service.LapTimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class SpringThymeleafTest {

//    private WebDriver driver;
//    private Object confirm;


    @Autowired
    private MockMvc mvc;

    @Autowired
    private LapTimeService lapTimeService;

    @MockBean
    private LapTimeRepo lapTimeRepo;

    @Test
    public void contextLoad() {
    }

//    @BeforeAll
//    public static void init() {
//        WebDriverManager.chromedriver().setup();
//    }

//    @BeforeEach
//    public void setup() {
//        driver = new ChromeDriver();
//        driver.manage().window().setPosition(new Point(2000, 0));
//        driver.manage().window().maximize();
//    }

//    @AfterEach
//    public void closeBrowser() {
//        driver.close();
//        driver.quit();
//    }

    @Test
    public void testSomething(){
        //Fungerar inte med spring boot, flera problem.
        //driver.get("http:localhost:8080/laptimes");
    }

    @Test
    public void test_laptimes_200_OK()
            throws Exception {

        List<LapTime> lapTimes = Arrays.asList(new LapTime(1.5), new LapTime(2.5),
                new LapTime(3.5), new LapTime(4.5));

        doReturn(lapTimes).when(lapTimeRepo).findAll();

        mvc.perform(MockMvcRequestBuilders.get("/laptimes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test_laptimes_sorted()
            throws Exception {

        List<LapTime> lapTimes = Arrays.asList(new LapTime(4.5), new LapTime(7.5),
                new LapTime(6.5), new LapTime(1.5), new LapTime(5.5),
                new LapTime(3.5), new LapTime(2.5));

        doReturn(lapTimes).when(lapTimeRepo).findAll();

        List<LapTime> lapTimesSorted = lapTimeService.findLapTimes();

        assertThat(lapTimesSorted, hasSize(5));
        for (int i = 0; i < lapTimesSorted.size() - 1; i++) {
            double lapTime1 = lapTimesSorted.get(i).getLapTime();
            double lapTime2 = lapTimesSorted.get(i + 1).getLapTime();
            assertThat(lapTime1 <= lapTime2, is(true));
        }
    }
}


