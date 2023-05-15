package com.ssafy.enjoytrip.plan.model.service;

import com.ssafy.enjoytrip.global.mapper.FileMapper;
import com.ssafy.enjoytrip.plan.dto.DayDto;
import com.ssafy.enjoytrip.plan.dto.DayForm;
import com.ssafy.enjoytrip.plan.dto.PlanForm;
import com.ssafy.enjoytrip.plan.model.mapper.PlanMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlanServiceImpl implements PlanService {

    private final PlanMapper planMapper;
    private final FileMapper fileMapper;

    /**
     * 여행 플래너 생성
     */
    @Override
    public void savePlan(PlanForm form) throws Exception {
//        planMapper.createPlan(form);
    }

    @Override
    public void savePlanDays(DayForm form) throws Exception {
//        Map<String, Object> param = new HashMap<>();
//        param.put("planId", form.getPlanId());
//
//        for (DayDto item : form.getItems()) {
//            param.put("item", item);
//            log.info("day={}", item.getDay());
//            planMapper.createPlanDays(param);
//        }
    }

    @Override
    public List<PlanForm> findPlans(int memberId) throws Exception {
        return planMapper.selectAllPlans(memberId);
    }

    @Override
    public Map<String, List<DayForm>> findPlanDetail(Map<String, String> param) throws Exception {
        int days = getDays(param.get("startDate"), param.get("endDate"));

        Map<String, List<DayForm>> result = new HashMap<>();
        for (int i = 1; i <= days; i++) {
            param.put("day", String.valueOf(i));
            List<DayForm> dayItems = planMapper.selectPlanDetail(param);
            result.put(String.valueOf(i), dayItems);
        }

        return result;
    }

    @Override
    public Map<String, String> findPlanInfo(Map<String, String> param) throws Exception {
        return planMapper.selectPlanInfo(param);
    }

    @Override
    public void updatePlan(PlanForm form) throws Exception {
        planMapper.updatePlan(form);
    }

    // TODO DELETE 시 file_info 테이블의 image도 삭제해야합니다
    @Override
    public void deletePlan(int planId) throws Exception {
        planMapper.deletePlan(planId);
//        fileMapper.deletePlanImage(planId);
    }

    @Override
    public void updatePlanDetail(Map<String, Object> form) throws Exception {
        planMapper.deletePlanDay(form);
        planMapper.insertPlanDay(form);
    }

    @Override
    public List<Map<String, Object>> createPlan(Map<String, Object> param) throws Exception {
        int days = getDays((String) param.get("startDate"), (String) param.get("endDate"));

        param.put("day1", days);
        param.put("day2", days * 2);

        // 1. 플랜 생성
        List<DayForm> plan = planMapper.createPlan(param);
        param.put("image", plan.get(0).getImage());
        param.put("title", param.get("sidoName"));

        // 2. List<DayForm> 배치 해주기
        List<Map<String, Object>> result = rearrangePlan(plan, days);

        // 3. 화면에 출력 전, DB PUSH 후 return 해주기
        Map<String, Object> tmp = new HashMap<>();
        planMapper.insertPlan(param); // plan 기본정보
        tmp.put("planId", param.get("planId"));
        for (Map<String, Object> items : result) {
            List<DayForm> item = (List<DayForm>) items.get("items");

            for (DayForm dayForm : item) {
                tmp.put("item", dayForm);
                planMapper.insertPlanDays(tmp);
            }
        }


        return result;
    }

    /**
     *  출발날짜, 마지막날짜로 일 수 계산
     */
    private static int getDays(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        int days = Integer.parseInt(String.valueOf(ChronoUnit.DAYS.between(start, end) + 1));
        return days;
    }

    public List<Map<String, Object>> rearrangePlan(List<DayForm> plan, int days) {
        List<Map<String, Object>> rearrangedPlan = new ArrayList<>();
        int mealsPerDay = days * 2;  // 음식점은 하루에 두 번 방문합니다.
        int accommodations = days;  // 숙소는 일수와 동일합니다.
        int sitesStartIndex = mealsPerDay + accommodations; // 관광지의 시작 위치

        for (int day = 0; day < days; day++) {
            List<DayForm> items = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                DayForm tmp;
                if (i == 4) {
                    tmp = plan.get(mealsPerDay + day);
                    accommodations--;  // 숙소를 하나 사용했습니다.
                } else if (i % 2 == 0) {
                    tmp = plan.get(sitesStartIndex + day * 2 + i / 2);
                } else {
                    tmp = plan.get(day * 2 + i / 2);
                }
                tmp.setOrder(i + 1);
                tmp.setDay(day + 1);
                items.add(tmp);
            }
            Map<String, Object> dayPlan = new HashMap<>();
            dayPlan.put("Day", (day + 1));
            dayPlan.put("items", items);
            rearrangedPlan.add(dayPlan);
        }

        return rearrangedPlan;
    }

}
