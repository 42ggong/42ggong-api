package hdj.ggong.controller;

import hdj.ggong.dto.itemHistory.ItemHistoryResponse;
import hdj.ggong.security.CustomUserDetails;
import hdj.ggong.service.ItemHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ItemHistoryController {

    private final ItemHistoryService itemHistoryService;

    @GetMapping("/item-histories")
    public List<ItemHistoryResponse> getItemHistoryListWithPage(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                @RequestParam(value = "page", required = false) Integer page) {
        return itemHistoryService.getItemHistoryListWithPage(userDetails, page);
    }
}
