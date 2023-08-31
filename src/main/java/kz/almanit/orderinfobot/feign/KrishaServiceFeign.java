package kz.almanit.orderinfobot.feign;

import kz.almanit.orderinfobot.dto.KrishaResponseDto;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RefreshScope
@FeignClient(value = "krisha", url = "${krisha.base.url}")
public interface KrishaServiceFeign {
    @GetMapping(value = "/ms/views/krisha/live/{ids}/")
    KrishaResponseDto getKrishaViewsByIds(@PathVariable String ids);

}
