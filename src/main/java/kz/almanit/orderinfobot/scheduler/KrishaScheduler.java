package kz.almanit.orderinfobot.scheduler;

import kz.almanit.orderinfobot.service.KrishaParsingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RefreshScope
@RequiredArgsConstructor
public class KrishaScheduler {

    @Value("${krisha.search.first.url}")
    private String searchFirstUrl;
    @Value("${krisha.search.second.url}")
    private String searchSecondUrl;

    private final KrishaParsingService parsingService;

    @Scheduled(cron = "${scheduler.krisha.cron.expression}")
    public void sendReadyForDeliveryNotification() {
        log.info("Scheduler running");
        checkGivenPage(this.searchFirstUrl);
        checkGivenPage(this.searchSecondUrl);
    }

    public void checkGivenPage(String searchUrl) {
        var dealIdsPage = parsingService.parseKrishaPage(searchUrl);
        parsingService.getViewsAndSendToTelegram(dealIdsPage);
    }

}
