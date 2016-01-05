<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.dao.${daoName}">
	
	<resultMap type="${packageName}.entity.${entityName}" id="${entityName}Map">
		<!--<id column="id" property="id"/>-->
		<#list mapping?keys as key>
			<result column="${mapping[key]}" property="${key}"/>
		</#list>
	</resultMap>

	<!-- 自动生成id策略 -->
	<insert id="save" parameterType="${packageName}.entity.${entityName}"  useGeneratedKeys="true" keyProperty="id">
		insert into ${tableName}(
			<#assign insert = 0>
			<#list mapping?keys as key>
				<#if key!='id'>
					<#if insert==0>
					<#assign insert = 1>
					${mapping[key]}
					<#else>
					,${mapping[key]}
					</#if>
				</#if>
			</#list>
		) 
		values(
			<#assign insert = 0>
			<#list mapping?keys as key>
				<#if key!='id'>
					<#if insert==0>
					<#assign insert = 1>
						${r"#{"+key+"}"}
					<#else>
						,${r"#{"+key+"}"}
					</#if>
				</#if>
			</#list>
		)
		<!-- <selectKey  resultType="long" keyProperty="id">  
	        select last_insert_id() as ID from ${tableName} limit 1  
	    </selectKey> -->
	</insert>

	<select id="getById" parameterType="long" resultMap="${entityName}Map">
		select * from ${tableName} where id = ${r"#{id}"} 
	</select>

	<delete id="delete">
		delete from ${tableName} where id=${r"#{id}"} 
	</delete>

	<update id="update">
		update ${tableName}
		 <set>  
		 	<#list entityJson  as attr>
		 		<#assign name = attr['attr']>
		 		<#if attr['update']?exists&&name!='id'>
		 				<#if attr.update.type?exists&&attr.update.type=='text'>
		 					<if test="e.${name} !=null and e.${name} !=''">${mapping[name]}=${r"#{e."+name+"}"},</if>
		 				<#else>
		 					<if test="e.${name} !=null">${mapping[name]}=${r"#{e."+name+"}"},</if>
		 				</#if>
		 		</#if>
		 	</#list>
    	</set>  
		where id = ${r"#{e.id}"}
	</update>
	
	<sql id="findPageSQL">
		<where>
			<#assign finded = 0>
		 	<#list entityJson  as attr>
		 		<#assign name = attr['attr']>
		 		<#if attr['find']?exists>
		 			<#if finded==0>
		 			    <#assign finded = 1>
		 				<#if attr.find.type?exists&&attr.find.type=='text'>
		 					<if test="e.${name} !=null and e.${name} !=''">
								${mapping[name]} Like concat('%', ${r"#{e."+name+"}"}, '%')
							</if>
		 				<#else>
		 					<if test="e.${name} !=null">
								${mapping[name]} = ${r"#{e."+name+"}"}
							</if>
		 				</#if>
					<#else>
						<#if attr.find.type?exists&&attr.find.type=='text'>
		 					<if test="e.${name} !=null and e.${name} !=''">
								and ${mapping[name]} Like concat('%', ${r"#{e."+name+"}"}, '%')
							</if>
		 				<#else>
		 					<if test="e.${name} !=null">
								and ${mapping[name]} = ${r"#{e."+name+"}"}
							</if>
		 				</#if>
					</#if>
		 		</#if>
		 	</#list>
		</where>
	</sql>
	<select id="findPage" resultMap="${entityName}Map">
		select * from ${tableName} t
		<if test="e!=null">
			<include refid="findPageSQL" />
		</if>
		<if test="orderName !=null and orderName !=''">
			<choose>
				<#list mapping?keys as key>
				<when test="orderName=='${key}'">
					order by ${mapping[key]} ${r"${orderType}"}
				</when>
				</#list>
				<otherwise>
					<!-- order by  ${r"${orderName}"}  ${r"${orderType}"} -->
				</otherwise>
			</choose>
		</if>
		limit ${r"#{start}"} ,${r"#{pagesize}"}
	</select>
	<select id="findPageSize" resultType="long">
		select count(1) from ${tableName} t
		<if test="e!=null">
			<include refid="findPageSQL" />
		</if>
	</select>
	
</mapper>


