package com.tanyang.twitter.dao;

import com.tanyang.twitter.pojo.Attention;
import com.tanyang.twitter.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public interface AttentionDao extends JpaRepository<Attention,String>{

    @Query(value = "select attented from attention where attent=:id",nativeQuery = true)
    List<String> getUserByAttent(@Param("id") String id);

    @Query(value = "select attented from attention where attent=:id limit :tstart,:num",nativeQuery = true)
    List<String> getUserPageByAttent(@Param("id") String id,@Param("tstart") Integer tstart,@Param("num") Integer num);

    @Query(value = "delete from attention where attented =:id",nativeQuery = true)
    @Modifying
    Integer deleteByAttented(@Param("id") String id);

    @Query(value = "select * from attention where attent=:attent and attented= :attented",nativeQuery = true)
    Attention getAttentionByAttentAndAndAttented(@Param("attent") String attent,@Param("attented") String attented);

    @Query(value = "select attent from attention where attented= :id",nativeQuery = true)
    List<String> getAttentByAttented(@Param("id") String attentid);


}
