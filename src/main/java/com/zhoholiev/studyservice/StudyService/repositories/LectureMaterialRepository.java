package com.zhoholiev.studyservice.StudyService.repositories;

import com.zhoholiev.studyservice.StudyService.models.LectureMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureMaterialRepository extends JpaRepository<LectureMaterial,Integer> {


    @Modifying
    @Query(value = "alter table Lecture_material  update  Lecture_material (name, description, link, course_id, course_block_id, name_video)" +
            " VALUES (:name,:description, :link, :course_id, :course_block_id, :name_video) WHERE id = :id", nativeQuery = true)
    void updateLecture(@Param("name") String name, @Param("description") String description,@Param("link") String link,
                @Param("course_id") int course_id, @Param("course_block_id") int course_block_id,@Param("name_video") String name_video, @Param("id") int id);

}
