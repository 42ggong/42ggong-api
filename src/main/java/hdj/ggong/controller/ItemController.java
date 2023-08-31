package hdj.ggong.controller;

import hdj.ggong.dto.item.CreateItemRequest;
import hdj.ggong.dto.item.CreateItemResponse;
import hdj.ggong.dto.item.ItemInfoResponse;
import hdj.ggong.security.CustomUserDetails;
import hdj.ggong.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/items/identifier/{keepIdentifier}")
    public ResponseEntity<ItemInfoResponse> getItemInfo(@PathVariable("keepIdentifier") String keepIdentifier) {
        return itemService.getItemInfo(keepIdentifier)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/users/me/items")
    public List<ItemInfoResponse> getMyItemInfoList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return itemService.getMyItemInfoList(userDetails);
    }

    @GetMapping("/items")
    public List<ItemInfoResponse> getAllItemInfoList() {
        return itemService.getAllItemInfoList();
    }

}
