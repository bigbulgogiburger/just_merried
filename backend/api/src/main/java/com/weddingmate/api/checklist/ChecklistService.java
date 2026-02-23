package com.weddingmate.api.checklist;

import com.weddingmate.api.checklist.dto.*;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.checklist.entity.Checklist;
import com.weddingmate.domain.checklist.entity.ChecklistItem;
import com.weddingmate.domain.checklist.repository.ChecklistItemRepository;
import com.weddingmate.domain.checklist.repository.ChecklistRepository;
import com.weddingmate.domain.user.entity.User;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChecklistResponse createChecklist(Long userId, ChecklistCreateRequest request) {
        User user = getUser(userId);
        Checklist checklist = checklistRepository.save(Checklist.builder()
                .user(user)
                .title(request.title())
                .description(request.description())
                .startDate(request.startDate())
                .dueDate(request.dueDate())
                .build());
        return toResponse(checklist);
    }

    @Transactional(readOnly = true)
    public List<ChecklistResponse> getMyChecklists(Long userId) {
        return checklistRepository.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public ChecklistResponse updateChecklist(Long userId, Long checklistId, ChecklistUpdateRequest request) {
        Checklist checklist = getOwnedChecklist(userId, checklistId);
        checklist.update(request.title(), request.description(), request.startDate(), request.dueDate());
        return toResponse(checklist);
    }

    @Transactional
    public void deleteChecklist(Long userId, Long checklistId) {
        Checklist checklist = getOwnedChecklist(userId, checklistId);
        checklistRepository.delete(checklist);
    }

    @Transactional
    public ChecklistItemResponse addItem(Long userId, Long checklistId, ChecklistItemCreateRequest request) {
        Checklist checklist = getOwnedChecklist(userId, checklistId);
        ChecklistItem item = checklistItemRepository.save(ChecklistItem.builder()
                .checklist(checklist)
                .title(request.title())
                .description(request.description())
                .assignee(request.assignee())
                .sortOrder(request.sortOrder() != null ? request.sortOrder() : 0)
                .build());
        return ChecklistItemResponse.from(item);
    }

    @Transactional
    public ChecklistItemResponse updateItem(Long userId, Long checklistId, Long itemId, ChecklistItemUpdateRequest request) {
        getOwnedChecklist(userId, checklistId);
        ChecklistItem item = getOwnedItem(checklistId, itemId);
        item.update(request.title(), request.description(), request.assignee(), request.sortOrder());
        return ChecklistItemResponse.from(item);
    }

    @Transactional
    public ChecklistItemResponse toggleItem(Long userId, Long checklistId, Long itemId, ChecklistItemToggleRequest request) {
        getOwnedChecklist(userId, checklistId);
        ChecklistItem item = getOwnedItem(checklistId, itemId);
        item.toggleComplete(request.completed());
        return ChecklistItemResponse.from(item);
    }

    @Transactional
    public void deleteItem(Long userId, Long checklistId, Long itemId) {
        getOwnedChecklist(userId, checklistId);
        ChecklistItem item = getOwnedItem(checklistId, itemId);
        checklistItemRepository.delete(item);
    }

    private ChecklistResponse toResponse(Checklist checklist) {
        List<ChecklistItemResponse> items = checklistItemRepository.findByChecklistIdOrderBySortOrderAsc(checklist.getId())
                .stream()
                .map(ChecklistItemResponse::from)
                .toList();

        int progress = 0;
        if (!items.isEmpty()) {
            long completed = items.stream().filter(ChecklistItemResponse::completed).count();
            progress = (int) Math.round((completed * 100.0) / items.size());
        }

        return ChecklistResponse.of(checklist, items, progress);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }

    private Checklist getOwnedChecklist(Long userId, Long checklistId) {
        Checklist checklist = checklistRepository.findById(checklistId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHECKLIST_NOT_FOUND, "체크리스트를 찾을 수 없습니다."));
        if (!checklist.getUser().getId().equals(userId)) {
            throw new NotFoundException(ErrorCode.CHECKLIST_NOT_FOUND, "체크리스트를 찾을 수 없습니다.");
        }
        return checklist;
    }

    private ChecklistItem getOwnedItem(Long checklistId, Long itemId) {
        ChecklistItem item = checklistItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHECKLIST_ITEM_NOT_FOUND, "체크리스트 항목을 찾을 수 없습니다."));
        if (!item.getChecklist().getId().equals(checklistId)) {
            throw new NotFoundException(ErrorCode.CHECKLIST_ITEM_NOT_FOUND, "체크리스트 항목을 찾을 수 없습니다.");
        }
        return item;
    }
}
