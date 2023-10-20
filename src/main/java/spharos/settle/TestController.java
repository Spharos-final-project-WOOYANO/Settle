package spharos.settle;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/settle")
public class TestController {
    @GetMapping("/test")
    public String testMethod(){
        return "Settle Service";
    }
}
