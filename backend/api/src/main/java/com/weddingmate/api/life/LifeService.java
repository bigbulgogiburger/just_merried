package com.weddingmate.api.life;

import com.weddingmate.api.life.dto.LifeDtos.*;
import com.weddingmate.common.exception.ErrorCode;
import com.weddingmate.common.exception.NotFoundException;
import com.weddingmate.domain.life.entity.*;
import com.weddingmate.domain.life.repository.*;
import com.weddingmate.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service @RequiredArgsConstructor
public class LifeService {
    private final UserRepository userRepository; private final FamilyMemberRepository familyMemberRepository; private final FamilyEventRepository familyEventRepository; private final NewHomeChecklistItemRepository newHomeChecklistItemRepository;

    @Transactional(readOnly = true)
    public LifeDashboardResponse dashboard(Long userId){ var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.")); LocalDate today = LocalDate.now(); long dDay = user.getWeddingDate()==null?0: ChronoUnit.DAYS.between(user.getWeddingDate(), today); int upcoming=familyEventRepository.findByUserIdAndEventDateBetweenOrderByEventDateAsc(userId, today, today.plusDays(30)).size(); var items = newHomeChecklistItemRepository.findByUserIdOrderByCreatedAtDesc(userId); int done=(int)items.stream().filter(NewHomeChecklistItem::isCompleted).count(); return new LifeDashboardResponse(dDay, upcoming, items.size(), done); }

    @Transactional(readOnly = true) public List<FamilyMemberResponse> members(Long userId){ return familyMemberRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(FamilyMemberResponse::from).toList(); }
    @Transactional public FamilyMemberResponse createMember(Long userId, FamilyMemberRequest r){ var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.")); return FamilyMemberResponse.from(familyMemberRepository.save(FamilyMember.builder().user(user).name(r.name()).relation(r.relation()).birthDate(r.birthDate()).lunar(Boolean.TRUE.equals(r.lunar())).build())); }
    @Transactional public FamilyMemberResponse updateMember(Long userId, Long memberId, FamilyMemberRequest r){ var m=getMember(userId, memberId); m.update(r.name(), r.relation(), r.birthDate(), r.lunar()); return FamilyMemberResponse.from(m); }
    @Transactional public void deleteMember(Long userId, Long memberId){ familyMemberRepository.delete(getMember(userId, memberId)); }

    @Transactional(readOnly = true) public List<FamilyEventResponse> events(Long userId){ return familyEventRepository.findByUserIdOrderByEventDateAsc(userId).stream().map(FamilyEventResponse::from).toList(); }
    @Transactional public FamilyEventResponse createEvent(Long userId, FamilyEventRequest r){ var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.")); FamilyMember member = r.familyMemberId()==null?null:getMember(userId, r.familyMemberId()); FamilyEvent event = familyEventRepository.save(FamilyEvent.builder().user(user).familyMember(member).title(r.title()).eventDate(r.eventDate()).lunar(Boolean.TRUE.equals(r.lunar())).remindDaysBefore(r.remindDaysBefore()==null?1:r.remindDaysBefore()).repeatYearly(r.repeatYearly()==null||r.repeatYearly()).build()); return FamilyEventResponse.from(event); }
    @Transactional public FamilyEventResponse updateEvent(Long userId, Long eventId, FamilyEventRequest r){ var event = getEvent(userId, eventId); FamilyMember member = r.familyMemberId()==null?null:getMember(userId, r.familyMemberId()); event.update(member, r.title(), r.eventDate(), r.lunar(), r.remindDaysBefore(), r.repeatYearly()); return FamilyEventResponse.from(event); }
    @Transactional public void deleteEvent(Long userId, Long eventId){ familyEventRepository.delete(getEvent(userId, eventId)); }

    @Transactional(readOnly = true)
    public List<NewHomeItemResponse> newHomeItems(Long userId, NewHomeCategory category){ List<NewHomeChecklistItem> items = category==null?newHomeChecklistItemRepository.findByUserIdOrderByCreatedAtDesc(userId):newHomeChecklistItemRepository.findByUserIdAndCategoryOrderByCreatedAtDesc(userId, category); return items.stream().map(NewHomeItemResponse::from).toList(); }
    @Transactional public NewHomeItemResponse createNewHomeItem(Long userId, NewHomeItemRequest r){ var user=userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다.")); return NewHomeItemResponse.from(newHomeChecklistItemRepository.save(NewHomeChecklistItem.builder().user(user).category(r.category()).title(r.title()).memo(r.memo()).completed(Boolean.TRUE.equals(r.completed())).assignee(r.assignee()).build())); }
    @Transactional public NewHomeItemResponse updateNewHomeItem(Long userId, Long itemId, NewHomeItemRequest r){ var item=getNewHome(userId, itemId); item.update(r.title(), r.memo(), r.completed(), r.assignee()); return NewHomeItemResponse.from(item); }
    @Transactional public void deleteNewHomeItem(Long userId, Long itemId){ newHomeChecklistItemRepository.delete(getNewHome(userId, itemId)); }

    private FamilyMember getMember(Long userId, Long memberId){ var m = familyMemberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "가족 구성원을 찾을 수 없습니다.")); if(!m.getUser().getId().equals(userId)) throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "접근할 수 없는 가족 구성원입니다."); return m; }
    private FamilyEvent getEvent(Long userId, Long eventId){ var e = familyEventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "가족 행사를 찾을 수 없습니다.")); if(!e.getUser().getId().equals(userId)) throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "접근할 수 없는 가족 행사입니다."); return e; }
    private NewHomeChecklistItem getNewHome(Long userId, Long id){ var i = newHomeChecklistItemRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, "신혼집 체크리스트 항목을 찾을 수 없습니다.")); if(!i.getUser().getId().equals(userId)) throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "접근할 수 없는 체크리스트 항목입니다."); return i; }
}
