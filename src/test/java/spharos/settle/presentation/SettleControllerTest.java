package spharos.settle.presentation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SettleControllerTest {

    @Autowired
    private SettleController settleController;


    @Test
    public void 테스트이름() throws Exception {
        // given
        settleController.test();
        // when

        // then
    }
}