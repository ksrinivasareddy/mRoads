package TestApiController;

import com.smarttrip.api.WeatherApiClient;
import com.smarttrip.api.CurrencyApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestApiController {

    @Autowired
    private WeatherApiClient weatherApiClient;

    @Autowired
    private CurrencyApiClient currencyApiClient;

    // ✅ Test Weather API
    @GetMapping("/weather/{city}")
    public String testWeather(@PathVariable String city) {
        return weatherApiClient.getWeather(city);
    }

    // ✅ Test Currency API
    @GetMapping("/currency")
    public String testCurrency(@RequestParam String from, @RequestParam String to, @RequestParam double amount) {
        double result = currencyApiClient.convert(from, to, amount);
        if (result == -1) {
            return "Currency conversion failed!";
        }
        return amount + " " + from + " = " + result + " " + to;
    }
}
