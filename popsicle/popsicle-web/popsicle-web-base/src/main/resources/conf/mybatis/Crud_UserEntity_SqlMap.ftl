<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.pfw.popsicle.demo.crud.dao.UserEntityDao">
	
	<resultMap type="com.pfw.popsicle.demo.crud.entity.UserEntity" id="UserEntityMap">
		<!--<id column="id" property="id"/>-->
			<result column="id" property="id"/>
			<result column="create_time" property="createTime"/>
			<result column="sex" property="sex"/>
			<result column="name" property="name"/>
	</resultMap>

	<!-- 自动生成id策略 -->
	<insert id="save" parameterType="com.pfw.popsicle.demo.crud.entity.UserEntity"  useGeneratedKeys="true" keyProperty="id">
		insert into t_user(
					,create_time
					,sex
					,name
		) 
		values(
						,#{createTime}
						,#{sex}
						,#{name}
		)
		<!-- <selectKey  resultType="long" keyProperty="id">  
	        select last_insert_id() as ID from t_user limit 1  
	    </selectKey> -->
	</insert>

	<select id="getById" parameterType="long" resultMap="UserEntityMap">
		select * from t_user where id = #{id} 
	</select>

	<delete id="delete">
		delete from t_user where id=#{id} 
	</delete>

	<update id="update">
		update t_user
		 <set>  
		 					<if test="e.name !=null and e.name !=''">name=#{e.name}</if>
		 					<if test="e.sex !=null and e.sex !=''">sex=#{e.sex}</if>
    	</set>  
		where id = #{e.id}
	</update>
	
	<sql id="findPageSQL">
		<where>
		 					<if test="e.name !=null and e.name !=''">
								and name Like concat('%', #{e.name}, '%')
							</if>
		 					<if test="e.sex !=null and e.sex !=''">
								and sex Like concat('%', #{e.sex}, '%')
							</if>
		</where>
	</sql>
	<select id="findPage" resultMap="UserEntityMap">
		select * from t_user t
		<if test="e!=null">
			<include refid="findPageSQL" />
		</if>
		<if test="orderName !=null and orderName !=''">
			<choose>
				<when test="orderName=='id'">
					order by id ${orderType}
				</when>
				<when test="orderName=='createTime'">
					order by create_time ${orderType}
				</when>
				<when test="orderName=='sex'">
					order by sex ${orderType}
				</when>
				<when test="orderName=='name'">
					order by name ${orderType}
				</when>
				<otherwise>
					<!-- order by  ${orderName}  ${orderType} -->
				</otherwise>
			</choose>
		</if>
		limit #{start} ,#{pagesize}
	</select>
	<select id="findPageSize" resultType="long">
		select count(1) from t_user t
		<if test="e!=null">
			<include refid="findPageSQL" />
		</if>
	</select>
	
</mapper>


