package kz.almanit.orderinfobot.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RefreshScope
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${krisha_bot.username}")
    private String username;
    @Value("${krisha_bot.token}")
    private String token;
    private static final String defaultResponse = "Unsupported operation";

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        String usernameOfUser = update.getMessage().getChat().getUserName();
        log.info("User with username {} sends request to the server", usernameOfUser);
        sendNotification(chatId, defaultResponse);
    }

    public void sendNotification(String chatId, String msg) throws TelegramApiException {
        SendMessage response = new SendMessage(chatId, msg);
        response.enableHtml(true);
        execute(response);
    }

}
