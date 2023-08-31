package kz.almanit.orderinfobot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kz.almanit.orderinfobot.bot.TelegramBot;
import kz.almanit.orderinfobot.dto.KrishaViewsDto;
import kz.almanit.orderinfobot.feign.KrishaServiceFeign;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KrishaParsingService {

    private final KrishaServiceFeign krishaServiceFeign;
    private final TelegramBot telegramBot;

    @Value("${krisha.cart.url}")
    private String cartUrl;
    @Value("${krisha_bot.channel_chat_id}")
    private String chatId;
    private static final Integer pageItemSize = 20;
    private static final Integer maximumViewCounts = 10;

    @SneakyThrows
    public List<String> parseKrishaPage(String searchUrl) {
        Document doc = Jsoup.connect(searchUrl).get();
        Elements elements =
            doc.getElementsByClass("a-card");
        List<String> dealIds = new ArrayList<>(pageItemSize);
        for (Element e : elements) {
            String linkHref = e.attr("data-id");
            dealIds.add(linkHref);
        }
        return dealIds;
    }

    @SneakyThrows
    public void getViewsAndSendToTelegram(List<String> dealIds) {
        var concatIds = String.join(",", dealIds);
        var viewsDto = krishaServiceFeign.getKrishaViewsByIds(concatIds);
        for (Map.Entry<String, KrishaViewsDto> entry: viewsDto.getData().entrySet()) {
            if (entry.getValue().getViewCount() < maximumViewCounts) {
                String telegramResponse = cartUrl + entry.getKey();
                telegramBot.sendNotification(chatId, telegramResponse);
            }
        }
    }

}
