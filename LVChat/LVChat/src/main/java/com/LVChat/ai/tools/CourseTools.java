package com.LVChat.ai.tools;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.LVChat.ai.entity.po.Course;
import com.LVChat.ai.entity.po.CourseReservation;
import com.LVChat.ai.entity.po.School;
import com.LVChat.ai.entity.query.CourseQuery;
import com.LVChat.ai.service.ICourseReservationService;
import com.LVChat.ai.service.ICourseService;
import com.LVChat.ai.service.ISchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CourseTools {

    private final ICourseService courseService;
    private final ISchoolService schoolService;
    private final ICourseReservationService courseReservationService;

    @Tool(description = "根据条件查询内容")
    public List<Course> queryCourse(@ToolParam(required = false, description = "查询条件") CourseQuery query) {
        QueryChainWrapper<Course> wrapper = courseService.query();
        wrapper
        .eq(query.getType() != null, "type", query.getType())
        .le(query.getEdu() != null, "edu", query.getEdu());
        if(query.getSorts() != null) {
            for (CourseQuery.Sort sort : query.getSorts()) {
                wrapper.orderBy(true, sort.getAsc(), sort.getField());
            }
        }
        return wrapper.list();
    }

    @Tool(description = "查询所有校区")
    public List<School> queryAllSchools() {
        return schoolService.list();
    }

    @Tool(description = "生成预约单,并返回生成的预约单号")
    public String generateCourseReservation(
        String courseName, String studentName, String contactInfo, String school, String remark) {
        CourseReservation courseReservation = new CourseReservation();
        courseReservation.setCourse(courseName);
        courseReservation.setStudentName(studentName);
        courseReservation.setContactInfo(contactInfo);
        courseReservation.setSchool(school);
        courseReservation.setRemark(remark);
        courseReservationService.save(courseReservation);
        return String.valueOf(courseReservation.getId());
    }
}