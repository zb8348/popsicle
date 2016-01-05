create table ${tableName}(
	 id                   bigint(19) not null auto_increment,
	<#list mapping?keys as key>
		<#if key!='id'>
	${mapping[key]}		varchar(32),
		</#if>
	</#list>
	 primary key (id)
)
-- datetime, numeric(10,5),varchar(32), bigint(19) ,smallint,int,
	


