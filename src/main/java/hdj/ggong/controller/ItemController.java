package hdj.ggong.controller;

import hdj.ggong.domain.Item;
import hdj.ggong.dto.item.CreateItemRequest;
import hdj.ggong.dto.item.CreateItemResponse;
import hdj.ggong.dto.item.ItemInfoResponse;
import hdj.ggong.security.CustomUserDetails;
import hdj.ggong.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/items")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CreateItemResponse createItem(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @Valid @RequestBody CreateItemRequest createItemRequest) {
        return itemService.createItem(userDetails, createItemRequest);
    }

    @GetMapping("/items/{itemId}")
    public ItemInfoResponse getItemInfo(@PathVariable("itemId") Long itemId) {
        return itemService.getItemInfo(itemId);
    }

}
