package org.csu.mypetstoreclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("log")
public class Log {

    @TableId(type = IdType.INPUT)
    private String log_user_id;
    private Timestamp log_date;
    private String url;
    private int response_status;
    private String response_msg;
    private String response_data_type;
}
