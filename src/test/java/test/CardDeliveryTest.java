package test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import data.DataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    @BeforeEach
    void setup() {
        Configuration.headless = true;

        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");

        var firstMeetingDate = DataGenerator.generateDate(4);
        var secondMeetingDate = DataGenerator.generateDate(7);

        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").press(Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $(withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));

        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate))
                .shouldBe(visible);
        $("[data-test-id=date] input").doubleClick();
        $("[data-test-id=date] input").press(Keys.DELETE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $(Selectors.byText("Запланировать")).click();

        $(byText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible, Duration.ofSeconds(3));
        $(byText("Перепланировать")).click();

        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + secondMeetingDate))
                .shouldBe(visible);

    }

}
