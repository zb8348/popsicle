package com.pfw.popsicle.social.hbase.repository;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.pfw.popsicle.social.hbase.entity.TalkContent;

@Repository
public class TalkContentRepository implements InitializingBean{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private String tableName = "talk";
	public static byte[] CF_INFO = Bytes.toBytes("cfInfo");
//	private byte[] qKey = Bytes.toBytes("key");
	private byte[] qTargetId = Bytes.toBytes("targetId");
	private byte[] qContent = Bytes.toBytes("content");
	
	
	@Autowired
	private HbaseTemplate hbaseTemplate;

	
//	@Resource(name = "hbaseConfiguration")
	@Autowired
	@Qualifier("hbaseConfiguration")
	private Configuration config;

	
	
	private HBaseAdmin admin;

	public void initialize() throws IOException {
		byte[] names = tableName.getBytes();
		if (admin.tableExists(names)) {
			if (!admin.isTableDisabled(names)) {
				log.info("Disabling {} ", tableName);
				admin.disableTable(tableName.getBytes());
			}
//			log.info("Deleting {} ", tableName);
//			admin.deleteTable(names);
		}else{
			TableName tname = TableName.valueOf(tableName);
			HTableDescriptor tableDescriptor = new HTableDescriptor(tname);
			HColumnDescriptor columnDescriptor = new HColumnDescriptor(CF_INFO);
			tableDescriptor.addFamily(columnDescriptor);
			admin.createTable(tableDescriptor);
		}
	}
	
	public void afterPropertiesSet() throws Exception {
		admin = new HBaseAdmin(config);
		initialize();
	}
	
	
	
	
//	public List<User> findAll() {
//		return hbaseTemplate.find(tableName, "cfInfo", new RowMapper<User>() {
//			public User mapRow(Result result, int rowNum) throws Exception {
//				Bytes.toString(result.getValue(CF_INFO, qUser);
//				
//				
//				return new User(), 
//							    Bytes.toString(result.getValue(CF_INFO, qEmail)),
//							    Bytes.toString(result.getValue(CF_INFO, qPassword)));
//			}
//		});
//
//	}

	public TalkContent save(final TalkContent tc) {
		return hbaseTemplate.execute(tableName, new TableCallback<TalkContent>() {
			public TalkContent doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(tc.getKey()));
				p.add(CF_INFO, qTargetId, Bytes.toBytes(tc.getTargetId()));
				p.add(CF_INFO, qContent, Bytes.toBytes(tc.getContent()));
				table.put(p);
				return tc;
			}
		});
	}

	public TalkContent getByKey(final String key) {
		return hbaseTemplate.get(tableName, key, new RowMapper<TalkContent>() {

			public TalkContent mapRow(Result result, int rowNum) throws Exception {
				long targetId = 0;
				try {
					targetId = Bytes.toLong(result.getValue(CF_INFO, qTargetId));
				} catch (Exception e) {
				}
				String content = Bytes.toString(result.getValue(CF_INFO, qContent));
				
				TalkContent tc = new TalkContent();
				tc.setKey(key);
				tc.setTargetId(targetId);
				tc.setContent(content);
				return tc;
			}
		});
	}

}
